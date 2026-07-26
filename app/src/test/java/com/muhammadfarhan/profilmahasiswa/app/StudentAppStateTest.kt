package com.muhammadfarhan.profilmahasiswa.app

import com.muhammadfarhan.profilmahasiswa.model.CourseGrade
import com.muhammadfarhan.profilmahasiswa.model.DefaultStudentProfile
import com.muhammadfarhan.profilmahasiswa.model.StudentProfile
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertSame
import org.junit.Assert.assertTrue
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

    @Test
    fun addStudentAppendsUniqueProfile() {
        assertEquals(
            listOf(DefaultStudentProfile, secondStudent),
            DefaultStudentAppState.addStudent(secondStudent).students
        )
    }

    @Test
    fun addStudentPreservesExistingOrder() {
        val third = secondStudent.copy(name = "Third", studentId = "TEST-3")
        val state = StudentAppState(listOf(DefaultStudentProfile, secondStudent))
        assertEquals(
            listOf(DefaultStudentProfile.studentId, secondStudent.studentId, third.studentId),
            state.addStudent(third).students.map(StudentProfile::studentId)
        )
    }

    @Test
    fun duplicateAddDoesNotAlterState() {
        val duplicate = DefaultStudentProfile.copy(name = "Duplicate", studentId = " 23083000060 ")
        assertSame(DefaultStudentAppState, DefaultStudentAppState.addStudent(duplicate))
    }

    @Test
    fun addedStudentSurvivesSaverRoundTrip() {
        val added = DefaultStudentAppState.addStudent(secondStudent)
        assertEquals(added, restoreStudentAppState(saveStudentAppState(added)))
    }

    @Test
    fun updateThemeModePreservesAllStudents() {
        val state = StudentAppState(listOf(DefaultStudentProfile, secondStudent))
        val updated = state.updateThemeMode(ThemeMode.DARK)
        assertEquals(state.students, updated.students)
    }

    @Test
    fun updateThemeModeChangesOnlyThemeMode() {
        val state = DefaultStudentAppState
        val updated = state.updateThemeMode(ThemeMode.DARK)
        assertEquals(ThemeMode.DARK, updated.themeMode)
        assertEquals(state.students, updated.students)
    }

    @Test
    fun studentAppStateSaverRestoresLight() {
        val state = DefaultStudentAppState.copy(themeMode = ThemeMode.LIGHT)
        assertEquals(ThemeMode.LIGHT, restoreStudentAppState(saveStudentAppState(state)).themeMode)
    }

    @Test
    fun studentAppStateSaverRestoresDark() {
        val state = DefaultStudentAppState.copy(themeMode = ThemeMode.DARK)
        assertEquals(ThemeMode.DARK, restoreStudentAppState(saveStudentAppState(state)).themeMode)
    }

    @Test
    fun malformedSavedThemeModeFallsBackSafely() {
        val malformedValues = listOf("INVALID_THEME", "0")
        val restored = restoreStudentAppState(malformedValues)
        assertEquals(ThemeMode.SYSTEM, restored.themeMode)
        assertEquals(DefaultStudentAppState.students, restored.students)
    }

    @Test
    fun demoGradesExistOnlyForMuhammadFarhan() {
        val farhanGrades = DefaultStudentAppState.getGradesForStudent(DefaultStudentProfile.studentId)
        assertTrue(farhanGrades.isNotEmpty())
        assertEquals(6, farhanGrades.size)
        
        val otherGrades = DefaultStudentAppState.getGradesForStudent("other-id")
        assertTrue(otherGrades.isEmpty())
    }

    @Test
    fun runtimeStudentHasEmptyGrades() {
        val newState = DefaultStudentAppState.addStudent(secondStudent)
        val grades = newState.getGradesForStudent(secondStudent.studentId)
        assertTrue(grades.isEmpty())
    }

    @Test
    fun gradeCalculationsAreCorrect() {
        val grades = listOf(
            CourseGrade("C1", "Course 1", 90, "A"),
            CourseGrade("C2", "Course 2", 80, "B")
        )
        assertEquals(85.0, grades.averageScore(), 0.001)
        assertEquals(90, grades.highestScore())
    }

    @Test
    fun emptyGradesCalculationSafety() {
        val grades = emptyList<CourseGrade>()
        assertEquals(0.0, grades.averageScore(), 0.001)
        assertEquals(0, grades.highestScore())
    }

    @Test
    fun saverRestoresProfileImageUri() {
        val profileWithPhoto = DefaultStudentProfile.copy(profileImageUri = "content://photo")
        val state = StudentAppState(listOf(profileWithPhoto))
        val restored = restoreStudentAppState(saveStudentAppState(state))
        assertEquals("content://photo", restored.students.first().profileImageUri)
    }

    @Test
    fun saverRestoresGrades() {
        val state = DefaultStudentAppState
        val restored = restoreStudentAppState(saveStudentAppState(state))
        assertEquals(state.gradesByStudentId, restored.gradesByStudentId)
    }

    @Test
    fun fullStateRoundTrip() {
        val state = StudentAppState(
            students = listOf(
                DefaultStudentProfile.copy(profileImageUri = "uri1"),
                secondStudent.copy(profileImageUri = null)
            ),
            themeMode = ThemeMode.DARK,
            gradesByStudentId = mapOf(
                DefaultStudentProfile.studentId to listOf(CourseGrade("C1", "N1", 100, "A")),
                secondStudent.studentId to emptyList()
            )
        )
        val restored = restoreStudentAppState(saveStudentAppState(state))
        assertEquals(state, restored)
    }
}
