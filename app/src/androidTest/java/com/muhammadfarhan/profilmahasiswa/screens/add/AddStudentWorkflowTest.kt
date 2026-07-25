package com.muhammadfarhan.profilmahasiswa.screens.add

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.compose.ui.test.performTextReplacement
import com.muhammadfarhan.profilmahasiswa.MainActivity
import com.muhammadfarhan.profilmahasiswa.screens.home.StudentListTestTags
import com.muhammadfarhan.profilmahasiswa.screens.profile.ProfileTestTags
import org.junit.Rule
import org.junit.Test

class AddStudentWorkflowTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Test fun homeFabOpensAddStudent() {
        openAdd()
        composeRule.onNodeWithTag(AddStudentTestTags.Screen).assertIsDisplayed()
    }

    @Test fun cancelReturnsHomeWithoutAddingStudent() {
        openAdd()
        composeRule.onNodeWithTag(AddStudentTestTags.Name).performTextReplacement("Tidak Disimpan")
        composeRule.onNodeWithTag(AddStudentTestTags.Cancel).performScrollTo().performClick()
        composeRule.onNodeWithTag(StudentListTestTags.Screen).assertExists()
        composeRule.onNodeWithText("Tidak Disimpan").assertDoesNotExist()
    }

    @Test fun incompleteFormShowsRequiredErrors() {
        openAdd()
        composeRule.onNodeWithTag(AddStudentTestTags.Name).performTextReplacement("Nama Test")
        composeRule.onNodeWithText("NIM wajib diisi").assertExists()
        composeRule.onNodeWithText("Semester wajib diisi").assertExists()
    }

    @Test fun invalidSemesterShowsIndonesianError() {
        openAdd()
        composeRule.onNodeWithTag(AddStudentTestTags.Semester).performTextReplacement("15")
        composeRule.onNodeWithText("Semester harus berada pada rentang 1 sampai 14").assertExists()
    }

    @Test fun duplicateNimShowsDuplicateError() {
        openAdd()
        composeRule.onNodeWithTag(AddStudentTestTags.StudentId)
            .performTextReplacement("23083000060")
        composeRule.onNodeWithText("NIM sudah terdaftar").assertExists()
    }

    @Test fun validFormReturnsHome() {
        addStudent("Batch Student 01", "71000001")
        composeRule.onNodeWithTag(StudentListTestTags.Screen).assertIsDisplayed()
    }

    @Test fun successfulAddShowsSnackbar() {
        addStudent("Batch Student 02", "71000002")
        composeRule.waitUntil(5_000) {
            composeRule.onAllNodes(hasText("Mahasiswa berhasil ditambahkan"))
                .fetchSemanticsNodes().size == 1
        }
    }

    @Test fun newlyAddedCardIsVisible() {
        addStudent("Batch Student 03", "71000003")
        composeRule.onNodeWithTag(StudentListTestTags.studentCard("71000003"))
            .assertIsDisplayed()
    }

    @Test fun studentCountIncreases() {
        val before = currentStudentCount()
        addStudent("Batch Student 04", "71000004")
        composeRule.onNodeWithText("Jumlah mahasiswa: ${before + 1}").assertExists()
    }

    @Test fun newlyAddedCardOpensProfile() {
        addStudent("Batch Student 05", "71000005")
        composeRule.onNodeWithTag(StudentListTestTags.studentCard("71000005")).performClick()
        composeRule.onNodeWithTag(ProfileTestTags.Screen).assertIsDisplayed()
    }

    @Test fun newProfileShowsEnteredIdentity() {
        addStudent("Batch Student 06", "71000006")
        composeRule.onNodeWithTag(StudentListTestTags.studentCard("71000006")).performClick()
        composeRule.onNodeWithText("Batch Student 06").assertExists()
        composeRule.onNodeWithText("NIM: 71000006").assertExists()
    }

    @Test fun addStudentFormSurvivesActivityRecreation() {
        openAdd()
        composeRule.onNodeWithTag(AddStudentTestTags.Name)
            .performTextReplacement("Draft Recreation")
        composeRule.activityRule.scenario.recreate()
        composeRule.onNodeWithText("Tambah Mahasiswa").assertExists()
        composeRule.onNodeWithTag(AddStudentTestTags.Name)
            .assertTextContains("Draft Recreation")
    }

    @Test fun systemBackReturnsHomeWithoutAdding() {
        openAdd()
        composeRule.onNodeWithTag(AddStudentTestTags.Name)
            .performTextReplacement("System Back Draft")
        composeRule.runOnUiThread {
            composeRule.activity.onBackPressedDispatcher.onBackPressed()
        }
        composeRule.onNodeWithTag(StudentListTestTags.Screen).assertIsDisplayed()
        composeRule.onNodeWithText("System Back Draft").assertDoesNotExist()
    }

    private fun openAdd() {
        composeRule.onNodeWithTag(StudentListTestTags.AddStudentFab).performClick()
    }

    private fun addStudent(name: String, studentId: String) {
        openAdd()
        enter(AddStudentTestTags.Name, name)
        enter(AddStudentTestTags.StudentId, studentId)
        enter(AddStudentTestTags.StudyProgram, "Program Test")
        enter(AddStudentTestTags.Semester, "3")
        enter(AddStudentTestTags.Email, "$studentId@example.com")
        enter(AddStudentTestTags.Phone, "+62 812-0000-0000")
        composeRule.onNodeWithTag(AddStudentTestTags.Save)
            .performScrollTo().assertIsEnabled().performClick()
    }

    private fun enter(tag: String, value: String) {
        composeRule.onNodeWithTag(tag).performScrollTo().performTextReplacement(value)
    }

    private fun currentStudentCount(): Int {
        val node = composeRule.onAllNodes(hasText("Jumlah mahasiswa:", substring = true))
            .fetchSemanticsNodes().single()
        return node.config[androidx.compose.ui.semantics.SemanticsProperties.Text]
            .single().text.substringAfterLast(' ').toInt()
    }
}
