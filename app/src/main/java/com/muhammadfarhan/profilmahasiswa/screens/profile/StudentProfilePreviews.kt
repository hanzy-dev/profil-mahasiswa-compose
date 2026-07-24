package com.muhammadfarhan.profilmahasiswa.screens.profile

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.muhammadfarhan.profilmahasiswa.model.DefaultStudentProfile
import com.muhammadfarhan.profilmahasiswa.ui.theme.ProfilMahasiswaTheme

private val viewPreviewState = StudentProfileUiState(
    savedProfile = DefaultStudentProfile
)

private val editPreviewState = StudentProfileUiState(
    savedProfile = DefaultStudentProfile,
    draftProfile = DefaultStudentProfile.copy(name = "Muhammad Farhan A."),
    isEditing = true
)

private val errorPreviewState = StudentProfileUiState(
    savedProfile = DefaultStudentProfile,
    draftProfile = DefaultStudentProfile.copy(
        name = "",
        email = "email-tidak-valid"
    ),
    isEditing = true,
    fieldErrors = ProfileFieldErrors(
        name = ProfileFieldError.Required,
        email = ProfileFieldError.InvalidFormat
    )
)

@Composable
private fun PreviewProfileScreen(uiState: StudentProfileUiState) {
    StudentProfileScreen(
        uiState = uiState,
        snackbarHostState = SnackbarHostState(),
        onEditClick = {},
        onSaveClick = {},
        onCancelClick = {},
        onNameChange = {},
        onStudyProgramChange = {},
        onEmailChange = {},
        onPhoneChange = {}
    )
}

@Preview(showBackground = true, showSystemUi = true, name = "Profil - Terang")
@Composable
private fun ProfileLightPreview() {
    ProfilMahasiswaTheme(darkTheme = false) {
        PreviewProfileScreen(viewPreviewState)
    }
}

@Preview(showBackground = true, showSystemUi = true, name = "Profil - Gelap")
@Composable
private fun ProfileDarkPreview() {
    ProfilMahasiswaTheme(darkTheme = true) {
        PreviewProfileScreen(viewPreviewState)
    }
}

@Preview(showBackground = true, showSystemUi = true, name = "Edit - Valid")
@Composable
private fun ProfileEditPreview() {
    ProfilMahasiswaTheme {
        PreviewProfileScreen(editPreviewState)
    }
}

@Preview(showBackground = true, showSystemUi = true, name = "Edit - Error")
@Composable
private fun ProfileErrorPreview() {
    ProfilMahasiswaTheme {
        PreviewProfileScreen(errorPreviewState)
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
    name = "Layar Kecil",
    device = "spec:width=360dp,height=640dp,dpi=420"
)
@Composable
private fun ProfileSmallScreenPreview() {
    ProfilMahasiswaTheme {
        PreviewProfileScreen(editPreviewState)
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
    name = "Teks Besar",
    fontScale = 1.5f
)
@Composable
private fun ProfileLargeTextPreview() {
    ProfilMahasiswaTheme {
        PreviewProfileScreen(viewPreviewState)
    }
}
