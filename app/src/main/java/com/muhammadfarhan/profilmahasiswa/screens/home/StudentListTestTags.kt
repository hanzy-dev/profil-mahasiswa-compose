package com.muhammadfarhan.profilmahasiswa.screens.home

object StudentListTestTags {
    const val Screen = "student_list_screen"
    const val PrimaryStudentCard = "primary_student_card"
    const val AddStudentFab = "add_student_fab"
    const val EmptyStateAction = "empty_state_add_student"

    fun studentCard(studentId: String): String = "student_card_${studentId.hashCode()}"
}
