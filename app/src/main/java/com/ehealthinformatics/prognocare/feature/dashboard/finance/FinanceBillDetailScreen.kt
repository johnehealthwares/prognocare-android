package com.ehealthinformatics.prognocare.feature.dashboard.finance

import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
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
import com.ehealthinformatics.prognocare.designsystem.theme.Primary
import com.ehealthinformatics.prognocare.designsystem.theme.Spacing

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FinanceBillDetailScreen(
    billId: String,
    onBack: () -> Unit,
    onRecordPayment: (String) -> Unit,
    viewModel: FinanceDashboardViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsState()
    val bill = state.recentBills.find { it.id == billId }

    if (bill == null) {
        onBack()
        return
    }

    val billStatusType = when (bill.status) {
        BillStatus.DRAFT -> StatusType.Draft
        BillStatus.PENDING -> StatusType.Pending
        BillStatus.PARTIAL -> StatusType.InProgress
        BillStatus.PAID -> StatusType.Completed
        BillStatus.OVERDUE -> StatusType.Urgent
        BillStatus.CANCELLED -> StatusType.Cancelled
    }
    val billStatusText = bill.status.name.lowercase().replaceFirstChar { it.uppercase() }
    val items = bill.lineItems

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(bill.billNumber) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.surface),
            )
        },
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(Spacing.base),
        ) {
            // Bill Header
            item {
                Card(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = Spacing.lg),
                    shape = RoundedCornerShape(Spacing.base),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                ) {
                    Column(modifier = Modifier.fillMaxWidth().padding(Spacing.base)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Column {
                                Text(bill.patientName, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                                Text("MRN: ${bill.patientMrn}", style = MaterialTheme.typography.bodyMedium, color = Color(0xFF6B7280))
                            }
                            StatusBadge(text = billStatusText, type = billStatusType)
                        }
                        Spacer(modifier = Modifier.height(Spacing.md))
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Column {
                                Text("Bill Date", style = MaterialTheme.typography.labelSmall, color = Color(0xFF6B7280))
                                Text(bill.date, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Medium)
                            }
                            Column(horizontalAlignment = Alignment.End) {
                                Text("Due Date", style = MaterialTheme.typography.labelSmall, color = Color(0xFF6B7280))
                                Text(bill.dueDate, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Medium,
                                    color = if (bill.status == BillStatus.OVERDUE) Color(0xFFDC2626) else Color(0xFF1E293B))
                            }
                        }
                        if (bill.notes != null) {
                            Spacer(modifier = Modifier.height(Spacing.md))
                            Text("Notes", style = MaterialTheme.typography.labelSmall, color = Color(0xFF6B7280))
                            Text(bill.notes, style = MaterialTheme.typography.bodySmall, color = Color(0xFF475569))
                        }
                    }
                }
            }

            // Payment Summary
            item {
                Card(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = Spacing.lg),
                    shape = RoundedCornerShape(Spacing.base),
                    colors = CardDefaults.cardColors(containerColor = KpiBlueLight),
                ) {
                    Column(modifier = Modifier.fillMaxWidth().padding(Spacing.base)) {
                        Text("Payment Summary", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.SemiBold, color = Primary)
                        Spacer(modifier = Modifier.height(Spacing.md))
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Column {
                                Text("Total Amount", style = MaterialTheme.typography.labelSmall, color = Color(0xFF6B7280))
                                Text(bill.totalDisplay, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = Color(0xFF1E293B))
                            }
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("Paid", style = MaterialTheme.typography.labelSmall, color = Color(0xFF6B7280))
                                Text(bill.paidDisplay, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = KpiGreen)
                            }
                            Column(horizontalAlignment = Alignment.End) {
                                Text("Balance", style = MaterialTheme.typography.labelSmall, color = Color(0xFF6B7280))
                                Text(bill.balanceDisplay, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold,
                                    color = if (bill.balanceAmount > 0) KpiOrange else KpiGreen)
                            }
                        }
                    }
                }
            }

            // Line Items Header
            item {
                Column(modifier = Modifier.padding(horizontal = Spacing.lg)) {
                    Text("Line Items", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                }
            }

            // Line Items
            item {
                Column(
                    modifier = Modifier.padding(horizontal = Spacing.lg),
                    verticalArrangement = Arrangement.spacedBy(Spacing.sm),
                ) {
                    items.forEach { item ->
                        LineItemRow(item = item)
                    }
                }
            }

            // Total
            item {
                Card(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = Spacing.lg),
                    shape = RoundedCornerShape(Spacing.base),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                ) {
                    Column(modifier = Modifier.fillMaxWidth().padding(Spacing.base)) {
                        HorizontalDivider(color = Color(0xFFE2E8F0))
                        Spacer(modifier = Modifier.height(Spacing.md))
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                            Text("Total (${items.size} items)", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                            Text(bill.totalDisplay, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = Primary)
                        }
                    }
                }
            }

            // Record Payment Button
            if (bill.balanceAmount > 0) {
                item {
                    Button(
                        onClick = { onRecordPayment(bill.id) },
                        modifier = Modifier.fillMaxWidth().padding(horizontal = Spacing.lg).height(56.dp),
                        shape = RoundedCornerShape(Spacing.md),
                    ) {
                        Text("Record Payment", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(Spacing.xxl)) }
        }
    }
}

@Composable
private fun LineItemRow(item: BillLineItem) {
    val (iconBg, iconTint) = when (item.category) {
        ItemCategory.CONSULTATION -> KpiBlueLight to KpiBlue
        ItemCategory.PROCEDURE -> KpiGreenLight to KpiGreen
        ItemCategory.LAB_TEST -> Color(0xFFFCE7F3) to Color(0xFFEC4899)
        ItemCategory.IMAGING -> KpiPurpleLight to KpiPurple
        ItemCategory.MEDICATION -> KpiOrangeLight to KpiOrange
        ItemCategory.ROOM_CHARGE -> Color(0xFFE0E7FF) to Color(0xFF6366F1)
        ItemCategory.OTHER -> Color(0xFFF1F5F9) to Color(0xFF6B7280)
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(Spacing.sm),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.5.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(Spacing.md),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier.size(36.dp).clip(CircleShape).background(iconBg),
                contentAlignment = Alignment.Center,
            ) {
                Text(item.category.name.take(1), style = MaterialTheme.typography.labelMedium, color = iconTint, fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.width(Spacing.md))
            Column(modifier = Modifier.weight(1f)) {
                Text(item.description, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Medium)
                Text(
                    "${item.category.name.replace("_", " ").lowercase().replaceFirstChar { it.uppercase() }} · Qty: ${item.quantity}",
                    style = MaterialTheme.typography.bodySmall, color = Color(0xFF6B7280),
                )
            }
            Column(horizontalAlignment = Alignment.End) {
                Text(item.totalDisplay, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold, color = Color(0xFF1E293B))
                if (item.quantity > 1) {
                    Text("@ ${item.unitPriceDisplay} each", style = MaterialTheme.typography.labelSmall, color = Color(0xFF9CA3AF))
                }
            }
        }
    }
}
