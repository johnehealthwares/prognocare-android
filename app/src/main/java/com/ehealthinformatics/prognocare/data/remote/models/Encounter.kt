package com.ehealthinformatics.prognocare.data.remote.models

import kotlinx.serialization.Serializable

@Serializable
data class Encounter(
    val id: String = "",
    val patientId: String = "",
    val visitId: String? = null,
    val encounterType: String = "",
    val providerId: String? = null,
    val providerName: String? = null,
    val encounterDatetime: String? = null,
    val reason: String? = null,
    val notes: String? = null,
    val createdAt: String? = null,
    val updatedAt: String? = null,
) {
    val typeDisplay: String
        get() = encounterType.replace("_", " ").lowercase().replaceFirstChar { it.uppercase() }
}

@Serializable
data class CreateEncounterDto(
    val patientId: String,
    val visitId: String? = null,
    val encounterType: String,
    val providerId: String? = null,
    val providerName: String? = null,
    val encounterDatetime: String? = null,
    val reason: String? = null,
    val notes: String? = null,
)

@Serializable
data class UpdateEncounterDto(
    val patientId: String? = null,
    val visitId: String? = null,
    val encounterType: String? = null,
    val providerId: String? = null,
    val providerName: String? = null,
    val encounterDatetime: String? = null,
    val reason: String? = null,
    val notes: String? = null,
)

enum class EncounterType {
    CONSULTATION, VITALS, HISTORY_AND_PHYSICAL, CLINICAL_NOTE,
    LAB_RESULTS, DISCHARGE, PROCEDURE, ADMISSION, OTHER,
}
