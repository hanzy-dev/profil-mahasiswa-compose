package com.muhammadfarhan.profilmahasiswa.screens.profile

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.hasSetTextAction
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.compose.ui.test.performTextClearance
import androidx.compose.ui.test.performTextReplacement
import androidx.compose.ui.semantics.SemanticsProperties
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
                .size == 1
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

    private fun displayedProfileName(): String {
        return composeRule.onNodeWithTag(ProfileTestTags.DisplayedName)
            .fetchSemanticsNode()
            .config[SemanticsProperties.Text]
            .joinToString(separator = "") { it.text }
    }
}
