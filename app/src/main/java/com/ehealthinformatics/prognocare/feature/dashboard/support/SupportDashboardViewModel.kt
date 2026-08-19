package com.ehealthinformatics.prognocare.feature.dashboard.support

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ehealthinformatics.prognocare.navigation.UserRole
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
class SupportDashboardViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow(SupportDashboardState())
    val state: StateFlow<SupportDashboardState> = _state.asStateFlow()

    init {
        loadDashboardData()
    }

    private fun loadDashboardData() {
        viewModelScope.launch {
            val queue = getMockCheckInQueue()
            val requests = getMockRequests()

            _state.update {
                it.copy(
                    isLoading = false,
                    greeting = getGreeting(),
                    todayDate = getTodayDate(),
                    patientsWaiting = queue.count { c -> c.status == CheckInStatus.WAITING },
                    checkedInToday = queue.count { c ->
                        c.status in listOf(
                            CheckInStatus.CHECKED_IN,
                            CheckInStatus.IN_SESSION,
                            CheckInStatus.CHECKED_OUT
                        )
                    },
                    activeRequests = requests.count { r ->
                        r.status in listOf(RequestStatus.OPEN, RequestStatus.IN_PROGRESS)
                    },
                    completedToday = queue.count { c -> c.status == CheckInStatus.CHECKED_OUT },
                    checkInQueue = queue,
                    recentRequests = requests,
                )
            }
        }
    }

    fun checkInPatient(checkInId: String) {
        _state.update { current ->
            val updatedQueue = current.checkInQueue.map { item ->
                if (item.id == checkInId) {
                    item.copy(status = CheckInStatus.CHECKED_IN, waitTime = null)
                } else item
            }
            current.copy(
                checkInQueue = updatedQueue,
                patientsWaiting = updatedQueue.count { it.status == CheckInStatus.WAITING },
                checkedInToday = updatedQueue.count { it.status != CheckInStatus.WAITING && it.status != CheckInStatus.NO_SHOW },
            )
        }
    }

    fun checkOutPatient(checkInId: String) {
        _state.update { current ->
            val updatedQueue = current.checkInQueue.map { item ->
                if (item.id == checkInId) {
                    item.copy(status = CheckInStatus.CHECKED_OUT, waitTime = null)
                } else item
            }
            current.copy(
                checkInQueue = updatedQueue,
                completedToday = updatedQueue.count { it.status == CheckInStatus.CHECKED_OUT },
            )
        }
    }

    fun resolveRequest(requestId: String) {
        _state.update { current ->
            val updatedRequests = current.recentRequests.map { item ->
                if (item.id == requestId) {
                    item.copy(status = RequestStatus.RESOLVED)
                } else item
            }
            current.copy(
                recentRequests = updatedRequests,
                activeRequests = updatedRequests.count { r ->
                    r.status in listOf(RequestStatus.OPEN, RequestStatus.IN_PROGRESS)
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
