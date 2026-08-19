package com.ehealthinformatics.prognocare.feature.dashboard.patient

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.Chat
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.Science
import androidx.compose.material.icons.outlined.MedicalServices
import androidx.compose.material.icons.outlined.Payment
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ehealthinformatics.prognocare.designsystem.components.SectionHeader
import com.ehealthinformatics.prognocare.designsystem.components.StatusBadge
import com.ehealthinformatics.prognocare.designsystem.components.StatusType
import com.ehealthinformatics.prognocare.designsystem.theme.KpiBlue
import com.ehealthinformatics.prognocare.designsystem.theme.KpiBlueLight
import com.ehealthinformatics.prognocare.designsystem.theme.KpiGreen
import com.ehealthinformatics.prognocare.designsystem.theme.KpiGreenLight
import com.ehealthinformatics.prognocare.designsystem.theme.KpiOrange
import com.ehealthinformatics.prognocare.designsystem.theme.KpiOrangeLight
import com.ehealthinformatics.prognocare.designsystem.theme.KpiPurple
import com.ehealthinformatics.prognocare.designsystem.theme.KpiPurpleLight
import com.ehealthinformatics.prognocare.designsystem.theme.NotificationBadge
import com.ehealthinformatics.prognocare.designsystem.theme.Primary
import com.ehealthinformatics.prognocare.designsystem.theme.Spacing

