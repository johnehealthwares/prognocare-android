package com.ehealthinformatics.prognocare.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ListAlt
import androidx.compose.material.icons.filled.Payment
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.Chat
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.Event
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.ListAlt
import androidx.compose.material.icons.outlined.Payment
import androidx.compose.material.icons.outlined.People
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.hilt.navigation.compose.hiltViewModel
import com.ehealthinformatics.prognocare.feature.dashboard.specialist.SpecialistDashboardScreen
import com.ehealthinformatics.prognocare.feature.dashboard.specialist.SpecialistReferralScreen
import com.ehealthinformatics.prognocare.feature.dashboard.specialist.SpecialistPatientListScreen
import com.ehealthinformatics.prognocare.feature.dashboard.therapist.TherapistDashboardScreen
import com.ehealthinformatics.prognocare.feature.dashboard.therapist.TherapistSessionScreen
import com.ehealthinformatics.prognocare.feature.dashboard.therapist.TherapistPatientListScreen
import com.ehealthinformatics.prognocare.feature.dashboard.finance.FinanceDashboardScreen
import com.ehealthinformatics.prognocare.feature.dashboard.support.SupportDashboardScreen
import com.ehealthinformatics.prognocare.feature.dashboard.technician.TechnicianDashboardScreen
import com.ehealthinformatics.prognocare.feature.dashboard.finance.FinanceBillDetailScreen
import com.ehealthinformatics.prognocare.feature.dashboard.admin.AdminDashboardScreen
import com.ehealthinformatics.prognocare.feature.auth.LoginScreen
import com.ehealthinformatics.prognocare.feature.chat.ChatScreen
import com.ehealthinformatics.prognocare.feature.chat.ConversationListScreen
import com.ehealthinformatics.prognocare.feature.dashboard.doctor.DoctorDashboardScreen
import com.ehealthinformatics.prognocare.feature.dashboard.doctor.DoctorAppointmentScreen
import com.ehealthinformatics.prognocare.feature.dashboard.doctor.DoctorPatientListScreen
import com.ehealthinformatics.prognocare.feature.dashboard.doctor.DoctorPatientDetailScreen
import com.ehealthinformatics.prognocare.feature.dashboard.nurse.NurseDashboardScreen
import com.ehealthinformatics.prognocare.feature.dashboard.nurse.VitalsRecordingScreen
import com.ehealthinformatics.prognocare.feature.dashboard.nurse.MedicationAdministrationScreen
import com.ehealthinformatics.prognocare.feature.dashboard.nurse.NurseCheckInScreen
import com.ehealthinformatics.prognocare.feature.dashboard.nurse.NurseTaskListScreen
import com.ehealthinformatics.prognocare.feature.dashboard.patient.PatientDashboardScreen
import com.ehealthinformatics.prognocare.feature.dashboard.patient.PatientAppointmentScreen
import com.ehealthinformatics.prognocare.feature.dashboard.patient.PatientRecordsScreen
import com.ehealthinformatics.prognocare.feature.dashboard.patient.PatientMedicationsScreen
import com.ehealthinformatics.prognocare.feature.profile.UserProfileScreen
import com.ehealthinformatics.prognocare.feature.splash.SplashDestination
import com.ehealthinformatics.prognocare.feature.splash.SplashScreen
import com.ehealthinformatics.prognocare.feature.splash.SplashViewModel

private const val ANIM_DURATION = 300

private val enterTransition: EnterTransition = fadeIn(animationSpec = tween(ANIM_DURATION))
private val exitTransition: ExitTransition = fadeOut(animationSpec = tween(ANIM_DURATION))
private val popEnterTransition: EnterTransition = fadeIn(animationSpec = tween(ANIM_DURATION))
private val popExitTransition: ExitTransition = fadeOut(animationSpec = tween(ANIM_DURATION))

// ── Bottom Nav Items ──────────────────────────────────────────

data class BottomNavItem(
    val route: String,
    val label: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
)

