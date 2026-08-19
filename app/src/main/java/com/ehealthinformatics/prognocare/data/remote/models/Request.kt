package com.ehealthinformatics.prognocare.data.remote.models

import kotlinx.serialization.Serializable

@Serializable
data class ClinicalRequest(
    val id: String = "",
    val patientId: String = "",
    val patientName: String = "",
    val encounterId: String? = null,
    val visitId: String? = null,
    val requestType: String = "",
    val priority: String = "ROUTINE",
    val status: String = "REQUESTED",
    val orderingProviderId: String? = null,
    val orderingProviderName: String? = null,
    val diagnosis: String? = null,
    val clinicalNotes: String? = null,
    val requestedAt: String? = null,
    val items: List<String> = emptyList(),
    val syncStatus: String? = null,
    val externalOrderId: String? = null,
    val externalReference: String? = null,
    val createdAt: String? = null,
    val updatedAt: String? = null,
) {
    val typeDisplay: String
        get() = requestType.replace("_", " ").lowercase().replaceFirstChar { it.uppercase() }

    val statusDisplay: String
        get() = status.replace("_", " ").lowercase().replaceFirstChar { it.uppercase() }

    val priorityDisplay: String
        get() = priority.replace("_", " ").lowercase().replaceFirstChar { it.uppercase() }

    val isOpen: Boolean
        get() = status in listOf("REQUESTED", "IN_PROGRESS")

    val isTerminal: Boolean
        get() = status in listOf("COMPLETED", "CANCELLED", "REJECTED")
}

@Serializable
data class CreateRequestDto(
    val patientId: String,
    val patientName: String? = null,
    val encounterId: String? = null,
    val visitId: String? = null,
    val requestType: String,
    val priority: String = "ROUTINE",
    val orderingProviderId: String? = null,
    val orderingProviderName: String? = null,
    val diagnosis: String? = null,
    val clinicalNotes: String? = null,
    val requestedAt: String? = null,
    val items: List<String> = emptyList(),
)

@Serializable
data class UpdateRequestDto(
    val patientId: String? = null,
    val patientName: String? = null,
    val encounterId: String? = null,
    val visitId: String? = null,
    val priority: String? = null,
    val orderingProviderId: String? = null,
    val orderingProviderName: String? = null,
    val diagnosis: String? = null,
    val clinicalNotes: String? = null,
    val items: List<String>? = null,
)

@Serializable
data class TransitionRequestStatusDto(
    val status: String,
    val reason: String? = null,
)

@Serializable
data class AddRequestNoteDto(
    val note: String,
)

@Serializable
data class SyncRequestDto(
    val externalOrderId: String? = null,
    val externalReference: String? = null,
)

@Serializable
data class RequestHistoryEntry(
    val id: String = "",
    val requestId: String = "",
    val fromStatus: String? = null,
    val toStatus: String = "",
    val reason: String? = null,
    val actorName: String? = null,
    val actorId: String? = null,
    val createdAt: String? = null,
)

enum class RequestType {
    PRESCRIPTION, LAB, RADIOLOGY, OTHER_TEST,
}

enum class RequestStatus {
    REQUESTED, IN_PROGRESS, COMPLETED, CANCELLED, REJECTED,
}
