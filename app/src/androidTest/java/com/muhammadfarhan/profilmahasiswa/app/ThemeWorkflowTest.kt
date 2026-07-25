package com.muhammadfarhan.profilmahasiswa.app

import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.hasStateDescription
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.compose.ui.test.performTextReplacement
import com.muhammadfarhan.profilmahasiswa.MainActivity
import com.muhammadfarhan.profilmahasiswa.screens.add.AddStudentTestTags
import com.muhammadfarhan.profilmahasiswa.screens.home.StudentListTestTags
import com.muhammadfarhan.profilmahasiswa.screens.profile.ProfileTestTags
import com.muhammadfarhan.profilmahasiswa.ui.components.THEME_TOGGLE_BUTTON_TAG
import org.junit.Rule
import org.junit.Test

class ThemeWorkflowTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun homeDisplaysThemeToggleButton() {
        composeRule.onNodeWithTag(THEME_TOGGLE_BUTTON_TAG).assertIsDisplayed()
    }

    @Test
    fun themeTogglingChangesActionDescription() {
        // Starts with one of them, click to toggle, then click back
        val isInitiallyDark = composeRule.onAllNodes(hasStateDescription("Tema gelap aktif")).fetchSemanticsNodes().isNotEmpty()

        val expectedFirstAction = if (isInitiallyDark) "Aktifkan tema terang" else "Aktifkan tema gelap"
        val expectedSecondAction = if (isInitiallyDark) "Aktifkan tema gelap" else "Aktifkan tema terang"
        
        composeRule.onNodeWithContentDescription(expectedFirstAction).assertIsDisplayed().performClick()
        composeRule.onNodeWithContentDescription(expectedSecondAction).assertIsDisplayed().performClick()
        composeRule.onNodeWithContentDescription(expectedFirstAction).assertIsDisplayed()
    }

    @Test
    fun themeSelectionRemainsActiveAfterOpeningProfile() {
        val isInitiallyDark = composeRule.onAllNodes(hasStateDescription("Tema gelap aktif")).fetchSemanticsNodes().isNotEmpty()
        val expectedFirstAction = if (isInitiallyDark) "Aktifkan tema terang" else "Aktifkan tema gelap"
        val expectedStateAfterToggle = if (isInitiallyDark) "Tema terang aktif" else "Tema gelap aktif"

        // Toggle
        composeRule.onNodeWithContentDescription(expectedFirstAction).performClick()

        // Open Profile
        composeRule.onNodeWithTag(StudentListTestTags.PrimaryStudentCard).performClick()

        // Verify state is preserved
        composeRule.onNode(hasStateDescription(expectedStateAfterToggle)).assertIsDisplayed()
    }

    @Test
    fun themeSelectionRemainsActiveAfterOpeningAddStudent() {
        val isInitiallyDark = composeRule.onAllNodes(hasStateDescription("Tema gelap aktif")).fetchSemanticsNodes().isNotEmpty()
        val expectedFirstAction = if (isInitiallyDark) "Aktifkan tema terang" else "Aktifkan tema gelap"
        val expectedStateAfterToggle = if (isInitiallyDark) "Tema terang aktif" else "Tema gelap aktif"

        // Toggle
        composeRule.onNodeWithContentDescription(expectedFirstAction).performClick()

        // Open Add
        composeRule.onNodeWithTag(StudentListTestTags.AddStudentFab).performClick()

        // Verify state is preserved
        composeRule.onNode(hasStateDescription(expectedStateAfterToggle)).assertIsDisplayed()
    }

    @Test
    fun themeSelectionRemainsActiveWhenReturningHome() {
        // Go to Add Student
        composeRule.onNodeWithTag(StudentListTestTags.AddStudentFab).performClick()
        
        val isInitiallyDark = composeRule.onAllNodes(hasStateDescription("Tema gelap aktif")).fetchSemanticsNodes().isNotEmpty()
        val expectedFirstAction = if (isInitiallyDark) "Aktifkan tema terang" else "Aktifkan tema gelap"
        val expectedStateAfterToggle = if (isInitiallyDark) "Tema terang aktif" else "Tema gelap aktif"

        // Toggle in Add Student
        composeRule.onNodeWithContentDescription(expectedFirstAction).performClick()

        // Return Home
        composeRule.runOnUiThread {
            composeRule.activity.onBackPressedDispatcher.onBackPressed()
        }

        // Verify state is preserved
        composeRule.onNode(hasStateDescription(expectedStateAfterToggle)).assertIsDisplayed()
    }

    @Test
    fun themeSelectionSurvivesActivityRecreation() {
        val isInitiallyDark = composeRule.onAllNodes(hasStateDescription("Tema gelap aktif")).fetchSemanticsNodes().isNotEmpty()
        val expectedFirstAction = if (isInitiallyDark) "Aktifkan tema terang" else "Aktifkan tema gelap"
        val expectedStateAfterToggle = if (isInitiallyDark) "Tema terang aktif" else "Tema gelap aktif"

        composeRule.onNodeWithContentDescription(expectedFirstAction).performClick()
        
        composeRule.activityRule.scenario.recreate()
        
        composeRule.onNode(hasStateDescription(expectedStateAfterToggle)).assertIsDisplayed()
    }

    @Test
    fun switchingThemeDoesNotClearAddStudentDraft() {
        composeRule.onNodeWithTag(StudentListTestTags.AddStudentFab).performClick()
        
        composeRule.onNodeWithTag(AddStudentTestTags.Name).performTextReplacement("Draft Name")
        
        val isInitiallyDark = composeRule.onAllNodes(hasStateDescription("Tema gelap aktif")).fetchSemanticsNodes().isNotEmpty()
        val expectedFirstAction = if (isInitiallyDark) "Aktifkan tema terang" else "Aktifkan tema gelap"
        
        composeRule.onNodeWithContentDescription(expectedFirstAction).performClick()
        
        composeRule.onNodeWithText("Draft Name").assertIsDisplayed()
    }

    @Test
    fun switchingThemeDoesNotLeaveProfileEditMode() {
        composeRule.onNodeWithTag(StudentListTestTags.PrimaryStudentCard).performClick()
        composeRule.onNodeWithTag(ProfileTestTags.Edit).performScrollTo().performClick()

        val isInitiallyDark = composeRule.onAllNodes(hasStateDescription("Tema gelap aktif")).fetchSemanticsNodes().isNotEmpty()
        val expectedFirstAction = if (isInitiallyDark) "Aktifkan tema terang" else "Aktifkan tema gelap"

        composeRule.onNodeWithContentDescription(expectedFirstAction).performClick()

        // Scroll to name field and Save button to verify editing mode is preserved
        composeRule.onNodeWithTag(ProfileTestTags.Name).performScrollTo().assertIsDisplayed()
        composeRule.onNodeWithTag(ProfileTestTags.Save).performScrollTo().assertIsDisplayed()
    }

    @Test
    fun switchingThemeDoesNotResetStudentCount() {
        val node = composeRule.onAllNodes(hasText("Jumlah mahasiswa:", substring = true))
            .fetchSemanticsNodes().single()
        val beforeText = node.config[SemanticsProperties.Text].single().text

        val isInitiallyDark = composeRule.onAllNodes(hasStateDescription("Tema gelap aktif")).fetchSemanticsNodes().isNotEmpty()
        val expectedFirstAction = if (isInitiallyDark) "Aktifkan tema terang" else "Aktifkan tema gelap"
        
        composeRule.onNodeWithContentDescription(expectedFirstAction).performClick()

        composeRule.onNodeWithText(beforeText).assertIsDisplayed()
    }
}
