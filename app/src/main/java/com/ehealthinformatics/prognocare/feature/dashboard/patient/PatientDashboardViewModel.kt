package com.ehealthinformatics.prognocare.feature.dashboard.patient

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class PatientDashboardViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow(PatientDashboardState())
    val state: StateFlow<PatientDashboardState> = _state.asStateFlow()

    init {
        loadDashboardData()
    }

    private fun loadDashboardData() {
        _state.value = PatientDashboardState(
            greeting = "Good morning",
            patientName = "Chidi Okonkwo",
            mrn = "MRN-00142",
            todayDate = "Tuesday, Aug 18",
            tagline = "Take charge of your health",
            upcomingAppointments = 2,
            activeMedications = 3,
            labResults = 5,
            healthScore = 72,
            healthScoreLabel = "Good",
            nextAppointment = PatientAppointment(
                "1", "Dr. Adekunle Adebayo", "General Practice", "General Consultation",
                "Tue, Aug 20", "10:30 AM", "Clinic A, Room 3", "SCHEDULED",
                "Annual checkup",
            ),
            recentAppointments = listOf(
                PatientAppointment("2", "Dr. Fatima", "Endocrinology", "Follow-up",
                    "Aug 10, 2026", "02:00 PM", "Clinic B, Room 5", "COMPLETED",
                    "Diabetes review"),
                PatientAppointment("3", "Dr. Ibrahim", "Cardiology", "Consultation",
                    "Jul 28, 2026", "09:30 AM", "Clinic A, Room 1", "COMPLETED",
                    "ECG review"),
            ),
            currentMedications = listOf(
                PatientMedication("1", "Lisinopril", "10mg", "Once daily", "Oral",
                    "Dr. Adebayo", "Jan 15, 2026", instructions = "Take in the morning with water",
                    nextDose = "Tomorrow, 8:00 AM"),
                PatientMedication("2", "Metformin", "500mg", "Twice daily", "Oral",
                    "Dr. Fatima", "Mar 1, 2026", instructions = "Take with meals",
                    nextDose = "Today, 6:00 PM"),
                PatientMedication("3", "Aspirin", "81mg", "Once daily", "Oral",
                    "Dr. Adebayo", "Jan 15, 2026", instructions = "Low-dose for cardiac prophylaxis",
                    nextDose = "Tomorrow, 8:00 AM"),
            ),
            recentRecords = listOf(
                PatientRecord("1", "Blood Test", "LAB_RESULTS",
                    "Aug 10, 2024", "Dr. Adebayo", "All values within normal range",
                    true, RecordStatus.NORMAL),
                PatientRecord("2", "ECG", "PROCEDURE",
                    "Aug 05, 2024", "Dr. Ibrahim", "Normal sinus rhythm, no abnormalities",
                    true, RecordStatus.NORMAL),
                PatientRecord("3", "Chest X-Ray", "IMAGING",
                    "Jul 28, 2024", "Dr. Ibrahim", "No acute findings",
                    true, RecordStatus.VIEW_REPORT),
            ),
            healthAlerts = listOf(
                HealthAlert("1", "Medication Refill Due", "Lisinopril refill is due in 3 days",
                    AlertSeverity.WARNING, "Aug 18, 2026"),
            ),
            shouldShowProfilePrompt = true,
            isLoading = false,
        )
    }
}
