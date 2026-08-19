package com.ehealthinformatics.prognocare.feature.dashboard.specialist

data class SpecialistDashboardState(
    val greeting: String = "",
    val specialistName: String = "Dr. Fatima Bello",
    val specialty: String = "Endocrinology",
    val todayDate: String = "Tuesday, Aug 18",
    val pendingReferrals: Int = 0,
    val activePatients: Int = 0,
    val completedReviews: Int = 0,
    val urgentCases: Int = 0,
    val recentReferrals: List<SpecialistReferral> = emptyList(),
    val upcomingConsultations: List<SpecialistConsultation> = emptyList(),
    val specialtyStats: SpecialtyStats = SpecialtyStats(),
    val isLoading: Boolean = true,
    val error: String? = null,
)

data class SpecialistReferral(
    val id: String,
    val patientName: String,
    val patientAge: Int,
    val patientMrn: String,
    val referringDoctor: String,
    val referralReason: String,
    val priority: ReferralPriority,
    val status: ReferralStatus,
    val dateReceived: String,
    val specialty: String,
    val notes: String? = null,
) {
    val priorityDisplay: String
        get() = priority.name.lowercase().replaceFirstChar { it.uppercase() }
    val statusDisplay: String
        get() = status.name.replace("_", " ").lowercase().replaceFirstChar { it.uppercase() }
}

enum class ReferralPriority {
    URGENT, HIGH, NORMAL, LOW
}

enum class ReferralStatus {
    PENDING, IN_REVIEW, ACCEPTED, DECLINED, COMPLETED
}

data class SpecialistConsultation(
    val id: String,
    val patientName: String,
    val patientMrn: String,
    val type: String,
    val date: String,
    val time: String,
    val location: String,
    val status: String,
    val reason: String? = null,
    val isUrgent: Boolean = false,
) {
    val statusDisplay: String
        get() = status.replace("_", " ").lowercase().replaceFirstChar { it.uppercase() }
}

data class SpecialtyStats(
    val totalReferralsThisMonth: Int = 24,
    val avgResponseTime: String = "2.3 hrs",
    val acceptanceRate: String = "87%",
    val patientSatisfaction: String = "4.8/5",
    val commonConditions: List<String> = listOf(
        "Diabetes Management",
        "Thyroid Disorders",
        "Hormonal Imbalances",
        "Metabolic Syndrome",
    ),
)

data class SpecialistPatient(
    val id: String,
    val name: String,
    val age: Int,
    val mrn: String,
    val condition: String,
    val lastVisit: String,
    val nextAppointment: String? = null,
    val isOngoing: Boolean = true,
)