@Composable
fun PatientDashboardScreen(
    onNavigateToAppointments: () -> Unit,
    onNavigateToRecords: () -> Unit,
    onNavigateToMedications: () -> Unit,
    onNavigateToChat: () -> Unit,
    onNavigateToProfile: () -> Unit = {},
    viewModel: PatientDashboardViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    Scaffold { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(bottom = 100.dp),
            verticalArrangement = Arrangement.spacedBy(Spacing.base),
        ) {
            // ── Header ───────────────────────────────────────
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = Spacing.lg)
                        .padding(top = Spacing.xl),
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "${state.greeting},",
                                style = MaterialTheme.typography.bodyLarge,
                                color = Color(0xFF6B7280),
                            )
                            Text(
                                text = state.patientName,
                                style = MaterialTheme.typography.headlineMedium,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF1E293B),
                            )
                            Spacer(modifier = Modifier.height(Spacing.xs))
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = state.tagline,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Color(0xFF6B7280),
                                )
                                Spacer(modifier = Modifier.width(Spacing.xs))
                                Text(
                                    text = "💚",
                                    style = MaterialTheme.typography.bodyMedium,
                                )
                            }
                        }

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            // Notification bell with badge
                            Box {
                                IconButton(onClick = { /* notifications */ }) {
                                    Icon(
                                        imageVector = Icons.Default.Notifications,
                                        contentDescription = "Notifications",
                                        tint = Color(0xFF6B7280),
                                        modifier = Modifier.size(28.dp),
                                    )
                                }
                                Box(
                                    modifier = Modifier
                                        .align(Alignment.TopEnd)
                                        .size(20.dp)
                                        .clip(CircleShape)
                                        .background(NotificationBadge),
                                    contentAlignment = Alignment.Center,
                                ) {
                                    Text(
                                        text = "2",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = Color.White,
                                        fontWeight = FontWeight.Bold,
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.width(Spacing.sm))

                            // Avatar with photo placeholder
                            Box(
                                modifier = Modifier
                                    .size(52.dp)
                                    .clip(CircleShape)
                                    .background(KpiBlueLight)
                                    .clickable { onNavigateToProfile() },
                                contentAlignment = Alignment.Center,
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Person,
                                    contentDescription = null,
                                    tint = Primary,
                                    modifier = Modifier.size(28.dp),
                                )
                            }
                        }
                    }
                }
            }

            // ── Next Appointment Card ────────────────────────
            state.nextAppointment?.let { appt ->
                item {
                    NextAppointmentCard(
                        appointment = appt,
                        onClick = onNavigateToAppointments,
                        modifier = Modifier.padding(horizontal = Spacing.lg),
                    )
                }
            }

            // ── Health Overview ──────────────────────────────
            item {
                Column(modifier = Modifier.padding(horizontal = Spacing.lg)) {
                    SectionHeader(
                        title = "Health Overview",
                        actionText = "View All",
                        onActionClick = { /* view all health */ },
                    )
                    Spacer(modifier = Modifier.height(Spacing.md))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(Spacing.sm),
                    ) {
                        HealthOverviewCard(
                            title = "Medications",
                            value = "${state.activeMedications}",
                            subtitle = "Active",
                            icon = Icons.Outlined.MedicalServices,
                            iconTint = KpiGreen,
                            iconBg = KpiGreenLight,
                            subtitleColor = KpiGreen,
                            onClick = onNavigateToMedications,
                            modifier = Modifier.weight(1f),
                        )
                        HealthOverviewCard(
                            title = "Appointments",
                            value = "${state.upcomingAppointments}",
                            subtitle = "Upcoming",
                            icon = Icons.Outlined.CalendarMonth,
                            iconTint = KpiPurple,
                            iconBg = KpiPurpleLight,
                            subtitleColor = KpiPurple,
                            onClick = onNavigateToAppointments,
                            modifier = Modifier.weight(1f),
                        )
                        HealthOverviewCard(
                            title = "Lab Results",
                            value = "${state.labResults}",
                            subtitle = "Available",
                            icon = Icons.Outlined.Description,
                            iconTint = KpiBlue,
                            iconBg = KpiBlueLight,
                            subtitleColor = KpiBlue,
                            onClick = onNavigateToRecords,
                            modifier = Modifier.weight(1f),
                        )
                        HealthOverviewCard(
                            title = "Health Score",
                            value = "${state.healthScore}",
                            subtitle = state.healthScoreLabel,
                            icon = Icons.Default.CheckCircle,
                            iconTint = KpiOrange,
                            iconBg = KpiOrangeLight,
                            subtitleColor = KpiOrange,
                            onClick = { /* health score */ },
                            modifier = Modifier.weight(1f),
                        )
                    }
                }
            }

            // ── Quick Actions ─────────────────────────────────
            item {
                Column(modifier = Modifier.padding(horizontal = Spacing.lg)) {
                    SectionHeader(title = "Quick Actions")
                    Spacer(modifier = Modifier.height(Spacing.md))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(Spacing.sm),
                    ) {
                        QuickActionItem(
                            icon = Icons.Outlined.CalendarMonth,
                            label = "Book\nAppointment",
                            iconTint = KpiGreen,
                            iconBg = KpiGreenLight,
                            onClick = onNavigateToAppointments,
                            modifier = Modifier.weight(1f),
                        )
                        QuickActionItem(
                            icon = Icons.Outlined.Science,
                            label = "View\nResults",
                            iconTint = KpiPurple,
                            iconBg = KpiPurpleLight,
                            onClick = onNavigateToRecords,
                            modifier = Modifier.weight(1f),
                        )
                        QuickActionItem(
                            icon = Icons.Outlined.MedicalServices,
                            label = "My\nMedications",
                            iconTint = Color(0xFFEC4899),
                            iconBg = Color(0xFFFCE7F3),
                            onClick = onNavigateToMedications,
                            modifier = Modifier.weight(1f),
                        )
                        QuickActionItem(
                            icon = Icons.Outlined.Chat,
                            label = "Message\nDoctor",
                            iconTint = KpiBlue,
                            iconBg = KpiBlueLight,
                            onClick = onNavigateToChat,
                            modifier = Modifier.weight(1f),
                        )
                        QuickActionItem(
                            icon = Icons.Outlined.Payment,
                            label = "Billing &\nPayments",
                            iconTint = KpiOrange,
                            iconBg = KpiOrangeLight,
                            onClick = { /* billing */ },
                            modifier = Modifier.weight(1f),
                        )
                    }
                }
            }

            // ── Profile Completion Prompt ─────────────────────
            if (state.shouldShowProfilePrompt) {
                item {
                    ProfileCompletionCard(
                        modifier = Modifier.padding(horizontal = Spacing.lg),
                    )
                }
            }

            // ── Recent Records ────────────────────────────────
            item {
                Column(modifier = Modifier.padding(horizontal = Spacing.lg)) {
                    SectionHeader(
                        title = "Recent Records",
                        actionText = "View All",
                        onActionClick = onNavigateToRecords,
                    )
                    Spacer(modifier = Modifier.height(Spacing.md))
                }
            }

            items(state.recentRecords) { record ->
                RecentRecordItem(
                    record = record,
                    onClick = { /* view record */ },
                    modifier = Modifier.padding(horizontal = Spacing.lg),
                )
            }

            // ── Bottom spacer ─────────────────────────────────
            item { Spacer(modifier = Modifier.height(Spacing.xxl)) }
        }
    }
}

// ── Sub-components ────────────────────────────────────────────

@Composable
private fun NextAppointmentCard(
    appointment: PatientAppointment,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
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
            // Calendar icon
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(Spacing.md))
                    .background(KpiBlueLight),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    imageVector = Icons.Default.CalendarMonth,
                    contentDescription = null,
                    tint = Primary,
                    modifier = Modifier.size(24.dp),
                )
            }

            Spacer(modifier = Modifier.width(Spacing.md))

            // Appointment info
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Next Appointment",
                    style = MaterialTheme.typography.labelMedium,
                    color = Primary,
                    fontWeight = FontWeight.SemiBold,
                )
                Text(
                    text = appointment.providerName,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1E293B),
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.CalendarMonth,
                        contentDescription = null,
                        tint = Color(0xFF6B7280),
                        modifier = Modifier.size(14.dp),
                    )
                    Spacer(modifier = Modifier.width(Spacing.xs))
                    Text(
                        text = "${appointment.date} • ${appointment.time}",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFF6B7280),
                    )
                }
                Text(
                    text = appointment.type,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFF6B7280),
                )
            }

            // View Details button
            OutlinedButton(
                onClick = onClick,
                shape = RoundedCornerShape(Spacing.sm),
            ) {
                Text("View Details", style = MaterialTheme.typography.labelMedium)
            }
        }
    }
}

