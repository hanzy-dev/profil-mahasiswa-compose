package com.muhammadfarhan.profilmahasiswa.screens.profile

import androidx.compose.runtime.saveable.listSaver
import com.muhammadfarhan.profilmahasiswa.model.StudentProfile

data class StudentProfileUiState(
    val profile: StudentProfile,
    val isEditing: Boolean = false
)

val StudentProfileSaver = listSaver<StudentProfile, Any>(
    save = { profile ->
        listOf(
            profile.name,
            profile.studentId,
            profile.studyProgram,
            profile.semester,
            profile.email,
            profile.phone
        )
    },
    restore = { values ->
        StudentProfile(
            name = values[0] as String,
            studentId = values[1] as String,
            studyProgram = values[2] as String,
            semester = values[3] as Int,
            email = values[4] as String,
            phone = values[5] as String
        )
    }
)
