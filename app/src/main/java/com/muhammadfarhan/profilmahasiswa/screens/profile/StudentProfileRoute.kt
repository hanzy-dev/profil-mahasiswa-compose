package com.muhammadfarhan.profilmahasiswa.screens.profile

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.material3.SnackbarHostState
import androidx.compose.ui.res.stringResource
import com.muhammadfarhan.profilmahasiswa.R
import com.muhammadfarhan.profilmahasiswa.model.DefaultStudentProfile
import com.muhammadfarhan.profilmahasiswa.model.StudentProfile
import kotlinx.coroutines.launch

@Composable
fun StudentProfileRoute() {
    var savedProfile by rememberSaveable(stateSaver = StudentProfileSaver) {
        mutableStateOf(DefaultStudentProfile)
    }
    var draftProfile by rememberSaveable(stateSaver = StudentProfileSaver) {
        mutableStateOf(DefaultStudentProfile)
    }
    var isEditing by rememberSaveable { mutableStateOf(false) }
    var fieldErrors by rememberSaveable(stateSaver = ProfileFieldErrorsSaver) {
        mutableStateOf(ProfileFieldErrors())
    }
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val successMessage = stringResource(R.string.profile_update_success)

    fun updateDraft(updatedProfile: StudentProfile) {
        draftProfile = updatedProfile
        fieldErrors = validateStudentProfile(updatedProfile)
    }

    StudentProfileScreen(
        uiState = StudentProfileUiState(
            savedProfile = savedProfile,
            draftProfile = draftProfile,
            isEditing = isEditing,
            fieldErrors = fieldErrors
        ),
        snackbarHostState = snackbarHostState,
        onEditClick = {
            draftProfile = savedProfile
            fieldErrors = ProfileFieldErrors()
            isEditing = true
        },
        onSaveClick = {
            val normalizedDraft = normalizeProfile(draftProfile)
            val validationErrors = validateStudentProfile(normalizedDraft)
            draftProfile = normalizedDraft
            fieldErrors = validationErrors

            if (!validationErrors.hasErrors) {
                savedProfile = normalizedDraft
                isEditing = false
                coroutineScope.launch {
                    snackbarHostState.showSnackbar(successMessage)
                }
            }
        },
        onCancelClick = {
            draftProfile = savedProfile
            fieldErrors = ProfileFieldErrors()
            isEditing = false
        },
        onNameChange = { name -> updateDraft(draftProfile.copy(name = name)) },
        onStudyProgramChange = { studyProgram ->
            updateDraft(draftProfile.copy(studyProgram = studyProgram))
        },
        onEmailChange = { email -> updateDraft(draftProfile.copy(email = email)) },
        onPhoneChange = { phone -> updateDraft(draftProfile.copy(phone = phone)) }
    )
}
