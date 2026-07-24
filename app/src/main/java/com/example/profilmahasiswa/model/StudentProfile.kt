package com.example.profilmahasiswa.model

data class StudentProfile(
    val name: String,
    val studentId: String,
    val studyProgram: String,
    val semester: Int,
    val email: String,
    val phone: String
)

val DefaultStudentProfile = StudentProfile(
    name = "Muhammad Farhan",
    studentId = "23083000060",
    studyProgram = "S1 Sistem Informasi",
    semester = 6,
    email = "muhammad.farhan@example.com",
    phone = "+62 8xx-xxxx-xxxx"
)
