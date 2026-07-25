package com.muhammadfarhan.profilmahasiswa.screens.profile

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.material3.SnackbarHostState
import androidx.compose.ui.Modifier
import com.muhammadfarhan.profilmahasiswa.model.StudentProfile

@Composable
fun StudentProfileRoute(
    profile: StudentProfile,
    onProfileSaved: (StudentProfile) -> Unit,
    onBack: () -> Unit,
    snackbarHostState: SnackbarHostState,
    onProfileSaveSuccess: () -> Unit,
    modifier: Modifier = Modifier
) {
    var draftProfile by rememberSaveable(profile.studentId, stateSaver = StudentProfileSaver) {
        mutableStateOf(profile)
    }
    var isEditing by rememberSaveable(profile.studentId) { mutableStateOf(false) }
    var fieldErrors by rememberSaveable(profile.studentId, stateSaver = ProfileFieldErrorsSaver) {
        mutableStateOf(ProfileFieldErrors())
    }
    fun updateDraft(updatedProfile: StudentProfile) {
        draftProfile = updatedProfile
        fieldErrors = validateStudentProfile(updatedProfile)
    }

    StudentProfileScreen(
        uiState = StudentProfileUiState(
            savedProfile = profile,
            draftProfile = draftProfile,
            isEditing = isEditing,
            fieldErrors = fieldErrors
        ),
        snackbarHostState = snackbarHostState,
        onBack = onBack,
        onEditClick = {
            draftProfile = profile
            fieldErrors = ProfileFieldErrors()
            isEditing = true
        },
        onSaveClick = {
            val normalizedDraft = normalizeProfile(draftProfile)
            val validationErrors = validateStudentProfile(normalizedDraft)
            draftProfile = normalizedDraft
            fieldErrors = validationErrors

            if (!validationErrors.hasErrors) {
                isEditing = false
                onProfileSaveSuccess()
                onProfileSaved(normalizedDraft)
            }
        },
        onCancelClick = {
            draftProfile = profile
            fieldErrors = ProfileFieldErrors()
            isEditing = false
        },
        onNameChange = { name -> updateDraft(draftProfile.copy(name = name)) },
        onStudyProgramChange = { studyProgram ->
            updateDraft(draftProfile.copy(studyProgram = studyProgram))
        },
        onEmailChange = { email -> updateDraft(draftProfile.copy(email = email)) },
        onPhoneChange = { phone -> updateDraft(draftProfile.copy(phone = phone)) },
        modifier = modifier
    )
}
