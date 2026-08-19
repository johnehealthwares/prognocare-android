package com.ehealthinformatics.prognocare.feature.dashboard.technician

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class TechnicianDashboardViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow(TechnicianDashboardState())
    val state: StateFlow<TechnicianDashboardState> = _state.asStateFlow()

    init {
        loadDashboardData()
    }

    private fun loadDashboardData() {
        viewModelScope.launch {
            val orders = getMockPendingOrders()
            val results = getMockRecentResults()

            _state.update {
                it.copy(
                    isLoading = false,
                    greeting = getGreeting(),
                    todayDate = getTodayDate(),
                    pendingOrders = orders.count { o ->
                        o.status in listOf(OrderStatus.RECEIVED, OrderStatus.SAMPLE_COLLECTED)
                    },
                    inProgress = orders.count { o ->
                        o.status in listOf(OrderStatus.IN_PROGRESS, OrderStatus.PROCESSING)
                    },
                    completedToday = results.size,
                    urgentOrders = orders.count { o ->
                        o.priority == OrderPriority.STAT || o.priority == OrderPriority.URGENT
                    },
                    pendingOrdersList = orders,
                    recentResults = results,
                )
            }
        }
    }

    fun updateOrderStatus(orderId: String, newStatus: OrderStatus) {
        _state.update { current ->
            val updatedOrders = current.pendingOrdersList.map { order ->
                if (order.id == orderId) order.copy(status = newStatus) else order
            }
            current.copy(
                pendingOrdersList = updatedOrders,
                pendingOrders = updatedOrders.count { o ->
                    o.status in listOf(OrderStatus.RECEIVED, OrderStatus.SAMPLE_COLLECTED)
                },
                inProgress = updatedOrders.count { o ->
                    o.status in listOf(OrderStatus.IN_PROGRESS, OrderStatus.PROCESSING)
                },
            )
        }
    }

    private fun getGreeting(): String {
        val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        return when {
            hour < 12 -> "Good morning"
            hour < 17 -> "Good afternoon"
            else -> "Good evening"
        }
    }

    private fun getTodayDate(): String {
        val sdf = SimpleDateFormat("EEEE, MMM d", Locale.US)
        return sdf.format(Calendar.getInstance().time)
    }
}
