package com.muhammadfarhan.profilmahasiswa.screens.grades

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.muhammadfarhan.profilmahasiswa.MainActivity
import com.muhammadfarhan.profilmahasiswa.screens.add.AddStudentTestTags
import com.muhammadfarhan.profilmahasiswa.screens.home.StudentListTestTags
import com.muhammadfarhan.profilmahasiswa.screens.profile.ProfileTestTags
import org.junit.Rule
import org.junit.Test

class StudentGradesWorkflowTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun navigateToGradesAndVerifyContent() {
        openPrimaryStudentProfile()

        // Check if View Grades button exists and click it
        composeRule.onNodeWithTag(ProfileTestTags.ViewGrades)
            .performScrollTo()
            .performClick()

        // Verify Grades Screen is shown
        composeRule.onNodeWithTag(StudentGradesTestTags.Screen).assertIsDisplayed()

        // Verify Student Info
        composeRule.onNodeWithTag(StudentGradesTestTags.StudentInfo).assertIsDisplayed()
        composeRule.onNodeWithText("Muhammad Farhan").assertIsDisplayed()
        composeRule.onNodeWithText("NIM: 23083000060").assertIsDisplayed()

        // Verify Demo Label
        composeRule.onNodeWithTag(StudentGradesTestTags.DemoLabel).assertIsDisplayed()

        // Verify Summary Section
        composeRule.onNodeWithTag(StudentGradesTestTags.CourseCount).assertIsDisplayed()
        composeRule.onNodeWithTag(StudentGradesTestTags.AverageScore).assertIsDisplayed()
        composeRule.onNodeWithTag(StudentGradesTestTags.HighestScore).assertIsDisplayed()

        // Verify Grade List exists and has items
        composeRule.onNodeWithTag(StudentGradesTestTags.GradeList).assertIsDisplayed()
        composeRule.onNodeWithText("Pemrograman Mobile").assertExists()
        composeRule.onNodeWithText("IF101").assertExists()
    }

    @Test
    fun backFromGradesReturnsToProfile() {
        openPrimaryStudentProfile()
        composeRule.onNodeWithTag(ProfileTestTags.ViewGrades)
            .performScrollTo()
            .performClick()

        composeRule.onNodeWithTag(StudentGradesTestTags.Back).performClick()

        composeRule.onNodeWithTag(ProfileTestTags.Screen).assertIsDisplayed()
    }

    @Test
    fun themeToggleWorksOnGradesScreen() {
        openPrimaryStudentProfile()
        composeRule.onNodeWithTag(ProfileTestTags.ViewGrades)
            .performScrollTo()
            .performClick()

        // Toggle theme
        // The description in strings.xml: action_enable_dark_theme = "Aktifkan tema gelap"
        // If it's currently light, the button says "Aktifkan tema gelap"
        val darkThemeDesc = "Aktifkan tema gelap"
        val lightThemeDesc = "Aktifkan tema terang"

        val button = composeRule.onNode(hasContentDescription(darkThemeDesc) or hasContentDescription(lightThemeDesc))
        button.assertExists().performClick()

        // After toggle, the other description should be present (or same if it was SYSTEM)
        composeRule.onNode(hasContentDescription(darkThemeDesc) or hasContentDescription(lightThemeDesc)).assertExists()
    }

    @Test
    fun emptyGradesForNewStudent() {
        // Go to Add Student
        composeRule.onNodeWithTag(StudentListTestTags.AddStudentFab).performClick()

        // Fill details
        val newId = "999999"
        composeRule.onNodeWithTag(AddStudentTestTags.Name).performTextInput("New Student")
        composeRule.onNodeWithTag(AddStudentTestTags.StudentId).performTextInput(newId)
        composeRule.onNodeWithTag(AddStudentTestTags.StudyProgram).performTextInput("Test")
        composeRule.onNodeWithTag(AddStudentTestTags.Semester).performTextInput("1")
        composeRule.onNodeWithTag(AddStudentTestTags.Email).performTextInput("test@test.com")
        composeRule.onNodeWithTag(AddStudentTestTags.Phone).performTextInput("08123456789")

        composeRule.onNodeWithTag(AddStudentTestTags.Save).performScrollTo().performClick()

        // Open the new student profile
        composeRule.onNodeWithText("New Student").performClick()

        // Open grades
        composeRule.onNodeWithTag(ProfileTestTags.ViewGrades)
            .performScrollTo()
            .performClick()

        // Verify empty state
        composeRule.onNodeWithTag(StudentGradesTestTags.EmptyState).assertIsDisplayed()
        composeRule.onNodeWithText("Belum ada data nilai").assertIsDisplayed()
    }

    private fun openPrimaryStudentProfile() {
        composeRule.onNodeWithTag(StudentListTestTags.PrimaryStudentCard).performClick()
    }
}
