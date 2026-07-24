package com.muhammadfarhan.profilmahasiswa.screens.profile

import com.muhammadfarhan.profilmahasiswa.model.StudentProfile

enum class ProfileFieldError {
    Required,
    TooLong,
    InvalidFormat
}

data class ProfileFieldErrors(
    val name: ProfileFieldError? = null,
    val studyProgram: ProfileFieldError? = null,
    val email: ProfileFieldError? = null,
    val phone: ProfileFieldError? = null
) {
    val hasErrors: Boolean
        get() = name != null || studyProgram != null || email != null || phone != null
}

private const val MAX_NAME_LENGTH = 60
private const val MAX_STUDY_PROGRAM_LENGTH = 80
private val emailPattern = Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")
private val phoneCharacters = Regex("^[0-9xX+()\\s-]+$")
private val maskedPhonePattern = Regex("^\\+62\\s8xx-xxxx-xxxx$", RegexOption.IGNORE_CASE)

fun normalizeProfile(profile: StudentProfile): StudentProfile = profile.copy(
    name = profile.name.trim(),
    studyProgram = profile.studyProgram.trim(),
    email = profile.email.trim(),
    phone = profile.phone.trim()
)

fun validateStudentProfile(profile: StudentProfile): ProfileFieldErrors {
    val normalized = normalizeProfile(profile)
    return ProfileFieldErrors(
        name = validateRequiredText(normalized.name, MAX_NAME_LENGTH),
        studyProgram = validateRequiredText(
            normalized.studyProgram,
            MAX_STUDY_PROGRAM_LENGTH
        ),
        email = validateEmail(normalized.email),
        phone = validatePhone(normalized.phone)
    )
}

fun hasRequiredProfileValues(profile: StudentProfile): Boolean =
    profile.name.isNotBlank() &&
        profile.studyProgram.isNotBlank() &&
        profile.email.isNotBlank() &&
        profile.phone.isNotBlank()

private fun validateRequiredText(
    value: String,
    maxLength: Int
): ProfileFieldError? = when {
    value.isBlank() -> ProfileFieldError.Required
    value.length > maxLength -> ProfileFieldError.TooLong
    else -> null
}

private fun validateEmail(email: String): ProfileFieldError? = when {
    email.isBlank() -> ProfileFieldError.Required
    !emailPattern.matches(email) -> ProfileFieldError.InvalidFormat
    else -> null
}

private fun validatePhone(phone: String): ProfileFieldError? {
    if (phone.isBlank()) return ProfileFieldError.Required
    if (!phoneCharacters.matches(phone)) return ProfileFieldError.InvalidFormat
    if (maskedPhonePattern.matches(phone)) return null

    val digitCount = phone.count(Char::isDigit)
    return if (digitCount in 8..15) null else ProfileFieldError.InvalidFormat
}
