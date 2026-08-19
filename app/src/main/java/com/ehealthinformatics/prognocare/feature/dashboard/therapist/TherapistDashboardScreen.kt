package com.ehealthinformatics.prognocare.feature.dashboard.therapist

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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Assessment
import androidx.compose.material.icons.outlined.EventAvailable
import androidx.compose.material.icons.outlined.FitnessCenter
import androidx.compose.material.icons.outlined.MedicalServices
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ehealthinformatics.prognocare.designsystem.components.AppointmentRow
import com.ehealthinformatics.prognocare.designsystem.components.DashboardKpiCard
import com.ehealthinformatics.prognocare.designsystem.components.DashboardQuickAction
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
fun TherapistDashboardScreen(
    onNavigateToSessions: () -> Unit,
    onNavigateToPatients: () -> Unit,
    onNavigateToPatientDetail: (String) -> Unit,
    onNavigateToChat: () -> Unit,
    onNavigateToProfile: () -> Unit = {},
    viewModel: TherapistDashboardViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    Scaffold(
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { /* new session */ },
                containerColor = Primary,
                contentColor = Color.White,
                shape = RoundedCornerShape(Spacing.lg),
                elevation = FloatingActionButtonDefaults.elevation(
                    defaultElevation = 6.dp,
                    pressedElevation = 8.dp,
                ),
            ) {
                Icon(Icons.Default.Add, contentDescription = null)
                Spacer(modifier = Modifier.width(Spacing.sm))
                Text("New Session", fontWeight = FontWeight.SemiBold)
            }
        },
    ) { innerPadding ->
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
                                text = state.therapistName,
                                style = MaterialTheme.typography.headlineMedium,
                                fontWeight = FontWeight.Bold,
                                color = Primary,
                            )
                            Spacer(modifier = Modifier.height(Spacing.xs))
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.CalendarMonth,
                                    contentDescription = null,
                                    tint = Primary,
                                    modifier = Modifier.size(16.dp),
                                )
                                Spacer(modifier = Modifier.width(Spacing.xs))
                                Text(
                                    text = state.todayDate,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Color(0xFF6B7280),
                                )
                            }
                            Spacer(modifier = Modifier.height(Spacing.xxs))
                            Text(
                                text = state.specialty,
                                style = MaterialTheme.typography.labelLarge,
                                color = Primary.copy(alpha = 0.7f),
                                fontWeight = FontWeight.Medium,
                            )
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
                                if (state.pendingAssessments > 0) {
                                    Box(
                                        modifier = Modifier
                                            .align(Alignment.TopEnd)
                                            .size(20.dp)
                                            .clip(CircleShape)
                                            .background(NotificationBadge),
                                        contentAlignment = Alignment.Center,
                                    ) {
                                        Text(
                                            text = "${state.pendingAssessments}",
                                            style = MaterialTheme.typography.labelSmall,
                                            color = Color.White,
                                            fontWeight = FontWeight.Bold,
                                        )
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.width(Spacing.sm))

                            // Avatar
                            Box(
                                modifier = Modifier
                                    .size(48.dp)
                                    .clip(CircleShape)
                                    .background(Primary)
                                    .clickable { onNavigateToProfile() },
                                contentAlignment = Alignment.Center,
                            ) {
                                Text(
                                    text = state.therapistName.take(1),
                                    style = MaterialTheme.typography.titleLarge,
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                )
                            }
                        }
                    }
                }
            }

            // ── KPI Grid ─────────────────────────────────────
            item {
                Column(
                    modifier = Modifier.padding(horizontal = Spacing.lg),
                    verticalArrangement = Arrangement.spacedBy(Spacing.sm),
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(Spacing.sm),
                    ) {
                        DashboardKpiCard(
                            title = "Today's Sessions",
                            value = "${state.todaySessions}",
                            icon = Icons.Outlined.EventAvailable,
                            iconTint = KpiBlue,
                            iconBg = KpiBlueLight,
                            onClick = onNavigateToSessions,
                            modifier = Modifier.weight(1f),
                        )
                        DashboardKpiCard(
                            title = "Active Plans",
                            value = "${state.activePlans}",
                            icon = Icons.Outlined.FitnessCenter,
                            iconTint = KpiPurple,
                            iconBg = KpiPurpleLight,
                            onClick = { /* active plans */ },
                            modifier = Modifier.weight(1f),
                        )
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(Spacing.sm),
                    ) {
                        DashboardKpiCard(
                            title = "Assessments Due",
                            value = "${state.pendingAssessments}",
                            icon = Icons.Outlined.Assessment,
                            iconTint = KpiOrange,
                            iconBg = KpiOrangeLight,
                            onClick = { /* assessments */ },
                            modifier = Modifier.weight(1f),
                        )
                        DashboardKpiCard(
                            title = "Completed",
                            value = "${state.completedToday}",
                            icon = Icons.Default.CheckCircle,
                            iconTint = KpiGreen,
                            iconBg = KpiGreenLight,
                            onClick = { /* completed */ },
                            modifier = Modifier.weight(1f),
                        )
                    }

                    // Progress bar
                    Spacer(modifier = Modifier.height(Spacing.md))
                    val totalTasks = state.todaySessions + state.completedToday
                    val progress = if (totalTasks > 0) state.completedToday.toFloat() / totalTasks else 0f
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = "Daily Progress",
                            style = MaterialTheme.typography.labelMedium,
                            color = Color(0xFF6B7280),
                        )
                        Text(
                            text = "${state.completedToday}/$totalTasks sessions",
                            style = MaterialTheme.typography.labelSmall,
                            color = Primary,
                            fontWeight = FontWeight.SemiBold,
                        )
                    }
                    Spacer(modifier = Modifier.height(Spacing.xs))
                    LinearProgressIndicator(
                        progress = { progress },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                            .clip(RoundedCornerShape(4.dp)),
                        color = KpiGreen,
                        trackColor = KpiGreenLight,
                    )
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
                        DashboardQuickAction(
                            icon = Icons.Outlined.EventAvailable,
                            label = "Start Session",
                            iconTint = KpiBlue,
                            iconBg = KpiBlueLight,
                            onClick = onNavigateToSessions,
                            modifier = Modifier.weight(1f),
                        )
                        DashboardQuickAction(
                            icon = Icons.Outlined.Assessment,
                            label = "Assessment",
                            iconTint = KpiOrange,
                            iconBg = KpiOrangeLight,
                            onClick = { /* assessment */ },
                            modifier = Modifier.weight(1f),
                        )
                        DashboardQuickAction(
                            icon = Icons.Outlined.FitnessCenter,
                            label = "Therapy Plans",
                            iconTint = KpiPurple,
                            iconBg = KpiPurpleLight,
                            onClick = { /* therapy plans */ },
                            modifier = Modifier.weight(1f),
                        )
                        DashboardQuickAction(
                            icon = Icons.Default.Person,
                            label = "Patients",
                            iconTint = KpiGreen,
                            iconBg = KpiGreenLight,
                            onClick = onNavigateToPatients,
                            modifier = Modifier.weight(1f),
                        )
                    }
                }
            }

            // ── Upcoming Sessions ─────────────────────────────
            item {
                Column(modifier = Modifier.padding(horizontal = Spacing.lg)) {
                    SectionHeader(
                        title = "Upcoming Sessions",
                        actionText = "View All",
                        onActionClick = onNavigateToSessions,
                    )
                    Spacer(modifier = Modifier.height(Spacing.md))
                }
            }

            items(state.upcomingSessions) { session ->
                SessionCard(
                    session = session,
                    onClick = { onNavigateToPatientDetail(session.patientMrn) },
                    onComplete = { viewModel.completeSession(session.id) },
                    modifier = Modifier.padding(horizontal = Spacing.lg),
                )
            }

            // ── Therapy Plans ─────────────────────────────────
            item {
                Column(modifier = Modifier.padding(horizontal = Spacing.lg)) {
                    SectionHeader(
                        title = "Active Therapy Plans",
                        actionText = "View All",
                        onActionClick = { /* view all plans */ },
                    )
                    Spacer(modifier = Modifier.height(Spacing.md))
                }
            }

            items(state.therapyPlans) { plan ->
                TherapyPlanCard(
                    plan = plan,
                    onClick = { onNavigateToPatientDetail(plan.patientMrn) },
                    modifier = Modifier.padding(horizontal = Spacing.lg),
                )
            }

            // ── Recent Assessments ────────────────────────────
            item {
                Column(modifier = Modifier.padding(horizontal = Spacing.lg)) {
                    SectionHeader(
                        title = "Recent Assessments",
                        actionText = "View All",
                        onActionClick = { /* view assessments */ },
                    )
                    Spacer(modifier = Modifier.height(Spacing.md))
                }
            }

            items(state.recentAssessments) { assessment ->
                AssessmentCard(
                    assessment = assessment,
                    onClick = { onNavigateToPatientDetail(assessment.patientMrn) },
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
private fun SessionCard(
    session: TherapySession,
    onClick: () -> Unit,
    onComplete: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val (statusType, statusText) = when (session.status) {
        SessionStatus.SCHEDULED -> StatusType.Scheduled to "Scheduled"
        SessionStatus.IN_PROGRESS -> StatusType.InProgress to "In Progress"
        SessionStatus.COMPLETED -> StatusType.Completed to "Completed"
        SessionStatus.CANCELLED -> StatusType.Cancelled to "Cancelled"
        SessionStatus.NO_SHOW -> StatusType.Urgent to "No Show"
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
            // Time
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.width(56.dp),
            ) {
                Text(
                    text = session.scheduledTime.split(" ")[0],
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = Primary,
                )
                Text(
                    text = session.scheduledTime.split(" ").getOrElse(1) { "" },
                    style = MaterialTheme.typography.labelSmall,
                    color = Color(0xFF6B7280),
                )
            }

            Spacer(modifier = Modifier.width(Spacing.md))

            // Avatar
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(KpiBlueLight),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = session.patientName.take(1),
                    style = MaterialTheme.typography.titleMedium,
                    color = Primary,
                    fontWeight = FontWeight.Bold,
                )
            }

            Spacer(modifier = Modifier.width(Spacing.md))

            // Session info
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = session.patientName,
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.SemiBold,
                    )
                    if (session.isUrgent) {
                        Spacer(modifier = Modifier.width(Spacing.xs))
                        StatusBadge(text = "Urgent", type = StatusType.Urgent)
                    }
                }
                Text(
                    text = "${session.sessionType.name.replace("_", " ").lowercase().replaceFirstChar { it.uppercase() }} · ${session.duration}",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFF6B7280),
                )
                if (session.notes != null) {
                    Text(
                        text = session.notes,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFF6B7280),
                        maxLines = 1,
                    )
                }
            }

            // Status
            StatusBadge(text = statusText, type = statusType)
        }
    }
}