val DoctorBottomNav = listOf(
    BottomNavItem(DoctorRoutes.DASHBOARD, "Home", Icons.Filled.Home, Icons.Outlined.Home),
    BottomNavItem(DoctorRoutes.PATIENT_LIST, "Patients", Icons.Filled.People, Icons.Outlined.People),
    BottomNavItem(DoctorRoutes.APPOINTMENTS, "Schedule", Icons.Filled.ListAlt, Icons.Outlined.ListAlt),
    BottomNavItem(ChatRoutes.CONVERSATIONS, "Chat", Icons.Filled.Chat, Icons.Outlined.Chat),
)

val NurseBottomNav = listOf(
    BottomNavItem(NurseRoutes.DASHBOARD, "Home", Icons.Filled.Home, Icons.Outlined.Home),
    BottomNavItem(NurseRoutes.PATIENT_LIST, "Patients", Icons.Filled.People, Icons.Outlined.People),
    BottomNavItem(NurseRoutes.TASKS, "Tasks", Icons.Filled.ListAlt, Icons.Outlined.ListAlt),
    BottomNavItem(ChatRoutes.CONVERSATIONS, "Chat", Icons.Filled.Chat, Icons.Outlined.Chat),
)

val PatientBottomNav = listOf(
    BottomNavItem(PatientRoutes.DASHBOARD, "Home", Icons.Filled.Home, Icons.Outlined.Home),
    BottomNavItem(PatientRoutes.RECORDS, "Records", Icons.Filled.Description, Icons.Outlined.Description),
    BottomNavItem(PatientRoutes.APPOINTMENTS, "Appointments", Icons.Filled.CalendarMonth, Icons.Outlined.CalendarMonth),
    BottomNavItem(ChatRoutes.CONVERSATIONS, "Chat", Icons.Filled.Chat, Icons.Outlined.Chat),
    BottomNavItem("patient/profile", "Profile", Icons.Filled.Person, Icons.Outlined.Person),
)

val SpecialistBottomNav = listOf(
    BottomNavItem(SpecialistRoutes.DASHBOARD, "Home", Icons.Filled.Home, Icons.Outlined.Home),
    BottomNavItem(SpecialistRoutes.REFERRALS, "Referrals", Icons.Filled.ListAlt, Icons.Outlined.ListAlt),
    BottomNavItem(SpecialistRoutes.PATIENT_LIST, "Patients", Icons.Filled.People, Icons.Outlined.People),
    BottomNavItem(ChatRoutes.CONVERSATIONS, "Chat", Icons.Filled.Chat, Icons.Outlined.Chat),
)

val TherapistBottomNav = listOf(
    BottomNavItem(TherapistRoutes.DASHBOARD, "Home", Icons.Filled.Home, Icons.Outlined.Home),
    BottomNavItem(TherapistRoutes.SESSIONS, "Sessions", Icons.Filled.CalendarMonth, Icons.Outlined.CalendarMonth),
    BottomNavItem(TherapistRoutes.PATIENT_LIST, "Patients", Icons.Filled.People, Icons.Outlined.People),
    BottomNavItem(ChatRoutes.CONVERSATIONS, "Chat", Icons.Filled.Chat, Icons.Outlined.Chat),
)

val FinanceBottomNav = listOf(
    BottomNavItem(FinanceRoutes.DASHBOARD, "Home", Icons.Filled.Home, Icons.Outlined.Home),
    BottomNavItem(FinanceRoutes.BILLS, "Bills", Icons.Filled.Description, Icons.Outlined.Description),
    BottomNavItem(FinanceRoutes.PAYMENTS, "Payments", Icons.Filled.Payment, Icons.Outlined.Payment),
    BottomNavItem(ChatRoutes.CONVERSATIONS, "Chat", Icons.Filled.Chat, Icons.Outlined.Chat),
)

