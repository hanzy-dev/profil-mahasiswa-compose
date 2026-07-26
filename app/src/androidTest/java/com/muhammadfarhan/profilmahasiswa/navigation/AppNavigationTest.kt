package com.muhammadfarhan.profilmahasiswa.navigation

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.semantics.SemanticsProperties
import com.muhammadfarhan.profilmahasiswa.MainActivity
import com.muhammadfarhan.profilmahasiswa.screens.home.StudentListTestTags
import com.muhammadfarhan.profilmahasiswa.screens.profile.ProfileTestTags
import com.muhammadfarhan.profilmahasiswa.screens.grades.StudentGradesTestTags
import org.junit.Rule
import org.junit.Test

class AppNavigationTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun homeDisplaysPrimaryStudent() {
        composeRule.onNodeWithTag(StudentListTestTags.Screen).assertIsDisplayed()
        composeRule.onNodeWithText("Muhammad Farhan", useUnmergedTree = true).assertExists()
        composeRule.onNodeWithText("NIM: 23083000060", useUnmergedTree = true).assertExists()
    }

    @Test
    fun tappingPrimaryStudentOpensCorrectProfile() {
        openProfile()
        composeRule.onNodeWithTag(ProfileTestTags.Screen).assertIsDisplayed()
        composeRule.onNodeWithText("NIM: 23083000060").assertExists()
    }

    @Test
    fun profileBackButtonReturnsHome() {
        openProfile()
        composeRule.onNodeWithTag(ProfileTestTags.Back).performClick()
        composeRule.onNodeWithTag(StudentListTestTags.Screen).assertIsDisplayed()
    }

    @Test
    fun savedNamePropagatesToHomeCard() {
        openProfile()
        val currentName = composeRule.onNodeWithTag(ProfileTestTags.DisplayedName)
            .fetchSemanticsNode().config[SemanticsProperties.Text].single().text
        val updatedName = if (currentName == "Muhammad Farhan Navigation") {
            "Muhammad Farhan Nav Verified"
        } else {
            "Muhammad Farhan Navigation"
        }
        composeRule.onNodeWithTag(ProfileTestTags.Edit).performScrollTo().performClick()
        composeRule.onNodeWithTag(ProfileTestTags.Name)
            .performTextReplacement(updatedName)
        composeRule.onNodeWithTag(ProfileTestTags.Save).performScrollTo().performClick()
        composeRule.onNodeWithTag(ProfileTestTags.Back).performClick()
        composeRule.onNodeWithContentDescription("Buka profil mahasiswa $updatedName").assertExists()
    }

    @Test
    fun tappingViewGradesOpensGradesScreen() {
        openProfile()
        composeRule.onNodeWithTag(ProfileTestTags.ViewGrades).performScrollTo().performClick()
        composeRule.onNodeWithTag(StudentGradesTestTags.Screen).assertIsDisplayed()
        composeRule.onNodeWithTag(StudentGradesTestTags.Back).assertIsDisplayed()
    }

    @Test
    fun backFromGradesReturnsToProfile() {
        openProfile()
        composeRule.onNodeWithTag(ProfileTestTags.ViewGrades).performScrollTo().performClick()
        composeRule.onNodeWithTag(StudentGradesTestTags.Back).performClick()
        composeRule.onNodeWithTag(ProfileTestTags.Screen).assertIsDisplayed()
    }

    private fun openProfile() {
        composeRule.onNodeWithTag(StudentListTestTags.PrimaryStudentCard).performClick()
    }
}
