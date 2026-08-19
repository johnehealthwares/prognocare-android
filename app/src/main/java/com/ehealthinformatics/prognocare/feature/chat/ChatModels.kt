package com.ehealthinformatics.prognocare.feature.chat

import com.ehealthinformatics.prognocare.navigation.UserRole

data class Conversation(
    val id: String,
    val title: String,
    val lastMessage: String,
    val lastMessageTime: String,
    val unreadCount: Int = 0,
    val isOnline: Boolean = false,
    val avatarText: String,
)

data class ChatMessage(
    val id: String,
    val text: String,
    val isMe: Boolean,
    val timestamp: String,
    val senderName: String,
)

fun getConversationsForRole(role: UserRole): List<Conversation> {
    return when (role) {
        UserRole.Doctor -> listOf(
            Conversation("1", "Dr. Fatima Yusuf", "Lab results are ready for review", "2m ago", 3, true, "FY"),
            Conversation("2", "Nurse Amina", "Patient Chidi's vitals are recorded", "15m ago", 0, true, "AN"),
            Conversation("3", "Lab - Emeka", "Blood work completed", "1h ago", 1, false, "LE"),
            Conversation("4", "Pharmacy", "Prescription dispensed for Tunde", "2h ago", 0, false, "PH"),
            Conversation("5", "Dr. Ibrahim", "Referral for patient Grace", "3h ago", 0, true, "IM"),
            Conversation("6", "Admin Office", "Schedule update for next week", "Yesterday", 0, false, "AO"),
        )
        UserRole.Patient -> listOf(
            Conversation("1", "Dr. Chidi Okonkwo", "Your appointment is confirmed for tomorrow", "10m ago", 1, true, "CO"),
            Conversation("2", "Pharmacy", "Your prescription is ready for pickup", "1h ago", 0, true, "PH"),
            Conversation("3", "Lab Results", "Your blood test results are available", "3h ago", 2, false, "LR"),
            Conversation("4", "Nurse Amara", "Vitals recording reminder", "Yesterday", 0, false, "NA"),
        )
        UserRole.Nurse -> listOf(
            Conversation("1", "Dr. Adebayo", "Patient vitals review needed", "5m ago", 2, true, "DA"),
            Conversation("2", "Dr. Fatima Yusuf", "Medication administration confirmed", "30m ago", 0, true, "FY"),
            Conversation("3", "Admin Office", "Shift schedule update", "2h ago", 0, false, "AO"),
            Conversation("4", "Lab - Emeka", "Specimen collection status", "4h ago", 1, false, "LE"),
        )
        UserRole.Specialist -> listOf(
            Conversation("1", "Dr. Adebayo", "New referral for cardiology consultation", "15m ago", 1, true, "DA"),
            Conversation("2", "Dr. Ibrahim", "Patient follow-up results", "1h ago", 0, true, "IM"),
            Conversation("3", "Nurse Amina", "Patient preparation for procedure", "3h ago", 0, false, "AN"),
        )
        UserRole.Therapist -> listOf(
            Conversation("1", "Dr. Adebayo", "Patient therapy progress update", "20m ago", 0, true, "DA"),
            Conversation("2", "Nurse Amara", "Session scheduling request", "2h ago", 1, false, "NA"),
            Conversation("3", "Admin Office", "Equipment maintenance schedule", "Yesterday", 0, false, "AO"),
        )
        UserRole.Technician -> listOf(
            Conversation("1", "Lab - Emeka", "Sample processing status", "10m ago", 0, true, "LE"),
            Conversation("2", "Dr. Fatima Yusuf", "Urgent test request", "1h ago", 2, true, "FY"),
            Conversation("3", "Admin Office", "Equipment calibration reminder", "3h ago", 0, false, "AO"),
        )
        UserRole.Finance -> listOf(
            Conversation("1", "Admin Office", "Billing discrepancy review", "30m ago", 1, true, "AO"),
            Conversation("2", "Dr. Adebayo", "Insurance pre-authorization needed", "2h ago", 0, true, "DA"),
            Conversation("3", "Pharmacy", "Medication billing inquiry", "4h ago", 0, false, "PH"),
        )
        UserRole.Support -> listOf(
            Conversation("1", "Admin Office", "Patient complaint resolution", "15m ago", 0, true, "AO"),
            Conversation("2", "Nurse Amina", "Patient discharge assistance", "1h ago", 1, false, "AN"),
            Conversation("3", "Dr. Ibrahim", "Appointment scheduling conflict", "3h ago", 0, false, "IM"),
        )
        UserRole.Admin -> listOf(
            Conversation("1", "Dr. Adebayo", "Staff schedule approval needed", "20m ago", 1, true, "DA"),
            Conversation("2", "Finance - Aisha", "Budget report review", "1h ago", 0, true, "FA"),
            Conversation("3", "IT Support", "System maintenance notification", "4h ago", 0, false, "IT"),
        )
    }
}

fun getMessagesForConversation(conversationId: String, role: UserRole): List<ChatMessage> {
    // Return role-appropriate messages
    return when (role) {
        UserRole.Patient -> listOf(
            ChatMessage("1", "Hello! I wanted to confirm my appointment for tomorrow at 10:30 AM.", true, "09:00 AM", "Chidi Okonkwo"),
            ChatMessage("2", "Hello Chidi! Yes, your appointment with Dr. Adebayo is confirmed for tomorrow at 10:30 AM. Please arrive 15 minutes early.", false, "09:05 AM", "Dr. Chidi Okonkwo"),
            ChatMessage("3", "Thank you! Should I bring any documents or reports?", true, "09:10 AM", "Chidi Okonkwo"),
            ChatMessage("4", "Please bring your insurance card and any recent lab results if you have them. See you tomorrow!", false, "09:12 AM", "Dr. Chidi Okonkwo"),
        )
        else -> listOf(
            ChatMessage("1", "Good morning! I wanted to discuss the lab results for patient Chidi Okonkwo", false, "08:30 AM", "Dr. Fatima Yusuf"),
            ChatMessage("2", "Good morning. I reviewed them yesterday. His glucose levels are still elevated.", true, "08:32 AM", "Dr. Adebayo"),
            ChatMessage("3", "Yes, I noticed that too. Should we increase the Metformin dosage?", false, "08:33 AM", "Dr. Fatima Yusuf"),
            ChatMessage("4", "Let's increase to 1000mg twice daily and schedule a follow-up in 2 weeks.", true, "08:35 AM", "Dr. Adebayo"),
            ChatMessage("5", "Understood. I'll update the medication record and notify the patient.", false, "08:36 AM", "Dr. Fatima Yusuf"),
            ChatMessage("6", "Also, his A1C is at 8.2%. We should consider adding Glipizide if the new dosage doesn't help.", true, "08:38 AM", "Dr. Adebayo"),
            ChatMessage("7", "I'll prepare the new prescription and send it to pharmacy. Thank you, doctor.", false, "08:39 AM", "Dr. Fatima Yusuf"),
        )
    }
}