@Composable
private fun HealthOverviewCard(
    title: String,
    value: String,
    subtitle: String,
    icon: ImageVector,
    iconTint: Color,
    iconBg: Color,
    subtitleColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(Spacing.base),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Spacing.sm),
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(RoundedCornerShape(Spacing.sm))
                    .background(iconBg),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = iconTint,
                    modifier = Modifier.size(20.dp),
                )
            }

            Spacer(modifier = Modifier.height(Spacing.sm))

            Text(
                text = value,
                style = MaterialTheme.typography.headlineSmall,
                color = iconTint,
                fontWeight = FontWeight.Bold,
            )

            Text(
                text = title,
                style = MaterialTheme.typography.labelSmall,
                color = Color(0xFF6B7280),
            )

            Text(
                text = subtitle,
                style = MaterialTheme.typography.labelSmall,
                color = subtitleColor,
                fontWeight = FontWeight.Medium,
            )
        }
    }
}

@Composable
private fun QuickActionItem(
    icon: ImageVector,
    label: String,
    iconTint: Color,
    iconBg: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(Spacing.base),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Spacing.sm),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(iconBg),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = iconTint,
                    modifier = Modifier.size(22.dp),
                )
            }

            Spacer(modifier = Modifier.height(Spacing.xs))

            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                color = Color(0xFF1E293B),
                textAlign = TextAlign.Center,
                maxLines = 2,
            )
        }
    }
}

@Composable
private fun ProfileCompletionCard(
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(Spacing.base),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF0FDF4)),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Spacing.base),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Stay on top of your health",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1E293B),
                )
                Spacer(modifier = Modifier.height(Spacing.xs))
                Text(
                    text = "Complete your health profile to get personalized care.",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFF6B7280),
                )
                Spacer(modifier = Modifier.height(Spacing.md))
                TextButton(
                    onClick = { /* complete profile */ },
                    shape = RoundedCornerShape(Spacing.sm),
                ) {
                    Text(
                        text = "Complete Profile",
                        color = KpiGreen,
                        fontWeight = FontWeight.SemiBold,
                    )
                }
            }

            // Placeholder for illustration
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(Spacing.md))
                    .background(KpiGreenLight),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = null,
                    tint = KpiGreen,
                    modifier = Modifier.size(40.dp),
                )
            }
        }
    }
}

@Composable
private fun RecentRecordItem(
    record: PatientRecord,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val (iconBg, iconTint) = when (record.type) {
        "LAB_RESULTS" -> KpiBlueLight to KpiBlue
        "PROCEDURE" -> Color(0xFFFCE7F3) to Color(0xFFEC4899)
        "IMAGING" -> KpiPurpleLight to KpiPurple
        else -> KpiBlueLight to KpiBlue
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
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
            // Icon
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(iconBg),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    imageVector = when (record.type) {
                        "LAB_RESULTS" -> Icons.Outlined.Science
                        "PROCEDURE" -> Icons.Default.CheckCircle
                        else -> Icons.Outlined.Description
                    },
                    contentDescription = null,
                    tint = iconTint,
                    modifier = Modifier.size(22.dp),
                )
            }

            Spacer(modifier = Modifier.width(Spacing.md))

            // Record info
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = record.title,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF1E293B),
                )
                Text(
                    text = record.date,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFF6B7280),
                )
            }

            // Status / Action
            when (record.status) {
                RecordStatus.NORMAL -> {
                    StatusBadge(text = "Normal", type = StatusType.Completed)
                }
                RecordStatus.ABNORMAL -> {
                    StatusBadge(text = "Abnormal", type = StatusType.Urgent)
                }
                RecordStatus.VIEW_REPORT -> {
                    Text(
                        text = "View Report",
                        style = MaterialTheme.typography.labelMedium,
                        color = Primary,
                        fontWeight = FontWeight.Medium,
                    )
                }
                RecordStatus.PENDING -> {
                    StatusBadge(text = "Pending", type = StatusType.Pending)
                }
            }

            Spacer(modifier = Modifier.width(Spacing.sm))

            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = null,
                tint = Color(0xFF9CA3AF),
                modifier = Modifier.size(20.dp),
            )
        }
    }
}
