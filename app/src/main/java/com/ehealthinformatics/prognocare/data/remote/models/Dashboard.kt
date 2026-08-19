package com.ehealthinformatics.prognocare.data.remote.models

import kotlinx.serialization.Serializable

@Serializable
data class DashboardSummary(
    val date: String = "",
    val totalAppointments: Int = 0,
    val scheduledAppointments: Int = 0,
    val checkedInAppointments: Int = 0,
    val inProgressAppointments: Int = 0,
    val completedAppointments: Int = 0,
    val cancelledAppointments: Int = 0,
    val noShowAppointments: Int = 0,
    val totalPatients: Int = 0,
    val activeVisits: Int = 0,
    val pendingRequests: Int = 0,
    val providerLoad: List<ProviderLoad> = emptyList(),
    val upcomingAppointments: List<Appointment> = emptyList(),
)

@Serializable
data class ProviderLoad(
    val providerId: String = "",
    val providerName: String = "",
    val appointmentCount: Int = 0,
    val activeCount: Int = 0,
)
