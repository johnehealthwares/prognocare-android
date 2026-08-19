package com.ehealthinformatics.prognocare.feature.dashboard.nurse

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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.MedicalServices
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ehealthinformatics.prognocare.designsystem.theme.Spacing
import com.ehealthinformatics.prognocare.designsystem.theme.Tertiary

private data class MedicationItem(
    val id: String,
    val patientName: String,
    val medicationName: String,
    val dosage: String,
    val route: String,
    val frequency: String,
    val scheduledTime: String,
    val status: String,
    val notes: String = "",
)

private val sampleMedications = listOf(
    MedicationItem("1", "Chidi Okonkwo", "Metformin", "500mg", "Oral", "Twice daily", "09:00 AM", "DUE"),
    MedicationItem("2", "Chidi Okonkwo", "Lisinopril", "10mg", "Oral", "Once daily", "09:00 AM", "DUE"),
    MedicationItem("3", "Amina Bello", "Amoxicillin", "250mg", "Oral", "Three times daily", "08:00 AM", "ADMINISTERED"),
    MedicationItem("4", "Emeka Nwosu", "Paracetamol", "500mg", "Oral", "As needed", "10:30 AM", "DUE"),
    MedicationItem("5", "Fatima Yusuf", "Omeprazole", "20mg", "Oral", "Once daily", "08:00 AM", "ADMINISTERED"),
    MedicationItem("6", "Tunde Adeyemi", "Morphine", "5mg", "IV", "Every 4 hours", "11:00 AM", "DUE"),
    MedicationItem("7", "Ngozi Okafor", "Iron Supplement", "325mg", "Oral", "Once daily", "12:00 PM", "DUE"),
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MedicationAdministrationScreen(
    onBack: () -> Unit,
) {
    var selectedFilter by remember { mutableStateOf("All") }
    var showAdminDialog by remember { mutableStateOf<MedicationItem?>(null) }
    val filters = listOf("All", "Due", "Administered", "Skipped")

    val filteredMeds = remember(selectedFilter) {
        when (selectedFilter) {
            "Due" -> sampleMedications.filter { it.status == "DUE" }
            "Administered" -> sampleMedications.filter { it.status == "ADMINISTERED" }
            else -> sampleMedications
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Medications",
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

            LazyColumn(
                contentPadding = PaddingValues(horizontal = Spacing.lg),
                verticalArrangement = Arrangement.spacedBy(Spacing.sm),
            ) {
                items(filteredMeds) { med ->
                    MedicationCard(
                        medication = med,
                        onAdminister = { showAdminDialog = med },
                    )
                }
            }
        }

        // Admin confirmation dialog
        showAdminDialog?.let { med ->
            AlertDialog(
                onDismissRequest = { showAdminDialog = null },
                title = { Text("Confirm Administration") },
                text = {
                    Column {
                        Text("Administer ${med.medicationName} ${med.dosage} to ${med.patientName}?")
                        Spacer(modifier = Modifier.height(Spacing.sm))
                        Text(
                            text = "Route: ${med.route} · Frequency: ${med.frequency}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                },
                confirmButton = {
                    TextButton(onClick = { showAdminDialog = null }) {
                        Text("Confirm", color = Tertiary)
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showAdminDialog = null }) {
                        Text("Cancel")
                    }
                },
            )
        }
    }
}

@Composable
private fun MedicationCard(
    medication: MedicationItem,
    onAdminister: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val isDue = medication.status == "DUE"
    val statusColor = if (isDue) MaterialTheme.colorScheme.error else Tertiary

    Card(
        modifier = modifier
            .fillMaxWidth()
            .then(if (isDue) Modifier.clickable(onClick = onAdminister) else Modifier),
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
            // Medication icon
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(RoundedCornerShape(Spacing.sm))
                    .background(statusColor.copy(alpha = 0.1f)),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    imageVector = Icons.Default.MedicalServices,
                    contentDescription = null,
                    tint = statusColor,
                    modifier = Modifier.size(22.dp),
                )
            }

            Spacer(modifier = Modifier.width(Spacing.md))

            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = medication.medicationName,
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Medium,
                    )
                    Spacer(modifier = Modifier.width(Spacing.sm))
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(Spacing.xs))
                            .background(statusColor.copy(alpha = 0.1f))
                            .padding(horizontal = Spacing.sm, vertical = Spacing.xxs),
                    ) {
                        Text(
                            text = medication.status,
                            style = MaterialTheme.typography.labelSmall,
                            color = statusColor,
                            fontWeight = FontWeight.Bold,
                        )
                    }
                }
                Spacer(modifier = Modifier.height(Spacing.xxs))
                Text(
                    text = "${medication.patientName} · ${medication.dosage} · ${medication.route}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                Text(
                    text = "Scheduled: ${medication.scheduledTime} · ${medication.frequency}",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }

            if (isDue) {
                IconButton(onClick = onAdminister) {
                    Icon(
                        Icons.Default.Check,
                        contentDescription = "Administer",
                        tint = Tertiary,
                        modifier = Modifier.size(28.dp),
                    )
                }
            }
        }
    }
}