@Composable
private fun TherapyPlanCard(
    plan: TherapyPlan,
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
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Spacing.base),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = plan.planName,
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.SemiBold,
                    )
                    Text(
                        text = "${plan.patientName} · ${plan.diagnosis}",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFF6B7280),
                    )
                }
                StatusBadge(
                    text = plan.statusDisplay,
                    type = when (plan.status) {
                        PlanStatus.ACTIVE -> StatusType.Active
                        PlanStatus.COMPLETED -> StatusType.Completed
                        PlanStatus.PAUSED -> StatusType.Pending
                        PlanStatus.CANCELLED -> StatusType.Cancelled
                    },
                )
            }

            Spacer(modifier = Modifier.height(Spacing.md))

            // Progress
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "Progress",
                    style = MaterialTheme.typography.labelMedium,
                    color = Color(0xFF6B7280),
                )
                Text(
                    text = "${plan.completedSessions}/${plan.totalSessions} sessions (${plan.progressPercent}%)",
                    style = MaterialTheme.typography.labelSmall,
                    color = Primary,
                    fontWeight = FontWeight.SemiBold,
                )
            }
            Spacer(modifier = Modifier.height(Spacing.xs))
            LinearProgressIndicator(
                progress = { plan.progressPercent / 100f },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .clip(RoundedCornerShape(3.dp)),
                color = when {
                    plan.progressPercent >= 75 -> KpiGreen
                    plan.progressPercent >= 50 -> KpiBlue
                    else -> KpiOrange
                },
                trackColor = Color(0xFFE2E8F0),
            )

            // Goals
            if (plan.goals.isNotEmpty()) {
                Spacer(modifier = Modifier.height(Spacing.md))
                Text(
                    text = "Goals",
                    style = MaterialTheme.typography.labelMedium,
                    color = Color(0xFF6B7280),
                )
                plan.goals.take(2).forEach { goal ->
                    Row(
                        modifier = Modifier.padding(top = Spacing.xs),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = null,
                            tint = KpiGreen,
                            modifier = Modifier.size(14.dp),
                        )
                        Spacer(modifier = Modifier.width(Spacing.xs))
                        Text(
                            text = goal,
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFF475569),
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun AssessmentCard(
    assessment: ProgressAssessment,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val trendColor = when (assessment.trend) {
        AssessmentTrend.IMPROVING -> KpiGreen
        AssessmentTrend.STABLE -> KpiBlue
        AssessmentTrend.DECLINING -> Color(0xFFDC2626)
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
            // Score circle
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(trendColor.copy(alpha = 0.1f)),
                contentAlignment = Alignment.Center,
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "${assessment.score}",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = trendColor,
                    )
                    Text(
                        text = "/${assessment.maxScore}",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color(0xFF6B7280),
                    )
                }
            }

            Spacer(modifier = Modifier.width(Spacing.md))

            // Info
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = assessment.patientName,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold,
                )
                Text(
                    text = "${assessment.assessmentType} · ${assessment.date}",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFF6B7280),
                )
                Text(
                    text = assessment.notes,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFF6B7280),
                    maxLines = 1,
                )
            }

            // Trend
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = assessment.trendDisplay,
                    style = MaterialTheme.typography.labelMedium,
                    color = trendColor,
                    fontWeight = FontWeight.SemiBold,
                )
            }
        }
    }
}
