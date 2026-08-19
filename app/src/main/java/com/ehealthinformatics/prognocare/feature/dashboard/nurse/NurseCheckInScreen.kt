package com.ehealthinformatics.prognocare.feature.dashboard.nurse

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.EventAvailable
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ehealthinformatics.prognocare.designsystem.components.StatusBadge
import com.ehealthinformatics.prognocare.designsystem.components.StatusType
import com.ehealthinformatics.prognocare.designsystem.theme.Spacing
import com.ehealthinformatics.prognocare.designsystem.theme.Tertiary

private data class CheckInPatient(
    val id: String,
    val patientName: String,
    val appointmentType: String,
    val appointmentTime: String,
    val providerName: String,
    val status: String,
    val vitalsComplete: Boolean,
)

private val sampleCheckIns = listOf(
    CheckInPatient("1", "Grace Obi", "Consultation", "09:30 AM", "Dr. Adebayo", "WAITING", false),
    CheckInPatient("2", "Kemi Adekunle", "Follow-up", "10:00 AM", "Dr. Fatima", "CHECKED_IN", true),
    CheckInPatient("3", "Yusuf Ali", "Checkup", "10:30 AM", "Dr. Ibrahim", "WAITING", false),
    CheckInPatient("4", "Blessing Eze", "Vaccination", "11:00 AM", "Dr. Adebayo", "WAITING", false),
    CheckInPatient("5", "Chidi Okonkwo", "Consultation", "09:00 AM", "Dr. Adebayo", "IN_PROGRESS", true),
    CheckInPatient("6", "Amina Bello", "Lab Review", "11:30 AM", "Dr. Fatima", "WAITING", false),
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NurseCheckInScreen(
    onBack: () -> Unit,
) {
    var selectedFilter by remember { mutableStateOf("All") }
    val filters = listOf("All", "Waiting", "Checked In", "In Progress")

    val filteredPatients = remember(selectedFilter) {
        when (selectedFilter) {
            "Waiting" -> sampleCheckIns.filter { it.status == "WAITING" }
            "Checked In" -> sampleCheckIns.filter { it.status == "CHECKED_IN" }
            "In Progress" -> sampleCheckIns.filter { it.status == "IN_PROGRESS" }
            else -> sampleCheckIns
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Patient Check-In",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.SemiBold,
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.surface),
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
        ) {
            // Filter chips
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Spacing.lg, vertical = Spacing.sm),
                horizontalArrangement = Arrangement.spacedBy(Spacing.sm),
            ) {
                filters.forEach { filter ->
                    FilterChip(
                        selected = selectedFilter == filter,
                        onClick = { selectedFilter = filter },
                        label = { Text(filter) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                        ),
                    )
                }
            }

            // Summary
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Spacing.lg, vertical = Spacing.sm),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = "${sampleCheckIns.count { it.status == "WAITING" }} waiting",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                Text(
                    text = "${sampleCheckIns.count { it.status == "CHECKED_IN" }} checked in",
                    style = MaterialTheme.typography.bodySmall,
                    color = Tertiary,
                )
            }

            LazyColumn(
                contentPadding = PaddingValues(horizontal = Spacing.lg),
                verticalArrangement = Arrangement.spacedBy(Spacing.sm),
            ) {
                items(filteredPatients) { patient ->
                    CheckInPatientCard(patient = patient)
                }
            }
        }
    }
}

@Composable
private fun CheckInPatientCard(
    patient: CheckInPatient,
    modifier: Modifier = Modifier,
) {
    val statusColor = when (patient.status) {
        "CHECKED_IN" -> Tertiary
        "IN_PROGRESS" -> MaterialTheme.colorScheme.primary
        else -> MaterialTheme.colorScheme.onSurfaceVariant
    }

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(Spacing.base),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Spacing.base),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            // Avatar
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primaryContainer),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = patient.patientName.take(2),
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    fontWeight = FontWeight.Bold,
                )
            }

            Spacer(modifier = Modifier.width(Spacing.md))

            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = patient.patientName,
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Medium,
                    )
                    Spacer(modifier = Modifier.width(Spacing.sm))
                    StatusBadge(
                        text = patient.status.replace("_", " "),
                        type = when (patient.status) {
                            "CHECKED_IN" -> StatusType.Active
                            "IN_PROGRESS" -> StatusType.InProgress
                            else -> StatusType.Pending
                        },
                    )
                }
                Spacer(modifier = Modifier.height(Spacing.xxs))
                Text(
                    text = "${patient.appointmentType} · ${patient.providerName}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                Spacer(modifier = Modifier.height(Spacing.xxs))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(6.dp)
                            .clip(CircleShape)
                            .background(if (patient.vitalsComplete) Tertiary else MaterialTheme.colorScheme.error),
                    )
                    Spacer(modifier = Modifier.width(Spacing.xs))
                    Text(
                        text = if (patient.vitalsComplete) "Vitals complete" else "Vitals pending",
                        style = MaterialTheme.typography.labelSmall,
                        color = if (patient.vitalsComplete) Tertiary else MaterialTheme.colorScheme.error,
                    )
                }
            }

            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = patient.appointmentTime,
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.SemiBold,
                )
                Spacer(modifier = Modifier.height(Spacing.xs))
                if (patient.status == "WAITING") {
                    TextButton(onClick = { /* check in */ }) {
                        Text("Check In", color = Tertiary, fontWeight = FontWeight.SemiBold)
                    }
                } else if (patient.status == "CHECKED_IN") {
                    TextButton(onClick = { /* start visit */ }) {
                        Text("Start Visit", fontWeight = FontWeight.SemiBold)
                    }
                }
            }
        }
    }
}
