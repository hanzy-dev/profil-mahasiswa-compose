package com.muhammadfarhan.profilmahasiswa.screens.add

import com.muhammadfarhan.profilmahasiswa.model.StudentProfile
import com.muhammadfarhan.profilmahasiswa.screens.profile.ProfileFieldError
import com.muhammadfarhan.profilmahasiswa.screens.profile.validateEmail
import com.muhammadfarhan.profilmahasiswa.screens.profile.validatePhone

private const val MaxNameLength = 60
private const val MaxStudyProgramLength = 80
private const val MinStudentIdLength = 6
private const val MaxStudentIdLength = 20

fun normalizeAddStudentForm(form: AddStudentForm): AddStudentForm = form.copy(
    name = form.name.trim(),
    studentId = form.studentId.trim(),
    studyProgram = form.studyProgram.trim(),
    semester = form.semester.trim(),
    email = form.email.trim(),
    phone = form.phone.trim()
)

fun validateAddStudentForm(
    form: AddStudentForm,
    existingStudentIds: Collection<String> = emptyList()
): AddStudentFieldErrors {
    val value = normalizeAddStudentForm(form)
    return AddStudentFieldErrors(
        name = when {
            value.name.isBlank() -> AddStudentFieldError.Required
            value.name.length > MaxNameLength -> AddStudentFieldError.TooLong
            else -> null
        },
        studentId = when {
            value.studentId.isBlank() -> AddStudentFieldError.Required
            !value.studentId.all(Char::isDigit) -> AddStudentFieldError.DigitsOnly
            value.studentId.length !in MinStudentIdLength..MaxStudentIdLength ->
                AddStudentFieldError.InvalidLength
            existingStudentIds.any { it.trim() == value.studentId } ->
                AddStudentFieldError.Duplicate
            else -> null
        },
        studyProgram = when {
            value.studyProgram.isBlank() -> AddStudentFieldError.Required
            value.studyProgram.length > MaxStudyProgramLength -> AddStudentFieldError.TooLong
            else -> null
        },
        semester = when {
            value.semester.isBlank() -> AddStudentFieldError.Required
            value.semester.toIntOrNull() == null -> AddStudentFieldError.DigitsOnly
            value.semester.toInt() !in 1..14 -> AddStudentFieldError.InvalidRange
            else -> null
        },
        email = validateEmail(value.email).toAddError(),
        phone = validatePhone(value.phone).toAddError()
    )
}

fun isAddStudentFormValid(
    form: AddStudentForm,
    existingStudentIds: Collection<String> = emptyList()
): Boolean = !validateAddStudentForm(form, existingStudentIds).hasErrors

fun AddStudentForm.toStudentProfile(): StudentProfile {
    val value = normalizeAddStudentForm(this)
    return StudentProfile(
        name = value.name,
        studentId = value.studentId,
        studyProgram = value.studyProgram,
        semester = value.semester.toInt(),
        email = value.email,
        phone = value.phone
    )
}

private fun ProfileFieldError?.toAddError(): AddStudentFieldError? = when (this) {
    null -> null
    ProfileFieldError.Required -> AddStudentFieldError.Required
    ProfileFieldError.TooLong -> AddStudentFieldError.TooLong
    ProfileFieldError.InvalidFormat -> AddStudentFieldError.InvalidFormat
}
