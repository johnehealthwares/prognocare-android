package com.ehealthinformatics.prognocare.feature.dashboard.specialist

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
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Search
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
import com.ehealthinformatics.prognocare.designsystem.components.EmptyState
import com.ehealthinformatics.prognocare.designsystem.theme.AppThemeColors
import com.ehealthinformatics.prognocare.designsystem.theme.Spacing

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpecialistPatientListScreen(
    onBack: () -> Unit,
    onPatientClick: (String) -> Unit,
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedFilter by remember { mutableStateOf("All") }

    val patients = remember {
        listOf(
            SpecialistPatient("1", "Chidi Okonkwo", 45, "MRN-00142", "Type 2 Diabetes", "Aug 18, 2026", "Aug 25, 2026", true),
            SpecialistPatient("2", "Funke Adeleke", 38, "MRN-00189", "Hypothyroidism", "Aug 17, 2026", "Sep 1, 2026", true),
            SpecialistPatient("3", "Emeka Nwosu", 52, "MRN-00201", "Suspected Cushing's", "Aug 18, 2026", null, true),
            SpecialistPatient("4", "Amina Bello", 29, "MRN-00156", "PCOS", "Aug 16, 2026", "Aug 30, 2026", true),
            SpecialistPatient("5", "Yusuf Abdullahi", 61, "MRN-00234", "Type 1 Diabetes", "Aug 14, 2026", "Aug 28, 2026", true),
            SpecialistPatient("6", "Ngozi Okafor", 55, "MRN-00178", "Hyperthyroidism", "Aug 10, 2026", "Sep 5, 2026", true),
            SpecialistPatient("7", "Tunde Bakare", 48, "MRN-00212", "Metabolic Syndrome", "Aug 8, 2026", null, false),
            SpecialistPatient("8", "Fatima Hassan", 34, "MRN-00195", "Adrenal Insufficiency", "Aug 5, 2026", "Sep 10, 2026", true),
        )
    }

    val filterOptions = listOf("All", "Ongoing", "Completed")

    val filteredPatients = patients.filter { patient ->
        val matchesSearch = searchQuery.isEmpty() ||
                patient.name.contains(searchQuery, ignoreCase = true) ||
                patient.mrn.contains(searchQuery, ignoreCase = true) ||
                patient.condition.contains(searchQuery, ignoreCase = true)
        val matchesFilter = when (selectedFilter) {
            "All" -> true
            "Ongoing" -> patient.isOngoing
            "Completed" -> !patient.isOngoing
            else -> true
        }
        matchesSearch && matchesFilter
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My Patients") },
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
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
        ) {
            // ── Search Bar ──────────────────────────────────
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Spacing.lg, vertical = Spacing.sm),
                placeholder = { Text("Search patients...") },
                leadingIcon = {
                    Icon(
                        Icons.Outlined.Search,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                },
                singleLine = true,
                shape = RoundedCornerShape(Spacing.base),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
                    focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
                ),
            )

            // ── Filter Chips ────────────────────────────────
            androidx.compose.foundation.lazy.LazyRow(
                modifier = Modifier.padding(horizontal = Spacing.lg),
                horizontalArrangement = Arrangement.spacedBy(Spacing.sm),
            ) {
                items(filterOptions.size) { index ->
                    FilterChip(
                        selected = selectedFilter == filterOptions[index],
                        onClick = { selectedFilter = filterOptions[index] },
                        label = {
                            Text(
                                text = filterOptions[index],
                                style = MaterialTheme.typography.labelMedium,
                            )
                        },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                            selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer,
                        ),
                    )
                }
            }

            Spacer(modifier = Modifier.height(Spacing.sm))

            // ── Patient List ────────────────────────────────
            if (filteredPatients.isEmpty()) {
                EmptyState(
                    icon = Icons.Default.Person,
                    title = "No patients found",
                    message = if (searchQuery.isNotEmpty())
                        "No patients match \"$searchQuery\""
                    else
                        "No patients in this category yet",
                )
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(Spacing.sm),
                    contentPadding = androidx.compose.foundation.layout.PaddingValues(
                        horizontal = Spacing.lg,
                        vertical = Spacing.base,
                    ),
                ) {
                    items(filteredPatients) { patient ->
                        PatientCard(
                            patient = patient,
                            onClick = { onPatientClick(patient.mrn) },
                        )
                    }
                    item { Spacer(modifier = Modifier.height(Spacing.xxxl)) }
                }
            }
        }
    }
}

@Composable
private fun PatientCard(
    patient: SpecialistPatient,
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
            // ── Avatar ──────────────────────────────────────
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primaryContainer),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = patient.name.take(1),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    fontWeight = FontWeight.Bold,
                )
            }

            Spacer(modifier = Modifier.width(Spacing.md))

            // ── Patient Info ────────────────────────────────
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = patient.name,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold,
                )
                Text(
                    text = "${patient.mrn} · ${patient.age}y",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                Spacer(modifier = Modifier.height(Spacing.xxs))
                Text(
                    text = patient.condition,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Medium,
                )
            }

            // ── Status & Next Appointment ───────────────────
            Column(horizontalAlignment = Alignment.End) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = if (patient.isOngoing) Icons.Default.CheckCircle else Icons.Default.Person,
                        contentDescription = null,
                        tint = if (patient.isOngoing) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(16.dp),
                    )
                    Spacer(modifier = Modifier.width(Spacing.xs))
                    Text(
                        text = if (patient.isOngoing) "Ongoing" else "Completed",
                        style = MaterialTheme.typography.labelSmall,
                        color = if (patient.isOngoing) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
                if (patient.nextAppointment != null) {
                    Spacer(modifier = Modifier.height(Spacing.xs))
                    Text(
                        text = "Next: ${patient.nextAppointment}",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }
        }
    }
}
