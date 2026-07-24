package com.muhammadfarhan.profilmahasiswa.screens.profile

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.muhammadfarhan.profilmahasiswa.model.DefaultStudentProfile

@Composable
fun StudentProfileRoute() {
    var savedProfile by rememberSaveable(stateSaver = StudentProfileSaver) {
        mutableStateOf(DefaultStudentProfile)
    }
    var draftProfile by rememberSaveable(stateSaver = StudentProfileSaver) {
        mutableStateOf(DefaultStudentProfile)
    }
    var isEditing by rememberSaveable { mutableStateOf(false) }

    StudentProfileScreen(
        uiState = StudentProfileUiState(
            savedProfile = savedProfile,
            draftProfile = draftProfile,
            isEditing = isEditing
        ),
        onEditClick = {
            draftProfile = savedProfile
            isEditing = true
        },
        onSaveClick = {
            savedProfile = draftProfile
            isEditing = false
        },
        onCancelClick = {
            draftProfile = savedProfile
            isEditing = false
        },
        onNameChange = { name -> draftProfile = draftProfile.copy(name = name) },
        onStudyProgramChange = { studyProgram ->
            draftProfile = draftProfile.copy(studyProgram = studyProgram)
        },
        onEmailChange = { email -> draftProfile = draftProfile.copy(email = email) },
        onPhoneChange = { phone -> draftProfile = draftProfile.copy(phone = phone) }
    )
}
