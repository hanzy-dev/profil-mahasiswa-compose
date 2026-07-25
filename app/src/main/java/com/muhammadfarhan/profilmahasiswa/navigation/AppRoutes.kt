package com.muhammadfarhan.profilmahasiswa.navigation

import android.net.Uri

object AppRoutes {
    const val STUDENTS = "students"
    const val ADD_STUDENT = "students/add"
    const val STUDENT_ID = "studentId"
    const val STUDENT_DETAIL = "student/{$STUDENT_ID}"

    fun studentDetail(studentId: String): String = "student/${Uri.encode(studentId)}"
}
