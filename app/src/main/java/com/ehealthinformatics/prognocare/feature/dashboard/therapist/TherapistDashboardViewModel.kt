package com.ehealthinformatics.prognocare.feature.dashboard.therapist

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class TherapistDashboardViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow(TherapistDashboardState())
    val state: StateFlow<TherapistDashboardState> = _state.asStateFlow()

    init {
        loadDashboardData()
    }

    private fun loadDashboardData() {
        _state.value = TherapistDashboardState(
            greeting = "Good morning",
            therapistName = "Dr. Grace Obi",
            specialty = "Physical Therapy",
            todayDate = "Tuesday, Aug 18",
            todaySessions = 6,
            activePlans = 12,
            pendingAssessments = 4,
            completedToday = 3,
            upcomingSessions = listOf(
                TherapySession(
                    "1", "Chidi Okonkwo", "MRN-00142", SessionType.FOLLOW_UP,
                    "10:00 AM", "45 min", "PT Clinic, Room 1", SessionStatus.SCHEDULED,
                    "Post-surgery rehabilitation", true,
                ),
                TherapySession(
                    "2", "Funke Adeleke", "MRN-00189", SessionType.TREATMENT,
                    "11:00 AM", "30 min", "PT Clinic, Room 2", SessionStatus.SCHEDULED,
                    "Lower back pain therapy",
                ),
                TherapySession(
                    "3", "Emeka Nwosu", "MRN-00201", SessionType.REHABILITATION,
                    "02:00 PM", "60 min", "PT Clinic, Room 3", SessionStatus.SCHEDULED,
                    "Knee replacement rehab", true,
                ),
                TherapySession(
                    "4", "Amina Bello", "MRN-00156", SessionType.INITIAL_ASSESSMENT,
                    "03:30 PM", "45 min", "PT Clinic, Room 1", SessionStatus.SCHEDULED,
                    "New patient assessment",
                ),
            ),
            activePatients = listOf(
                TherapyPatient("1", "Chidi Okonkwo", 45, "MRN-00142",
                    "Post-ACL Reconstruction", "Rehabilitation", 8, 12, "Aug 20, 2026", 67),
                TherapyPatient("2", "Funke Adeleke", 38, "MRN-00189",
                    "Chronic Lower Back Pain", "Pain Management", 5, 10, "Aug 19, 2026", 50),
                TherapyPatient("3", "Emeka Nwosu", 52, "MRN-00201",
                    "Total Knee Replacement", "Rehabilitation", 10, 20, "Aug 18, 2026", 50),
                TherapyPatient("4", "Ngozi Okafor", 55, "MRN-00178",
                    "Shoulder Impingement", "Strengthening", 3, 8, "Aug 21, 2026", 38),
            ),
            recentAssessments = listOf(
                ProgressAssessment("1", "Chidi Okonkwo", "MRN-00142",
                    "Range of Motion", "Aug 17, 2026", 78, 100,
                    "Good improvement in flexion, continue current plan", AssessmentTrend.IMPROVING),
                ProgressAssessment("2", "Funke Adeleke", "MRN-00189",
                    "Pain Scale", "Aug 16, 2026", 4, 10,
                    "Pain reduced from 7 to 4, medication effective", AssessmentTrend.IMPROVING),
                ProgressAssessment("3", "Emeka Nwosu", "MRN-00201",
                    "Functional Mobility", "Aug 15, 2026", 65, 100,
                    "Can walk 50m with walker, progress as expected", AssessmentTrend.STABLE),
            ),
            therapyPlans = listOf(
                TherapyPlan("1", "Chidi Okonkwo", "MRN-00142",
                    "ACL Recovery Program", "Post-ACL Reconstruction",
                    "Aug 1, 2026", "Oct 30, 2026", 12, 8, PlanStatus.ACTIVE,
                    listOf("Restore full range of motion", "Strengthen quadriceps", "Return to daily activities")),
                TherapyPlan("2", "Funke Adeleke", "MRN-00189",
                    "Back Pain Management", "Chronic Lower Back Pain",
                    "Aug 5, 2026", "Nov 5, 2026", 10, 5, PlanStatus.ACTIVE,
                    listOf("Reduce pain to 3/10", "Improve core strength", "Ergonomic education")),
                TherapyPlan("3", "Emeka Nwosu", "MRN-00201",
                    "Knee Replacement Rehab", "Total Knee Replacement",
                    "Jul 15, 2026", "Jan 15, 2027", 20, 10, PlanStatus.ACTIVE,
                    listOf("Walk independently", "Climb stairs", "Return to work")),
            ),
            isLoading = false,
        )
    }

    fun completeSession(sessionId: String) {
        val current = _state.value
        _state.value = current.copy(
            upcomingSessions = current.upcomingSessions.map {
                if (it.id == sessionId) it.copy(status = SessionStatus.COMPLETED) else it
            },
            completedToday = current.completedToday + 1,
            todaySessions = (current.todaySessions - 1).coerceAtLeast(0),
        )
    }

    fun sendNotification(patientMrn: String, message: String) {
        // In a real app, this would send a notification to the patient
    }
}
