package com.ehealthinformatics.prognocare.feature.dashboard.specialist

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SpecialistDashboardViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow(SpecialistDashboardState())
    val state: StateFlow<SpecialistDashboardState> = _state.asStateFlow()

    init {
        loadDashboardData()
    }

    private fun loadDashboardData() {
        _state.value = SpecialistDashboardState(
            greeting = "Good morning",
            specialistName = "Dr. Fatima Bello",
            specialty = "Endocrinology",
            todayDate = "Tuesday, Aug 18",
            pendingReferrals = 8,
            activePatients = 45,
            completedReviews = 12,
            urgentCases = 3,
            recentReferrals = listOf(
                SpecialistReferral(
                    "1", "Chidi Okonkwo", 45, "MRN-00142",
                    "Dr. Adebayo", "Persistent hyperglycemia despite metformin adjustment",
                    ReferralPriority.HIGH, ReferralStatus.PENDING, "Aug 18, 2026",
                    "Endocrinology", "Patient needs insulin therapy evaluation",
                ),
                SpecialistReferral(
                    "2", "Funke Adeleke", 38, "MRN-00189",
                    "Dr. Ibrahim", "Thyroid nodule evaluation - TSH elevated",
                    ReferralPriority.NORMAL, ReferralStatus.IN_REVIEW, "Aug 17, 2026",
                    "Endocrinology", "Ultrasound results attached",
                ),
                SpecialistReferral(
                    "3", "Emeka Nwosu", 52, "MRN-00201",
                    "Dr. Adebayo", "Suspected Cushing's syndrome - overnight dexamethasone suppression test",
                    ReferralPriority.URGENT, ReferralStatus.PENDING, "Aug 18, 2026",
                    "Endocrinology", "Rapid weight gain and moon facies observed",
                ),
                SpecialistReferral(
                    "4", "Amina Bello", 29, "MRN-00156",
                    "Dr. Fatima", "PCOS management - irregular cycles and hirsutism",
                    ReferralPriority.NORMAL, ReferralStatus.ACCEPTED, "Aug 16, 2026",
                    "Endocrinology", "Start treatment plan",
                ),
                SpecialistReferral(
                    "5", "Yusuf Abdullahi", 61, "MRN-00234",
                    "Dr. Ibrahim", "Type 1 diabetes - difficulty controlling glucose",
                    ReferralPriority.HIGH, ReferralStatus.COMPLETED, "Aug 14, 2026",
                    "Endocrinology", "Insulin pump therapy initiated",
                ),
            ),
            upcomingConsultations = listOf(
                SpecialistConsultation(
                    "1", "Chidi Okonkwo", "MRN-00142", "Follow-up",
                    "Aug 20, 2026", "10:00 AM", "Endocrinology Clinic, Room 2",
                    "SCHEDULED", "Insulin therapy review", true,
                ),
                SpecialistConsultation(
                    "2", "Funke Adeleke", "MRN-00189", "Consultation",
                    "Aug 21, 2026", "02:30 PM", "Endocrinology Clinic, Room 2",
                    "SCHEDULED", "Thyroid nodule biopsy results",
                ),
                SpecialistConsultation(
                    "3", "Emeka Nwosu", "MRN-00201", "Urgent Review",
                    "Aug 19, 2026", "09:00 AM", "Endocrinology Clinic, Room 1",
                    "SCHEDULED", "Cushing's evaluation", true,
                ),
            ),
            specialtyStats = SpecialtyStats(),
            isLoading = false,
        )
    }

    fun acceptReferral(referralId: String) {
        val current = _state.value
        _state.value = current.copy(
            recentReferrals = current.recentReferrals.map {
                if (it.id == referralId) it.copy(status = ReferralStatus.ACCEPTED) else it
            },
            pendingReferrals = (current.pendingReferrals - 1).coerceAtLeast(0),
            activePatients = current.activePatients + 1,
        )
    }

    fun declineReferral(referralId: String) {
        val current = _state.value
        _state.value = current.copy(
            recentReferrals = current.recentReferrals.map {
                if (it.id == referralId) it.copy(status = ReferralStatus.DECLINED) else it
            },
            pendingReferrals = (current.pendingReferrals - 1).coerceAtLeast(0),
        )
    }
}
