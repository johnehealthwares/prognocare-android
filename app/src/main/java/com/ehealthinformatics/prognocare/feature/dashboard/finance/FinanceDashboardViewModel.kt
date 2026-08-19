package com.ehealthinformatics.prognocare.feature.dashboard.finance

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class FinanceDashboardViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow(FinanceDashboardState())
    val state: StateFlow<FinanceDashboardState> = _state.asStateFlow()

    init {
        loadDashboardData()
    }

    private fun loadDashboardData() {
        _state.value = FinanceDashboardState(
            greeting = "Good morning",
            financeName = "Adaeze Nwankwo",
            todayDate = "Tuesday, Aug 18",
            totalRevenue = "₦2,450,000",
            pendingBills = 18,
            completedPayments = 24,
            overdueBills = 5,
            recentBills = listOf(
                Bill("1", "BILL-2026-001", "Chidi Okonkwo", "MRN-00142",
                    "Aug 18, 2026", "Sep 17, 2026", 125000.0, 50000.0, BillStatus.PARTIAL,
                    listOf(
                        BillLineItem("1", "Consultation - General", ItemCategory.CONSULTATION, 1, 15000.0, 15000.0),
                        BillLineItem("2", "Blood Test - CBC", ItemCategory.LAB_TEST, 1, 8000.0, 8000.0),
                        BillLineItem("3", "Blood Test - Lipid Panel", ItemCategory.LAB_TEST, 1, 12000.0, 12000.0),
                        BillLineItem("4", "ECG", ItemCategory.PROCEDURE, 1, 25000.0, 25000.0),
                        BillLineItem("5", "Lisinopril 10mg (30 days)", ItemCategory.MEDICATION, 1, 5000.0, 5000.0),
                        BillLineItem("6", "Room Charge - VIP (1 day)", ItemCategory.ROOM_CHARGE, 1, 60000.0, 60000.0),
                    ), "Follow-up consultation"),
                Bill("2", "BILL-2026-002", "Funke Adeleke", "MRN-00189",
                    "Aug 17, 2026", "Sep 16, 2026", 85000.0, 85000.0, BillStatus.PAID,
                    listOf(
                        BillLineItem("7", "Consultation - Endocrinology", ItemCategory.CONSULTATION, 1, 20000.0, 20000.0),
                        BillLineItem("8", "Thyroid Function Test", ItemCategory.LAB_TEST, 1, 15000.0, 15000.0),
                        BillLineItem("9", "Ultrasound - Thyroid", ItemCategory.IMAGING, 1, 30000.0, 30000.0),
                        BillLineItem("10", "Metformin 500mg (30 days)", ItemCategory.MEDICATION, 1, 8000.0, 8000.0),
                        BillLineItem("11", "Room Charge - Standard (1 day)", ItemCategory.ROOM_CHARGE, 1, 12000.0, 12000.0),
                    )),
                Bill("3", "BILL-2026-003", "Emeka Nwosu", "MRN-00201",
                    "Aug 18, 2026", "Aug 25, 2026", 250000.0, 0.0, BillStatus.OVERDUE,
                    listOf(
                        BillLineItem("12", "Consultation - Cardiology", ItemCategory.CONSULTATION, 1, 25000.0, 25000.0),
                        BillLineItem("13", "Cardiac Catheterization", ItemCategory.PROCEDURE, 1, 150000.0, 150000.0),
                        BillLineItem("14", "Chest X-Ray", ItemCategory.IMAGING, 1, 15000.0, 15000.0),
                        BillLineItem("15", "Blood Work - Cardiac Panel", ItemCategory.LAB_TEST, 1, 20000.0, 20000.0),
                        BillLineItem("16", "Room Charge - ICU (2 days)", ItemCategory.ROOM_CHARGE, 2, 20000.0, 40000.0),
                    ), "Cardiac procedure - URGENT"),
                Bill("4", "BILL-2026-004", "Amina Bello", "MRN-00156",
                    "Aug 16, 2026", "Sep 15, 2026", 45000.0, 45000.0, BillStatus.PAID,
                    listOf(
                        BillLineItem("17", "Consultation - Gynecology", ItemCategory.CONSULTATION, 1, 18000.0, 18000.0),
                        BillLineItem("18", "Pelvic Ultrasound", ItemCategory.IMAGING, 1, 20000.0, 20000.0),
                        BillLineItem("19", "Lab Test - Hormone Panel", ItemCategory.LAB_TEST, 1, 7000.0, 7000.0),
                    )),
                Bill("5", "BILL-2026-005", "Yusuf Abdullahi", "MRN-00234",
                    "Aug 15, 2026", "Sep 14, 2026", 95000.0, 30000.0, BillStatus.PARTIAL,
                    listOf(
                        BillLineItem("20", "Consultation - Neurology", ItemCategory.CONSULTATION, 1, 22000.0, 22000.0),
                        BillLineItem("21", "MRI Brain", ItemCategory.IMAGING, 1, 45000.0, 45000.0),
                        BillLineItem("22", "EEG", ItemCategory.PROCEDURE, 1, 18000.0, 18000.0),
                        BillLineItem("23", "Medication - Neurotin", ItemCategory.MEDICATION, 1, 10000.0, 10000.0),
                    )),
            ),
            recentPayments = listOf(
                Payment("1", "PAY-2026-001", "BILL-2026-001", "BILL-2026-001",
                    "Chidi Okonkwo", 50000.0, PaymentMethod.BANK_TRANSFER,
                    "Aug 18, 2026", "TRF-20260818-001", PaymentStatus.COMPLETED),
                Payment("2", "PAY-2026-002", "BILL-2026-002", "BILL-2026-002",
                    "Funke Adeleke", 85000.0, PaymentMethod.CARD,
                    "Aug 17, 2026", "CRD-20260817-001", PaymentStatus.COMPLETED),
                Payment("3", "PAY-2026-003", "BILL-2026-005", "BILL-2026-005",
                    "Yusuf Abdullahi", 30000.0, PaymentMethod.CASH,
                    "Aug 16, 2026", "CSH-20260816-001", PaymentStatus.COMPLETED),
            ),
            isLoading = false,
        )
    }

    fun recordPayment(billId: String, amount: Double, method: PaymentMethod) {
        val current = _state.value
        val updatedBills = current.recentBills.map { bill ->
            if (bill.id == billId) {
                val newPaid = bill.paidAmount + amount
                bill.copy(
                    paidAmount = newPaid,
                    status = if (newPaid >= bill.totalAmount) BillStatus.PAID else BillStatus.PARTIAL,
                )
            } else bill
        }
        _state.value = current.copy(
            recentBills = updatedBills,
            completedPayments = current.completedPayments + 1,
            pendingBills = (current.pendingBills - 1).coerceAtLeast(0),
        )
    }
}