val AdminBottomNav = listOf(
    BottomNavItem(AdminRoutes.DASHBOARD, "Home", Icons.Filled.Home, Icons.Outlined.Home),
    BottomNavItem(AdminRoutes.CHECKIN, "Check-In", Icons.Filled.CheckCircle, Icons.Outlined.CheckCircle),
    BottomNavItem(AdminRoutes.STAFF, "Staff", Icons.Filled.People, Icons.Outlined.People),
    BottomNavItem(ChatRoutes.CONVERSATIONS, "Chat", Icons.Filled.Chat, Icons.Outlined.Chat),
)

@Composable
fun PrognoCareNavGraph(
    navController: NavHostController = rememberNavController(),
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    // Determine which bottom bar items to show based on current route
    val isDoctorRoute = DoctorBottomNav.any { item ->
        currentDestination?.hierarchy?.any { it.route == item.route } == true
    }
    val isNurseRoute = NurseBottomNav.any { item ->
        currentDestination?.hierarchy?.any { it.route == item.route } == true
    }
    val isPatientRoute = PatientBottomNav.any { item ->
        currentDestination?.hierarchy?.any { it.route == item.route } == true
    }
    val isSpecialistRoute = SpecialistBottomNav.any { item ->
        currentDestination?.hierarchy?.any { it.route == item.route } == true
    }
    val isTherapistRoute = TherapistBottomNav.any { item ->
        currentDestination?.hierarchy?.any { it.route == item.route } == true
    }
    val isFinanceRoute = FinanceBottomNav.any { item ->
        currentDestination?.hierarchy?.any { it.route == item.route } == true
    }
    val isAdminRoute = AdminBottomNav.any { item ->
        currentDestination?.hierarchy?.any { it.route == item.route } == true
    }
    val showBottomBar = isDoctorRoute || isNurseRoute || isPatientRoute || isSpecialistRoute || isTherapistRoute || isFinanceRoute || isAdminRoute
    val currentBottomNavItems = when {
        isDoctorRoute -> DoctorBottomNav
        isNurseRoute -> NurseBottomNav
        isPatientRoute -> PatientBottomNav
        isSpecialistRoute -> SpecialistBottomNav
        isTherapistRoute -> TherapistBottomNav
        isFinanceRoute -> FinanceBottomNav
        isAdminRoute -> AdminBottomNav
        else -> emptyList()
    }

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                NavigationBar(
                    containerColor = androidx.compose.material3.MaterialTheme.colorScheme.surface,
                    tonalElevation = 0.dp,
                ) {
                    currentBottomNavItems.forEach { item ->
                        val selected = currentDestination?.hierarchy?.any {
                            it.route == item.route
                        } == true

                        NavigationBarItem(
                            icon = {
                                Icon(
                                    imageVector = if (selected) item.selectedIcon else item.unselectedIcon,
                                    contentDescription = item.label,
                                    modifier = Modifier.size(24.dp),
                                )
                            },
                            label = {
                                Text(
                                    text = item.label,
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = if (selected) androidx.compose.ui.text.font.FontWeight.SemiBold else androidx.compose.ui.text.font.FontWeight.Normal,
                                )
                            },
                            selected = selected,
                            colors = androidx.compose.material3.NavigationBarItemDefaults.colors(
                                selectedIconColor = androidx.compose.material3.MaterialTheme.colorScheme.primary,
                                selectedTextColor = androidx.compose.material3.MaterialTheme.colorScheme.primary,
                                indicatorColor = androidx.compose.material3.MaterialTheme.colorScheme.primaryContainer,
                                unselectedIconColor = androidx.compose.material3.MaterialTheme.colorScheme.onSurfaceVariant,
                                unselectedTextColor = androidx.compose.material3.MaterialTheme.colorScheme.onSurfaceVariant,
                            ),
                            onClick = {
                                navController.navigate(item.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                        )
                    }
                }
            }
        },
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Routes.SPLASH,
            modifier = Modifier.padding(innerPadding),
            enterTransition = { enterTransition },
            exitTransition = { exitTransition },
            popEnterTransition = { popEnterTransition },
            popExitTransition = { popExitTransition },
        ) {
            // Splash
            composable(Routes.SPLASH) {
                val splashViewModel: SplashViewModel = hiltViewModel()
                SplashScreen()

                androidx.compose.runtime.LaunchedEffect(Unit) {
                    splashViewModel.destination.collect { dest ->
                        when (dest) {
                            is SplashDestination.Login -> {
                                navController.navigate(Routes.LOGIN) {
                                    popUpTo(Routes.SPLASH) { inclusive = true }
                                }
                            }
                            is SplashDestination.Dashboard -> {
                                navController.navigate(dest.role.route) {
                                    popUpTo(Routes.SPLASH) { inclusive = true }
                                }
                            }
                        }
                    }
                }
            }

            // Auth
            composable(Routes.LOGIN) {
                LoginScreen(
                    onLoginSuccess = { role ->
                        navController.navigate(role.route) {
                            popUpTo(Routes.LOGIN) { inclusive = true }
                        }
                    },
                )
            }

            // Doctor
            composable(DoctorRoutes.DASHBOARD) {
                DoctorDashboardScreen(
                    onNavigateToAppointments = { navController.navigate(DoctorRoutes.APPOINTMENTS) },
                    onNavigateToPatients = { navController.navigate(DoctorRoutes.PATIENT_LIST) },
                    onNavigateToPatientDetail = { id -> navController.navigate(DoctorRoutes.patientDetail(id)) },
                    onNavigateToChat = { navController.navigate(ChatRoutes.CONVERSATIONS) },
                    onNavigateToProfile = { navController.navigate(ProfileRoutes.PROFILE) },
                )
            }

            composable(DoctorRoutes.APPOINTMENTS) {
                DoctorAppointmentScreen(
                    onBack = { navController.popBackStack() },
                    onPatientClick = { id -> navController.navigate(DoctorRoutes.patientDetail(id)) },
                )
            }

            composable(DoctorRoutes.PATIENT_LIST) {
                DoctorPatientListScreen(
                    onBack = { navController.popBackStack() },
                    onPatientClick = { id -> navController.navigate(DoctorRoutes.patientDetail(id)) },
                )
            }

            composable(
                route = DoctorRoutes.PATIENT_DETAIL,
                arguments = listOf(navArgument("patientId") { type = NavType.StringType }),
            ) { backStackEntry ->
                val patientId = backStackEntry.arguments?.getString("patientId") ?: return@composable
                DoctorPatientDetailScreen(
                    patientId = patientId,
                    onBack = { navController.popBackStack() },
                )
            }

            // Chat
            composable(ChatRoutes.CONVERSATIONS) {
                ConversationListScreen(
                    onConversationClick = { id -> navController.navigate(ChatRoutes.conversationDetail(id)) },
                    onBack = { navController.popBackStack() },
                )
            }

            composable(
                route = ChatRoutes.CONVERSATION_DETAIL,
                arguments = listOf(navArgument("conversationId") { type = NavType.StringType }),
            ) { backStackEntry ->
                val conversationId = backStackEntry.arguments?.getString("conversationId") ?: return@composable
                ChatScreen(
                    conversationId = conversationId,
                    onBack = { navController.popBackStack() },
                )
            }

            // Nurse
            composable(NurseRoutes.DASHBOARD) {
                NurseDashboardScreen(
                    onNavigateToVitals = { navController.navigate(NurseRoutes.VITALS) },
                    onNavigateToMedications = { navController.navigate(NurseRoutes.MEDICATIONS) },
                    onNavigateToCheckIn = { navController.navigate(NurseRoutes.CHECKIN) },
                    onNavigateToTasks = { navController.navigate(NurseRoutes.TASKS) },
                    onNavigateToPatients = { navController.navigate(NurseRoutes.PATIENT_LIST) },
                    onNavigateToChat = { navController.navigate(ChatRoutes.CONVERSATIONS) },
                    onNavigateToProfile = { navController.navigate(ProfileRoutes.PROFILE) },
                )
            }

            composable(NurseRoutes.VITALS) {
                VitalsRecordingScreen(
                    onBack = { navController.popBackStack() },
                    onSaved = { navController.popBackStack() },
                )
            }

            composable(NurseRoutes.MEDICATIONS) {
                MedicationAdministrationScreen(
                    onBack = { navController.popBackStack() },
                )
            }

            composable(NurseRoutes.CHECKIN) {
                NurseCheckInScreen(
                    onBack = { navController.popBackStack() },
                )
            }

            composable(NurseRoutes.TASKS) {
                NurseTaskListScreen(
                    onBack = { navController.popBackStack() },
                )
            }

            // Patient
            composable(PatientRoutes.DASHBOARD) {
                PatientDashboardScreen(
                    onNavigateToAppointments = { navController.navigate(PatientRoutes.APPOINTMENTS) },
                    onNavigateToRecords = { navController.navigate(PatientRoutes.RECORDS) },
                    onNavigateToMedications = { navController.navigate(PatientRoutes.MEDICATIONS) },
                    onNavigateToChat = { navController.navigate(ChatRoutes.CONVERSATIONS) },
                    onNavigateToProfile = { navController.navigate(ProfileRoutes.PROFILE) },
                )
            }

            composable(PatientRoutes.APPOINTMENTS) {
                PatientAppointmentScreen(
                    onBack = { navController.popBackStack() },
                )
            }

            composable(PatientRoutes.RECORDS) {
                PatientRecordsScreen(
                    onBack = { navController.popBackStack() },
                )
            }

            composable(PatientRoutes.MEDICATIONS) {
                PatientMedicationsScreen(
                    onBack = { navController.popBackStack() },
                )
            }

            // Specialist
            composable(SpecialistRoutes.DASHBOARD) {
                SpecialistDashboardScreen(
                    onNavigateToReferrals = { navController.navigate(SpecialistRoutes.REFERRALS) },
                    onNavigateToPatients = { navController.navigate(SpecialistRoutes.PATIENT_LIST) },
                    onNavigateToPatientDetail = { mrn -> navController.navigate("specialist/patients/$mrn") },
                    onNavigateToChat = { navController.navigate(ChatRoutes.CONVERSATIONS) },
                    onNavigateToProfile = { navController.navigate(ProfileRoutes.PROFILE) },
                )
            }

            composable(SpecialistRoutes.REFERRALS) {
                SpecialistReferralScreen(
                    onBack = { navController.popBackStack() },
                    onPatientClick = { mrn -> navController.navigate("specialist/patients/$mrn") },
                )
            }

            composable(SpecialistRoutes.PATIENT_LIST) {
                SpecialistPatientListScreen(
                    onBack = { navController.popBackStack() },
                    onPatientClick = { mrn -> navController.navigate("specialist/patients/$mrn") },
                )
            }

            // Therapist
            composable(TherapistRoutes.DASHBOARD) {
                TherapistDashboardScreen(
                    onNavigateToSessions = { navController.navigate(TherapistRoutes.SESSIONS) },
                    onNavigateToPatients = { navController.navigate(TherapistRoutes.PATIENT_LIST) },
                    onNavigateToPatientDetail = { mrn -> navController.navigate("therapist/patients/$mrn") },
                    onNavigateToChat = { navController.navigate(ChatRoutes.CONVERSATIONS) },
                    onNavigateToProfile = { navController.navigate(ProfileRoutes.PROFILE) },
                )
            }

            composable(TherapistRoutes.SESSIONS) {
                TherapistSessionScreen(
                    onBack = { navController.popBackStack() },
                    onPatientClick = { mrn -> navController.navigate("therapist/patients/$mrn") },
                )
            }

            composable(TherapistRoutes.PATIENT_LIST) {
                TherapistPatientListScreen(
                    onBack = { navController.popBackStack() },
                    onPatientClick = { mrn -> navController.navigate("therapist/patients/$mrn") },
                )
            }

            // Finance
            composable(FinanceRoutes.DASHBOARD) {
                FinanceDashboardScreen(
                    onNavigateToBills = { navController.navigate(FinanceRoutes.BILLS) },
                    onNavigateToBillDetail = { billId -> navController.navigate(FinanceRoutes.billDetail(billId)) },
                    onNavigateToPayments = { navController.navigate(FinanceRoutes.PAYMENTS) },
                    onNavigateToPatientSearch = { navController.navigate(FinanceRoutes.PATIENT_SEARCH) },
                    onNavigateToChat = { navController.navigate(ChatRoutes.CONVERSATIONS) },
                    onNavigateToProfile = { navController.navigate(ProfileRoutes.PROFILE) },
                )
            }

            composable(FinanceRoutes.BILLS) {
                // Bills list screen
            }

            composable(
                route = FinanceRoutes.BILL_DETAIL,
                arguments = listOf(navArgument("billId") { type = NavType.StringType }),
            ) { backStackEntry ->
                val billId = backStackEntry.arguments?.getString("billId") ?: return@composable
                FinanceBillDetailScreen(
                    billId = billId,
                    onBack = { navController.popBackStack() },
                    onRecordPayment = { /* record payment */ },
                )
            }

            composable(FinanceRoutes.PAYMENTS) {
                // Payments list screen
            }

            composable(FinanceRoutes.PATIENT_SEARCH) {
                // Patient search screen
            }

            // Admin
            composable(AdminRoutes.DASHBOARD) {
                AdminDashboardScreen(
                    onNavigateToPatientSearch = { navController.navigate(AdminRoutes.PATIENT_SEARCH) },
                    onNavigateToCheckIn = { navController.navigate(AdminRoutes.CHECKIN) },
                    onNavigateToStaff = { navController.navigate(AdminRoutes.STAFF) },
                    onNavigateToChat = { navController.navigate(ChatRoutes.CONVERSATIONS) },
                    onNavigateToProfile = { navController.navigate(ProfileRoutes.PROFILE) },
                )
            }

            composable(AdminRoutes.PATIENT_SEARCH) {
                // Patient search screen
            }

            composable(AdminRoutes.CHECKIN) {
                // Check-in screen
            }

            composable(AdminRoutes.STAFF) {
                // Staff management screen
            }

            // ── Placeholder routes for other roles ────────────
            composable(TechnicianRoutes.DASHBOARD) {
                TechnicianDashboardScreen(
                    onNavigateToOrders = { /* TODO: navigate to orders screen */ },
                    onNavigateToResults = { /* TODO: navigate to results screen */ },
                    onNavigateToChat = {
                        navController.navigate("chat/conversations") {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    onNavigateToProfile = {
                        navController.navigate(ProfileRoutes.PROFILE)
                    },
                )
            }
            composable(SupportRoutes.DASHBOARD) {
                val navBackStackEntry = rememberNavController() // reuses outer navController
                SupportDashboardScreen(
                    onNavigateToCheckIn = { /* TODO: navigate to check-in screen */ },
                    onNavigateToRequests = { /* TODO: navigate to requests screen */ },
                    onNavigateToPatientLookup = { /* TODO: navigate to patient lookup */ },
                    onNavigateToChat = {
                        navController.navigate("chat/conversations") {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    onNavigateToProfile = {
                        navController.navigate(ProfileRoutes.PROFILE)
                    },
                )
            }

            // Profile (shared across all roles)
            composable(ProfileRoutes.PROFILE) {
                UserProfileScreen(
                    onBack = { navController.popBackStack() },
                    onSignOut = {
                        navController.navigate(Routes.LOGIN) {
                            popUpTo(0) { inclusive = true }
                        }
                    },
                )
            }
        }
    }
}
