package com.muhammadfarhan.profilmahasiswa.screens.add

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class AddStudentValidationTest {
    private val valid = AddStudentForm(
        name = "Mahasiswa Test",
        studentId = "10000001",
        studyProgram = "Program Test",
        semester = "3",
        email = "student@example.com",
        phone = "+62 812-0000-0000"
    )

    @Test fun `blank NIM rejected`() =
        assertEquals(AddStudentFieldError.Required, errors(studentId = " ").studentId)

    @Test fun `non digit NIM rejected`() =
        assertEquals(AddStudentFieldError.DigitsOnly, errors(studentId = "12345A").studentId)

    @Test fun `too short NIM rejected`() =
        assertEquals(AddStudentFieldError.InvalidLength, errors(studentId = "12345").studentId)

    @Test fun `too long NIM rejected`() =
        assertEquals(AddStudentFieldError.InvalidLength, errors(studentId = "1".repeat(21)).studentId)

    @Test fun `valid NIM accepted`() = assertNull(errors().studentId)

    @Test fun `duplicate NIM rejected`() {
        assertEquals(
            AddStudentFieldError.Duplicate,
            validateAddStudentForm(valid, listOf(" ${valid.studentId} ")).studentId
        )
    }

    @Test fun `blank semester rejected`() =
        assertEquals(AddStudentFieldError.Required, errors(semester = " ").semester)

    @Test fun `non numeric semester rejected`() =
        assertEquals(AddStudentFieldError.DigitsOnly, errors(semester = "dua").semester)

    @Test fun `semester below one rejected`() =
        assertEquals(AddStudentFieldError.InvalidRange, errors(semester = "0").semester)

    @Test fun `semester above fourteen rejected`() =
        assertEquals(AddStudentFieldError.InvalidRange, errors(semester = "15").semester)

    @Test fun `valid semester accepted`() = assertNull(errors().semester)

    @Test fun `fully valid form accepted`() = assertTrue(isAddStudentFormValid(valid))

    @Test fun `invalid form reports every required field`() {
        val result = validateAddStudentForm(AddStudentForm())
        assertTrue(result.hasErrors)
        assertEquals(AddStudentFieldError.Required, result.name)
        assertEquals(AddStudentFieldError.Required, result.studentId)
        assertEquals(AddStudentFieldError.Required, result.studyProgram)
        assertEquals(AddStudentFieldError.Required, result.semester)
        assertEquals(AddStudentFieldError.Required, result.email)
        assertEquals(AddStudentFieldError.Required, result.phone)
    }

    @Test fun `normalization trims every field`() {
        val normalized = normalizeAddStudentForm(
            AddStudentForm(" Name ", " 123456 ", " Program ", " 2 ", " a@b.co ", " +62 81234567 ")
        )
        assertFalse(
            listOf(
                normalized.name, normalized.studentId, normalized.studyProgram,
                normalized.semester, normalized.email, normalized.phone
            ).any { it != it.trim() }
        )
    }

    private fun errors(
        studentId: String = valid.studentId,
        semester: String = valid.semester
    ) = validateAddStudentForm(valid.copy(studentId = studentId, semester = semester))
}
