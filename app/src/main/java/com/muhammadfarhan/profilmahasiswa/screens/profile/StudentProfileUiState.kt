package com.muhammadfarhan.profilmahasiswa.screens.profile

import androidx.compose.runtime.saveable.listSaver
import com.muhammadfarhan.profilmahasiswa.model.StudentProfile

data class StudentProfileUiState(
    val savedProfile: StudentProfile,
    val draftProfile: StudentProfile = savedProfile,
    val isEditing: Boolean = false,
    val fieldErrors: ProfileFieldErrors = ProfileFieldErrors()
) {
    val hasChanges: Boolean
        get() = draftProfile != savedProfile

    val displayedProfile: StudentProfile
        get() = if (isEditing) draftProfile else savedProfile

    val canSave: Boolean
        get() = hasChanges &&
            hasRequiredProfileValues(draftProfile) &&
            !fieldErrors.hasErrors
}

val StudentProfileSaver = listSaver<StudentProfile, Any>(
    save = { profile ->
        listOf(
            profile.name,
            profile.studentId,
            profile.studyProgram,
            profile.semester,
            profile.email,
            profile.phone,
            profile.profileImageUri ?: ""
        )
    },
    restore = { values ->
        StudentProfile(
            name = values[0] as String,
            studentId = values[1] as String,
            studyProgram = values[2] as String,
            semester = values[3] as Int,
            email = values[4] as String,
            phone = values[5] as String,
            profileImageUri = (values[6] as String).ifEmpty { null }
        )
    }
)

val ProfileFieldErrorsSaver = listSaver<ProfileFieldErrors, String>(
    save = { errors ->
        listOf(
            errors.name?.name.orEmpty(),
            errors.studyProgram?.name.orEmpty(),
            errors.email?.name.orEmpty(),
            errors.phone?.name.orEmpty()
        )
    },
    restore = { values ->
        fun safeError(value: String) = value.takeIf(String::isNotEmpty)?.let {
            runCatching { ProfileFieldError.valueOf(it) }.getOrNull()
        }
        ProfileFieldErrors(
            name = safeError(values[0]),
            studyProgram = safeError(values[1]),
            email = safeError(values[2]),
            phone = safeError(values[3])
        )
    }
)
