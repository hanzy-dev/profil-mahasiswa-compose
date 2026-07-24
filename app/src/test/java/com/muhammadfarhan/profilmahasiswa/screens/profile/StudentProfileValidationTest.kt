package com.muhammadfarhan.profilmahasiswa.screens.profile

import com.muhammadfarhan.profilmahasiswa.model.DefaultStudentProfile
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class StudentProfileValidationTest {

    @Test
    fun `blank name is required`() {
        assertEquals(
            ProfileFieldError.Required,
            validateStudentProfile(DefaultStudentProfile.copy(name = "   ")).name
        )
    }

    @Test
    fun `valid name is accepted`() {
        assertNull(validateStudentProfile(DefaultStudentProfile.copy(name = "Farhan")).name)
    }

    @Test
    fun `name longer than 60 characters is rejected`() {
        assertEquals(
            ProfileFieldError.TooLong,
            validateStudentProfile(DefaultStudentProfile.copy(name = "a".repeat(61))).name
        )
    }

    @Test
    fun `surrounding name spaces are normalized`() {
        assertEquals("Farhan", normalizeProfile(DefaultStudentProfile.copy(name = " Farhan ")).name)
    }

    @Test
    fun `blank study program is required`() {
        assertEquals(
            ProfileFieldError.Required,
            validateStudentProfile(DefaultStudentProfile.copy(studyProgram = "")).studyProgram
        )
    }

    @Test
    fun `valid study program is accepted`() {
        assertNull(
            validateStudentProfile(DefaultStudentProfile.copy(studyProgram = "S1 Informatika"))
                .studyProgram
        )
    }

    @Test
    fun `study program longer than 80 characters is rejected`() {
        assertEquals(
            ProfileFieldError.TooLong,
            validateStudentProfile(DefaultStudentProfile.copy(studyProgram = "a".repeat(81)))
                .studyProgram
        )
    }

    @Test
    fun `blank email is required`() {
        assertEquals(
            ProfileFieldError.Required,
            validateStudentProfile(DefaultStudentProfile.copy(email = " ")).email
        )
    }

    @Test
    fun `malformed email is rejected`() {
        assertEquals(
            ProfileFieldError.InvalidFormat,
            validateStudentProfile(DefaultStudentProfile.copy(email = "farhan.example.com")).email
        )
    }

    @Test
    fun `valid email is accepted`() {
        assertNull(
            validateStudentProfile(DefaultStudentProfile.copy(email = "farhan@example.co.id")).email
        )
    }

    @Test
    fun `surrounding email spaces are normalized`() {
        assertEquals(
            "farhan@example.com",
            normalizeProfile(DefaultStudentProfile.copy(email = " farhan@example.com ")).email
        )
    }

    @Test
    fun `blank phone is required`() {
        assertEquals(
            ProfileFieldError.Required,
            validateStudentProfile(DefaultStudentProfile.copy(phone = "")).phone
        )
    }

    @Test
    fun `phone with unsupported characters is rejected`() {
        assertEquals(
            ProfileFieldError.InvalidFormat,
            validateStudentProfile(DefaultStudentProfile.copy(phone = "+62 812#345")).phone
        )
    }

    @Test
    fun `phone with too few digits is rejected`() {
        assertEquals(
            ProfileFieldError.InvalidFormat,
            validateStudentProfile(DefaultStudentProfile.copy(phone = "1234567")).phone
        )
    }

    @Test
    fun `formatted numeric phone is accepted`() {
        assertNull(
            validateStudentProfile(DefaultStudentProfile.copy(phone = "+62 812-3456-7890")).phone
        )
    }

    @Test
    fun `masked demo phone is accepted`() {
        assertNull(
            validateStudentProfile(DefaultStudentProfile.copy(phone = "+62 8xx-xxxx-xxxx")).phone
        )
    }

    @Test
    fun `complete valid profile has no errors`() {
        assertFalse(validateStudentProfile(DefaultStudentProfile).hasErrors)
    }

    @Test
    fun `required value check rejects incomplete profile and accepts complete profile`() {
        assertFalse(hasRequiredProfileValues(DefaultStudentProfile.copy(email = " ")))
        assertTrue(hasRequiredProfileValues(DefaultStudentProfile))
    }
}
