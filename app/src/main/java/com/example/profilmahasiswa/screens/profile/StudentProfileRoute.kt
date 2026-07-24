package com.example.profilmahasiswa.screens.profile

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.example.profilmahasiswa.model.DefaultStudentProfile

@Composable
fun StudentProfileRoute() {
    var profile by rememberSaveable(stateSaver = StudentProfileSaver) {
        mutableStateOf(DefaultStudentProfile)
    }
    var isEditing by rememberSaveable { mutableStateOf(false) }

    StudentProfileScreen(
        uiState = StudentProfileUiState(
            profile = profile,
            isEditing = isEditing
        ),
        onEditClick = { isEditing = true },
        onDoneClick = { isEditing = false },
        onEmailChange = { email -> profile = profile.copy(email = email) },
        onPhoneChange = { phone -> profile = profile.copy(phone = phone) }
    )
}
