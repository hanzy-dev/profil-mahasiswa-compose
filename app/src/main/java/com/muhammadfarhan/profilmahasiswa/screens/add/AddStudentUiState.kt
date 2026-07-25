package com.muhammadfarhan.profilmahasiswa.screens.add

import androidx.compose.runtime.saveable.listSaver

data class AddStudentForm(
    val name: String = "",
    val studentId: String = "",
    val studyProgram: String = "",
    val semester: String = "",
    val email: String = "",
    val phone: String = ""
)

enum class AddStudentFieldError {
    Required,
    TooLong,
    DigitsOnly,
    InvalidLength,
    Duplicate,
    InvalidRange,
    InvalidFormat
}

data class AddStudentFieldErrors(
    val name: AddStudentFieldError? = null,
    val studentId: AddStudentFieldError? = null,
    val studyProgram: AddStudentFieldError? = null,
    val semester: AddStudentFieldError? = null,
    val email: AddStudentFieldError? = null,
    val phone: AddStudentFieldError? = null
) {
    val hasErrors: Boolean
        get() = listOf(name, studentId, studyProgram, semester, email, phone).any { it != null }
}

data class AddStudentUiState(
    val form: AddStudentForm = AddStudentForm(),
    val errors: AddStudentFieldErrors = AddStudentFieldErrors(),
    val canSave: Boolean = false
)

val AddStudentFormSaver = listSaver<AddStudentForm, String>(
    save = { listOf(it.name, it.studentId, it.studyProgram, it.semester, it.email, it.phone) },
    restore = {
        if (it.size != 6) AddStudentForm()
        else AddStudentForm(it[0], it[1], it[2], it[3], it[4], it[5])
    }
)

val AddStudentErrorsSaver = listSaver<AddStudentFieldErrors, String>(
    save = {
        listOf(it.name, it.studentId, it.studyProgram, it.semester, it.email, it.phone)
            .map { error -> error?.name.orEmpty() }
    },
    restore = { values ->
        fun error(index: Int) = values.getOrNull(index)
            ?.takeIf(String::isNotEmpty)
            ?.let { runCatching { AddStudentFieldError.valueOf(it) }.getOrNull() }
        AddStudentFieldErrors(error(0), error(1), error(2), error(3), error(4), error(5))
    }
)
