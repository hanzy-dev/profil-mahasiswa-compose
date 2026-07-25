package com.muhammadfarhan.profilmahasiswa.app

import com.muhammadfarhan.profilmahasiswa.model.DefaultStudentProfile
import com.muhammadfarhan.profilmahasiswa.model.StudentProfile
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertSame
import org.junit.Test

class StudentAppStateTest {

    private val secondStudent = StudentProfile(
        name = "Student Test",
        studentId = "TEST-2",
        studyProgram = "Test Program",
        semester = 2,
        email = "student@example.com",
        phone = "+62 800"
    )

    @Test
    fun findStudentReturnsMatchingProfile() {
        assertEquals(
            DefaultStudentProfile,
            DefaultStudentAppState.findStudent(DefaultStudentProfile.studentId)
        )
    }

    @Test
    fun findStudentReturnsNullForUnknownId() {
        assertNull(DefaultStudentAppState.findStudent("unknown"))
    }

    @Test
    fun updateStudentReplacesMatchingProfile() {
        val updated = DefaultStudentProfile.copy(name = "Updated")
        assertEquals(updated, DefaultStudentAppState.updateStudent(updated).students.single())
    }

    @Test
    fun updateStudentPreservesOrder() {
        val state = StudentAppState(listOf(DefaultStudentProfile, secondStudent))
        val updated = DefaultStudentProfile.copy(name = "Updated")
        assertEquals(
            listOf(updated.studentId, secondStudent.studentId),
            state.updateStudent(updated).students.map(StudentProfile::studentId)
        )
    }

    @Test
    fun updateStudentDoesNotAddUnknownProfile() {
        assertSame(DefaultStudentAppState, DefaultStudentAppState.updateStudent(secondStudent))
    }

    @Test
    fun defaultStateContainsOnlyPrimaryStudent() {
        assertEquals(listOf(DefaultStudentProfile), DefaultStudentAppState.students)
    }

    @Test
    fun defaultThemeModeIsSystem() {
        assertEquals(ThemeMode.SYSTEM, DefaultStudentAppState.themeMode)
    }

    @Test
    fun studentListSaveRestoreRoundTrip() {
        val state = StudentAppState(
            students = listOf(DefaultStudentProfile, secondStudent),
            themeMode = ThemeMode.LIGHT
        )
        assertEquals(state, restoreStudentAppState(saveStudentAppState(state)))
    }

    @Test
    fun themeModeSaveRestoreRoundTrip() {
        ThemeMode.entries.forEach { mode ->
            val state = DefaultStudentAppState.copy(themeMode = mode)
            assertEquals(mode, restoreStudentAppState(saveStudentAppState(state)).themeMode)
        }
    }
}
