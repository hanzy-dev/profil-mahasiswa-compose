package com.muhammadfarhan.profilmahasiswa.screens.profile

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.compose.ui.test.performTextClearance
import androidx.compose.ui.test.performTextReplacement
import com.muhammadfarhan.profilmahasiswa.MainActivity
import org.junit.Rule
import org.junit.Test

class StudentProfileWorkflowTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun profileContentIsDisplayed() {
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
        enterEditMode()

        composeRule.onNodeWithTag(ProfileTestTags.Name)
            .performTextReplacement("Nama Sementara")
        composeRule.onNodeWithTag(ProfileTestTags.Cancel)
            .performScrollTo()
            .performClick()

        composeRule.onNodeWithText("Muhammad Farhan").assertIsDisplayed()
        composeRule.onNodeWithText("Nama Sementara").assertDoesNotExist()
        composeRule.onNodeWithTag(ProfileTestTags.Edit).assertExists()
    }

    @Test
    fun validChangesCanBeSavedAndConfirmed() {
        enterEditMode()

        composeRule.onNodeWithTag(ProfileTestTags.Name)
            .performTextReplacement("Muhammad Farhan A")
        composeRule.onNodeWithTag(ProfileTestTags.Save)
            .performScrollTo()
            .assertIsEnabled()
            .performClick()

        composeRule.onNodeWithText("Muhammad Farhan A").assertIsDisplayed()
        composeRule.onNodeWithText("Profil berhasil diperbarui").assertExists()
        composeRule.onNodeWithTag(ProfileTestTags.Edit).assertExists()
    }

    @Test
    fun studentIdAndSemesterRemainReadOnlyDuringEditing() {
        enterEditMode()

        composeRule.onNodeWithText("NIM: 23083000060").assertExists()
        composeRule.onNodeWithText("S1 Sistem Informasi", substring = true).assertExists()
        composeRule.onNodeWithText("Semester 6", substring = true).assertExists()
        composeRule.onNodeWithTag("profile_student_id").assertDoesNotExist()
        composeRule.onNodeWithTag("profile_semester").assertDoesNotExist()
    }

    private fun enterEditMode() {
        composeRule.onNodeWithTag(ProfileTestTags.Edit)
            .performScrollTo()
            .performClick()
    }
}
