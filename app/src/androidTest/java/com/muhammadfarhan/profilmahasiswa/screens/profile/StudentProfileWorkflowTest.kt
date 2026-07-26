package com.muhammadfarhan.profilmahasiswa.screens.profile

import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.muhammadfarhan.profilmahasiswa.MainActivity
import com.muhammadfarhan.profilmahasiswa.screens.home.StudentListTestTags
import org.junit.Rule
import org.junit.Test

class StudentProfileWorkflowTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun profileContentIsDisplayed() {
        openPrimaryStudentProfile()
        composeRule.onNodeWithTag(ProfileTestTags.Screen).assertExists()
        composeRule.onNodeWithText("Muhammad Farhan").assertIsDisplayed()
        composeRule.onNodeWithText("NIM: 23083000060").assertExists()
        composeRule.onNodeWithText("Mahasiswa Aktif").assertExists()
        composeRule.onNodeWithText("Informasi Kontak").assertExists()
        
        // Batch 7D: View Grades button exists in view mode
        composeRule.onNodeWithTag(ProfileTestTags.ViewGrades).assertIsDisplayed()
    }

    @Test
    fun editActionShowsEditableFieldsAndActions() {
        enterEditMode()

        composeRule.onNodeWithTag(ProfileTestTags.Name).assertIsEnabled()
        composeRule.onNodeWithTag(ProfileTestTags.StudyProgram).assertIsEnabled()
        composeRule.onNodeWithTag(ProfileTestTags.Email).assertIsEnabled()
        composeRule.onNodeWithTag(ProfileTestTags.Phone).assertIsEnabled()
        composeRule.onNodeWithTag(ProfileTestTags.Save).assertExists()
        composeRule.onNodeWithTag(ProfileTestTags.Cancel).assertExists()
        
        // Batch 7D: Photo action appears in edit mode
        composeRule.onNodeWithTag(ProfileTestTags.PhotoAction).assertIsDisplayed()
    }

    @Test
    fun photoActionHasCorrectAccessibility() {
        enterEditMode()
        // Default Farhan has no photo, so it should say "Pilih foto profil"
        composeRule.onNodeWithTag(ProfileTestTags.PhotoAction)
            .assertContentDescriptionEquals("Pilih foto profil")
    }

    @Test
    fun invalidEmailShowsErrorAndPreventsSave() {
        enterEditMode()

        composeRule.onNodeWithTag(ProfileTestTags.Email).performTextClearance()
        composeRule.onNodeWithText("Email wajib diisi").assertExists()
        composeRule.onNodeWithTag(ProfileTestTags.Save)
            .performScrollTo()
            .assertIsNotEnabled()
        composeRule.onNodeWithText("Edit Profil").assertExists()
    }

    @Test
    fun cancelDiscardsDraftChanges() {
        openPrimaryStudentProfile()
        val originalName = displayedProfileName()
        composeRule.onNodeWithTag(ProfileTestTags.Edit)
            .performScrollTo()
            .performClick()

        composeRule.onNodeWithTag(ProfileTestTags.Name)
            .performTextReplacement("Nama Sementara")
        composeRule.onNodeWithTag(ProfileTestTags.Cancel)
            .performScrollTo()
            .performClick()

        composeRule.onNodeWithTag(ProfileTestTags.DisplayedName)
            .assertTextEquals(originalName)
        composeRule.onNodeWithText("Nama Sementara").assertDoesNotExist()
        composeRule.onNodeWithTag(ProfileTestTags.Edit).assertExists()
    }

    @Test
    fun validChangesCanBeSavedAndConfirmed() {
        returnToHome()
        composeRule.activityRule.scenario.recreate()
        openPrimaryStudentProfile()
        val updatedName = if (displayedProfileName() == "Muhammad Farhan Workflow") {
            "Muhammad Farhan Verified"
        } else {
            "Muhammad Farhan Workflow"
        }
        composeRule.onNodeWithTag(ProfileTestTags.Edit)
            .performScrollTo()
            .performClick()

        composeRule.onNodeWithTag(ProfileTestTags.Name)
            .performTextReplacement(updatedName)
        composeRule.onNodeWithTag(ProfileTestTags.Save)
            .performScrollTo()
            .assertIsEnabled()
            .performClick()

        composeRule.onNodeWithTag(ProfileTestTags.DisplayedName)
            .assertTextEquals(updatedName)
        composeRule.waitUntil(timeoutMillis = 5_000) {
            composeRule.onAllNodes(hasText("Profil berhasil diperbarui"))
                .fetchSemanticsNodes()
                .isNotEmpty()
        }
        composeRule.onNodeWithTag(ProfileTestTags.Edit).assertExists()
    }

    @Test
    fun studentIdAndSemesterRemainReadOnlyDuringEditing() {
        enterEditMode()

        composeRule.onNodeWithText("NIM: 23083000060").assertExists()
        composeRule.onNodeWithText("S1 Sistem Informasi • Semester 6").assertExists()
        composeRule.onNode(
            hasText("NIM: 23083000060") and hasSetTextAction()
        ).assertDoesNotExist()
        composeRule.onNode(
            hasText("S1 Sistem Informasi • Semester 6") and hasSetTextAction()
        ).assertDoesNotExist()
    }

    private fun enterEditMode() {
        openPrimaryStudentProfile()
        composeRule.onNodeWithTag(ProfileTestTags.Edit)
            .performScrollTo()
            .performClick()
    }

    private fun openPrimaryStudentProfile() {
        if (composeRule.onAllNodes(hasText("Daftar Mahasiswa")).fetchSemanticsNodes().isNotEmpty()) {
            composeRule.onNodeWithTag(StudentListTestTags.PrimaryStudentCard).performClick()
        }
    }

    private fun returnToHome() {
        if (composeRule.onAllNodes(hasText("Profil Mahasiswa")).fetchSemanticsNodes().isNotEmpty()) {
            composeRule.onNodeWithTag(ProfileTestTags.Back).performClick()
        }
    }

    private fun displayedProfileName(): String {
        return composeRule.onNodeWithTag(ProfileTestTags.DisplayedName)
            .fetchSemanticsNode()
            .config[SemanticsProperties.Text]
            .joinToString(separator = "") { it.text }
    }
}
