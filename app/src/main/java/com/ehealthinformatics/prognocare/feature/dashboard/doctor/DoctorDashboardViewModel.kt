package com.ehealthinformatics.prognocare.feature.dashboard.doctor

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

data class AppointmentUi(
    val id: String,
    val patientName: String,
    val type: String,
    val time: String,
    val status: String,
    val isUrgent: Boolean = false,
)

data class DoctorDashboardState(
    val greeting: String = "",
    val doctorName: String = "Dr. Adebayo",
    val todayDate: String = "Tuesday, Aug 18",
    val totalPatients: Int = 0,
    val todayAppointments: Int = 0,
    val pendingTasks: Int = 0,
    val activeEncounters: Int = 0,
    val completedToday: Int = 0,
    val urgentCount: Int = 0,
    val upcomingAppointments: List<AppointmentUi> = emptyList(),
    val recentPatients: List<PatientUi> = emptyList(),
    val isLoading: Boolean = true,
    val error: String? = null,
)

data class PatientUi(
    val id: String,
    val name: String,
    val mrn: String,
    val age: Int,
    val lastVisit: String,
    val diagnosis: String,
)

@HiltViewModel
class DoctorDashboardViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow(DoctorDashboardState())
    val state: StateFlow<DoctorDashboardState> = _state.asStateFlow()

    init {
        loadDashboardData()
    }

    private fun loadDashboardData() {
        // Simulated data - replace with real repository calls
        _state.value = DoctorDashboardState(
            greeting = "Good morning",
            doctorName = "Dr. Adebayo",
            todayDate = "Tuesday, Aug 18",
            totalPatients = 156,
            todayAppointments = 12,
            pendingTasks = 5,
            activeEncounters = 3,
            completedToday = 7,
            urgentCount = 2,
            upcomingAppointments = listOf(
                AppointmentUi("1", "Chidi Okonkwo", "Consultation", "09:00 AM", "IN_PROGRESS", true),
                AppointmentUi("2", "Amina Bello", "Follow-up", "10:30 AM", "SCHEDULED"),
                AppointmentUi("3", "Emeka Nwosu", "Consultation", "11:00 AM", "SCHEDULED"),
                AppointmentUi("4", "Fatima Yusuf", "Prescription Review", "02:00 PM", "SCHEDULED"),
                AppointmentUi("5", "Tunde Adeyemi", "Emergency", "03:30 PM", "SCHEDULED", true),
            ),
            recentPatients = listOf(
                PatientUi("1", "Chidi Okonkwo", "MRN-00142", 45, "Today", "Hypertension"),
                PatientUi("2", "Amina Bello", "MRN-00287", 32, "Yesterday", "Diabetes Type 2"),
                PatientUi("3", "Emeka Nwosu", "MRN-00156", 58, "Aug 16", "Post-Op Review"),
            ),
            isLoading = false,
        )
    }
}
