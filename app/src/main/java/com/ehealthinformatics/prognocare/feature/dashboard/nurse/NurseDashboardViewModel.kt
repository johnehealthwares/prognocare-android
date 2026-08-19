package com.ehealthinformatics.prognocare.feature.dashboard.nurse

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class NurseDashboardViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow(NurseDashboardState())
    val state: StateFlow<NurseDashboardState> = _state.asStateFlow()

    init {
        loadDashboardData()
    }

    private fun loadDashboardData() {
        _state.value = NurseDashboardState(
            greeting = "Good morning",
            nurseName = "Nurse Amina",
            todayDate = "Tuesday, Aug 18",
            patientsCheckedIn = 8,
            vitalsToRecord = 5,
            medsToAdminister = 12,
            pendingTasks = 7,
            completedToday = 15,
            urgentTasks = 2,
            taskQueue = listOf(
                NurseTask("1", "Chidi Okonkwo", "PT-001", NurseTaskType.VITALS,
                    "Record pre-consultation vitals", TaskPriority.HIGH, "09:00 AM", TaskStatus.PENDING),
                NurseTask("2", "Amina Bello", "PT-002", NurseTaskType.MEDICATION,
                    "Administer Metformin 500mg", TaskPriority.NORMAL, "09:30 AM", TaskStatus.PENDING),
                NurseTask("3", "Emeka Nwosu", "PT-003", NurseTaskType.CHECK_IN,
                    "Check in for follow-up appointment", TaskPriority.NORMAL, "10:00 AM", TaskStatus.PENDING),
                NurseTask("4", "Fatima Yusuf", "PT-004", NurseTaskType.MEDICATION,
                    "Administer Lisinopril 10mg", TaskPriority.HIGH, "10:30 AM", TaskStatus.IN_PROGRESS),
                NurseTask("5", "Tunde Adeyemi", "PT-005", NurseTaskType.VITALS,
                    "Record post-procedure vitals", TaskPriority.URGENT, "11:00 AM", TaskStatus.PENDING),
                NurseTask("6", "Ngozi Okafor", "PT-006", NurseTaskType.ASSESSMENT,
                    "Pain assessment and documentation", TaskPriority.NORMAL, "11:30 AM", TaskStatus.PENDING),
                NurseTask("7", "Ibrahim Mohammed", "PT-007", NurseTaskType.SPECIMEN,
                    "Collect blood sample for lab", TaskPriority.HIGH, "02:00 PM", TaskStatus.PENDING),
            ),
            upcomingCheckIns = listOf(
                NurseCheckIn("1", "Grace Obi", "PT-008", "09:30 AM", "Consultation", "Dr. Adebayo", false, false),
                NurseCheckIn("2", "Kemi Adekunle", "PT-009", "10:00 AM", "Follow-up", "Dr. Fatima", true, false),
                NurseCheckIn("3", "Yusuf Ali", "PT-010", "10:30 AM", "Checkup", "Dr. Ibrahim", false, false),
                NurseCheckIn("4", "Blessing Eze", "PT-011", "11:00 AM", "Vaccination", "Dr. Adebayo", false, false),
            ),
            recentVitals = listOf(
                VitalsRecord("1", "Kemi Adekunle", "08:45 AM", "36.8°C", "128", "82", "72", "16", "98%", "68kg", "165cm"),
                VitalsRecord("2", "Chidi Okonkwo", "08:30 AM", "37.1°C", "142", "90", "78", "18", "97%", "82kg", "175cm"),
            ),
            isLoading = false,
        )
    }

    fun completeTask(taskId: String) {
        val current = _state.value
        val updatedTasks = current.taskQueue.map { task ->
            if (task.id == taskId) task.copy(status = TaskStatus.COMPLETED) else task
        }
        val completedCount = updatedTasks.count { it.status == TaskStatus.COMPLETED }
        _state.value = current.copy(
            taskQueue = updatedTasks,
            completedToday = current.completedToday + 1,
            pendingTasks = current.pendingTasks - 1,
        )
    }
}
