package com.ehealthinformatics.prognocare.feature.profile

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ehealthinformatics.prognocare.feature.splash.SplashViewModel
import com.ehealthinformatics.prognocare.navigation.UserRole
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserProfileViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
) : ViewModel() {

    private val _state = MutableStateFlow(UserProfileState())
    val state: StateFlow<UserProfileState> = _state.asStateFlow()

    private val _signOutComplete = MutableStateFlow(false)
    val signOutComplete: StateFlow<Boolean> = _signOutComplete.asStateFlow()

    init {
        loadProfile()
    }

    private fun loadProfile() {
        viewModelScope.launch {
            val prefs = context.getSharedPreferences("prognocare_auth", Context.MODE_PRIVATE)
            val roleOrdinal = prefs.getInt("user_role", 0)
            val role = UserRole.entries.getOrNull(roleOrdinal) ?: UserRole.Doctor

            // Mock profile data based on role
            val profile = getMockProfile(role)
            _state.update {
                it.copy(
                    isLoading = false,
                    profile = profile,
                )
            }
        }
    }

    fun signOut() {
        viewModelScope.launch {
            _state.update { it.copy(isSigningOut = true) }
            SplashViewModel.clearAuthState(context)
            _signOutComplete.value = true
        }
    }

    private fun getMockProfile(role: UserRole): UserProfile {
        return when (role) {
            UserRole.Doctor -> UserProfile(
                id = "DOC-001",
                name = "Dr. Chidi Okonkwo",
                email = "chidi.okonkwo@prognocare.com",
                phone = "+234 801 234 5678",
                role = UserRole.Doctor,
                department = "Internal Medicine",
                facility = "PrognoCare General Hospital",
                employeeId = "EMP-2024-001",
                joinDate = "Jan 15, 2022",
                licenseNumber = "MDCN/2021/12345",
                specialty = "Cardiology",
            )
            UserRole.Nurse -> UserProfile(
                id = "NRS-001",
                name = "Nurse Amara Eze",
                email = "amara.eze@prognocare.com",
                phone = "+234 802 345 6789",
                role = UserRole.Nurse,
                department = "Emergency Unit",
                facility = "PrognoCare General Hospital",
                employeeId = "EMP-2024-002",
                joinDate = "Mar 20, 2023",
                licenseNumber = "NDC/2022/67890",
            )
            UserRole.Patient -> UserProfile(
                id = "PAT-001",
                name = "Chidi Okonkwo",
                email = "chidi.okonkwo@email.com",
                phone = "+234 803 456 7890",
                role = UserRole.Patient,
                department = "Patient",
                facility = "PrognoCare General Hospital",
                joinDate = "Aug 10, 2024",
            )
            UserRole.Specialist -> UserProfile(
                id = "SPE-001",
                name = "Dr. Fatima Bello",
                email = "fatima.bello@prognocare.com",
                phone = "+234 804 567 8901",
                role = UserRole.Specialist,
                department = "Cardiology",
                facility = "PrognoCare Specialist Center",
                employeeId = "EMP-2024-003",
                joinDate = "Jun 1, 2021",
                licenseNumber = "MDCN/2020/54321",
                specialty = "Interventional Cardiology",
            )
            UserRole.Therapist -> UserProfile(
                id = "THR-001",
                name = "Ibrahim Musa",
                email = "ibrahim.musa@prognocare.com",
                phone = "+234 805 678 9012",
                role = UserRole.Therapist,
                department = "Physical Therapy",
                facility = "PrognoCare Rehabilitation Center",
                employeeId = "EMP-2024-004",
                joinDate = "Sep 15, 2023",
                licenseNumber = "PCN/2022/98765",
                specialty = "Musculoskeletal Therapy",
            )
            UserRole.Technician -> UserProfile(
                id = "TEC-001",
                name = "Kemi Adeyemi",
                email = "kemi.adeyemi@prognocare.com",
                phone = "+234 806 789 0123",
                role = UserRole.Technician,
                department = "Laboratory",
                facility = "PrognoCare General Hospital",
                employeeId = "EMP-2024-005",
                joinDate = "Feb 10, 2024",
            )
            UserRole.Finance -> UserProfile(
                id = "FIN-001",
                name = "Aisha Abdullahi",
                email = "aisha.abdullahi@prognocare.com",
                phone = "+234 807 890 1234",
                role = UserRole.Finance,
                department = "Finance & Billing",
                facility = "PrognoCare General Hospital",
                employeeId = "EMP-2024-006",
                joinDate = "Apr 5, 2022",
            )
            UserRole.Support -> UserProfile(
                id = "SUP-001",
                name = "Emeka Nwosu",
                email = "emeka.nwosu@prognocare.com",
                phone = "+234 808 901 2345",
                role = UserRole.Support,
                department = "Patient Services",
                facility = "PrognoCare General Hospital",
                employeeId = "EMP-2024-007",
                joinDate = "Jul 20, 2023",
            )
            UserRole.Admin -> UserProfile(
                id = "ADM-001",
                name = "Oluwaseun Bakare",
                email = "oluwaseun.bakare@prognocare.com",
                phone = "+234 809 012 3456",
                role = UserRole.Admin,
                department = "Administration",
                facility = "PrognoCare General Hospital",
                employeeId = "EMP-2024-008",
                joinDate = "Jan 1, 2021",
            )
        }
    }
}
