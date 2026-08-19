package com.ehealthinformatics.prognocare.feature.dashboard.admin

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class AdminDashboardViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow(AdminDashboardState())
    val state: StateFlow<AdminDashboardState> = _state.asStateFlow()

    init {
        loadDashboardData()
    }

    private fun loadDashboardData() {
        _state.value = AdminDashboardState(
            greeting = "Good morning",
            adminName = "Ibrahim Mohammed",
            todayDate = "Tuesday, Aug 18",
            totalPatients = 1247,
            checkedInToday = 42,
            waitingForCheckIn = 8,
            totalStaff = 54,
            activeVisits = 35,
            checkInQueue = listOf(
                AdminCheckIn("1", "Chidi Okonkwo", "MRN-00142", 45,
                    "09:00 AM", "Consultation", "Dr. Adebayo", "General Medicine",
                    CheckInStatus.WAITING),
                AdminCheckIn("2", "Funke Adeleke", "MRN-00189", 38,
                    "09:30 AM", "Follow-up", "Dr. Fatima", "Endocrinology",
                    CheckInStatus.CHECKED_IN, "09:25 AM"),
                AdminCheckIn("3", "Emeka Nwosu", "MRN-00201", 52,
                    "10:00 AM", "Procedure", "Dr. Ibrahim", "Cardiology",
                    CheckInStatus.WAITING),
                AdminCheckIn("4", "Amina Bello", "MRN-00156", 29,
                    "10:30 AM", "Consultation", "Dr. Adebayo", "General Medicine",
                    CheckInStatus.WAITING),
                AdminCheckIn("5", "Yusuf Abdullahi", "MRN-00234", 61,
                    "11:00 AM", "Lab Review", "Dr. Fatima", "Endocrinology",
                    CheckInStatus.WAITING),
                AdminCheckIn("6", "Ngozi Okafor", "MRN-00178", 55,
                    "08:30 AM", "Checkup", "Dr. Ibrahim", "Cardiology",
                    CheckInStatus.IN_VISIT, "08:25 AM"),
                AdminCheckIn("7", "Tunde Bakare", "MRN-00212", 48,
                    "08:00 AM", "Consultation", "Dr. Adebayo", "General Medicine",
                    CheckInStatus.CHECKED_OUT, "07:55 AM", "08:45 AM"),
                AdminCheckIn("8", "Fatima Hassan", "MRN-00195", 34,
                    "09:15 AM", "Follow-up", "Dr. Fatima", "Endocrinology",
                    CheckInStatus.NO_SHOW),
            ),
            isLoading = false,
        )
    }

    fun searchPatients(query: String) {
        if (query.isEmpty()) {
            _state.value = _state.value.copy(searchResults = emptyList())
            return
        }
        val allPatients = listOf(
            AdminPatient("1", "Chidi Okonkwo", 45, "MRN-00142", "Male", "+234 801 234 5678", "chidi@email.com", "Aug 18, 2026", PatientStatus.ACTIVE, true),
            AdminPatient("2", "Funke Adeleke", 38, "MRN-00189", "Female", "+234 802 345 6789", "funke@email.com", "Aug 17, 2026", PatientStatus.ACTIVE, true),
            AdminPatient("3", "Emeka Nwosu", 52, "MRN-00201", "Male", "+234 803 456 7890", "emeka@email.com", "Aug 16, 2026", PatientStatus.ACTIVE, true),
            AdminPatient("4", "Amina Bello", 29, "MRN-00156", "Female", "+234 804 567 8901", "amina@email.com", "Aug 15, 2026", PatientStatus.ACTIVE),
            AdminPatient("5", "Yusuf Abdullahi", 61, "MRN-00234", "Male", "+234 805 678 9012", "yusuf@email.com", "Aug 14, 2026", PatientStatus.ACTIVE),
            AdminPatient("6", "Ngozi Okafor", 55, "MRN-00178", "Female", "+234 806 789 0123", "ngozi@email.com", "Aug 13, 2026", PatientStatus.ACTIVE),
            AdminPatient("7", "Tunde Bakare", 48, "MRN-00212", "Male", "+234 807 890 1234", "tunde@email.com", "Aug 12, 2026", PatientStatus.ACTIVE),
            AdminPatient("8", "Fatima Hassan", 34, "MRN-00195", "Female", "+234 808 901 2345", "fatima@email.com", "Aug 11, 2026", PatientStatus.ACTIVE),
        )
        val results = allPatients.filter {
            it.name.contains(query, ignoreCase = true) ||
                    it.mrn.contains(query, ignoreCase = true) ||
                    it.phone.contains(query)
        }
        _state.value = _state.value.copy(searchResults = results)
    }

    fun checkInPatient(checkInId: String) {
        val current = _state.value
        _state.value = current.copy(
            checkInQueue = current.checkInQueue.map {
                if (it.id == checkInId) it.copy(
                    status = CheckInStatus.CHECKED_IN,
                    checkedInAt = "Now"
                ) else it
            },
            checkedInToday = current.checkedInToday + 1,
            waitingForCheckIn = (current.waitingForCheckIn - 1).coerceAtLeast(0),
        )
    }

    fun checkOutPatient(checkInId: String) {
        val current = _state.value
        _state.value = current.copy(
            checkInQueue = current.checkInQueue.map {
                if (it.id == checkInId) it.copy(
                    status = CheckInStatus.CHECKED_OUT,
                    checkedOutAt = "Now"
                ) else it
            },
            activeVisits = (current.activeVisits - 1).coerceAtLeast(0),
        )
    }
}
