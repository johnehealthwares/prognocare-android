package com.ehealthinformatics.prognocare.feature.dashboard.patient

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.EventBusy
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ehealthinformatics.prognocare.designsystem.components.EmptyState
import com.ehealthinformatics.prognocare.designsystem.theme.Primary
import com.ehealthinformatics.prognocare.designsystem.components.StatusBadge
import com.ehealthinformatics.prognocare.designsystem.components.StatusType
import com.ehealthinformatics.prognocare.designsystem.theme.Spacing

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PatientAppointmentScreen(
    onBack: () -> Unit,
) {
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = listOf("Upcoming", "Past")

    val upcomingAppointments = remember {
        listOf(
            PatientAppointment(
                "1", "Dr. Adebayo", "Internal Medicine", "Consultation",
                "Aug 22, 2026", "10:00 AM", "Clinic A, Room 3", "SCHEDULED",
                "Follow-up for hypertension management",
            ),
            PatientAppointment(
                "2", "Dr. Fatima", "Endocrinology", "Follow-up",
                "Sep 5, 2026", "02:00 PM", "Clinic B, Room 5", "SCHEDULED",
                "Diabetes quarterly review",
            ),
            PatientAppointment(
                "3", "Dr. Ibrahim", "Cardiology", "ECG Check",
                "Sep 15, 2026", "09:30 AM", "Clinic A, Room 1", "CHECKED_IN",
                "Routine cardiac monitoring",
            ),
        )
    }

    val pastAppointments = remember {
        listOf(
            PatientAppointment(
                "4", "Dr. Fatima", "Endocrinology", "Follow-up",
                "Aug 10, 2026", "02:00 PM", "Clinic B, Room 5", "COMPLETED",
                "Diabetes review",
            ),
            PatientAppointment(
                "5", "Dr. Ibrahim", "Cardiology", "Consultation",
                "Jul 28, 2026", "09:30 AM", "Clinic A, Room 1", "COMPLETED",
                "ECG review",
            ),
            PatientAppointment(
                "6", "Dr. Adebayo", "Internal Medicine", "Consultation",
                "Jul 1, 2026", "11:00 AM", "Clinic A, Room 3", "COMPLETED",
                "Blood pressure check",
            ),
            PatientAppointment(
                "7", "Dr. Fatima", "Endocrinology", "Follow-up",
                "Jun 10, 2026", "03:00 PM", "Clinic B, Room 5", "CANCELLED",
                "Diabetes management",
            ),
        )
    }

    val displayedAppointments = if (selectedTab == 0) upcomingAppointments else pastAppointments

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My Appointments") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                ),
            )
        },
        floatingActionButton = {
            if (selectedTab == 0) {
                FloatingActionButton(
                    onClick = { /* book appointment */ },
                    containerColor = Primary,
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Book Appointment")
                }
            }
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
        ) {
            // ── Tab Row ──────────────────────────────────────
            TabRow(
                selectedTabIndex = selectedTab,
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.primary,
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        text = {
                            Text(
                                text = title,
                                fontWeight = if (selectedTab == index) FontWeight.SemiBold else FontWeight.Normal,
                            )
                        },
                    )
                }
            }

            if (displayedAppointments.isEmpty()) {
                EmptyState(
                    icon = if (selectedTab == 0) Icons.Default.CalendarMonth else Icons.Outlined.EventBusy,
                    title = if (selectedTab == 0) "No upcoming appointments" else "No past appointments",
                    message = if (selectedTab == 0)
                        "Tap the + button to book your next appointment"
                    else
                        "Your completed and cancelled appointments will appear here",
                )
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(Spacing.md),
                    contentPadding = androidx.compose.foundation.layout.PaddingValues(
                        horizontal = Spacing.lg,
                        vertical = Spacing.base,
                    ),
                ) {
                    items(displayedAppointments) { appointment ->
                        PatientAppointmentDetailCard(appointment = appointment)
                    }
                    item { Spacer(modifier = Modifier.height(Spacing.xxxl)) }
                }
            }
        }
    }
}

@Composable
private fun PatientAppointmentDetailCard(
    appointment: PatientAppointment,
    modifier: Modifier = Modifier,
) {
    val (statusType, statusText) = when (appointment.status) {
        "SCHEDULED" -> StatusType.Scheduled to "Scheduled"
        "CHECKED_IN" -> StatusType.InProgress to "Checked In"
        "COMPLETED" -> StatusType.Completed to "Completed"
        "CANCELLED" -> StatusType.Cancelled to "Cancelled"
        else -> StatusType.Pending to appointment.statusDisplay
    }

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(Spacing.base),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Spacing.base),
        ) {
            // ── Header Row ──────────────────────────────────
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = appointment.type,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.SemiBold,
                )
                StatusBadge(text = statusText, type = statusType)
            }

            Spacer(modifier = Modifier.height(Spacing.sm))

            // ── Provider Info ───────────────────────────────
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primaryContainer),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier.size(20.dp),
                    )
                }
                Spacer(modifier = Modifier.width(Spacing.md))
                Column {
                    Text(
                        text = appointment.providerName,
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.SemiBold,
                    )
                    Text(
                        text = appointment.providerSpecialty,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }

            Spacer(modifier = Modifier.height(Spacing.md))

            // ── Date, Time, Location ────────────────────────
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(Spacing.xl),
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.CalendarMonth,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(16.dp),
                    )
                    Spacer(modifier = Modifier.width(Spacing.xs))
                    Text(
                        text = "${appointment.date} · ${appointment.time}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }

            Spacer(modifier = Modifier.height(Spacing.xs))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(16.dp),
                )
                Spacer(modifier = Modifier.width(Spacing.xs))
                Text(
                    text = appointment.location,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }

            // ── Reason ──────────────────────────────────────
            if (appointment.reason != null) {
                Spacer(modifier = Modifier.height(Spacing.sm))
                Text(
                    text = appointment.reason,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                            RoundedCornerShape(Spacing.sm),
                        )
                        .padding(Spacing.sm),
                )
            }
        }
    }
}
