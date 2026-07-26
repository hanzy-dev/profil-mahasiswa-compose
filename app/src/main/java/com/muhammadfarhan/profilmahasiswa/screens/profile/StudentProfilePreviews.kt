package com.muhammadfarhan.profilmahasiswa.screens.profile

import androidx.compose.runtime.Composable
import androidx.compose.material3.SnackbarHostState
import androidx.compose.ui.tooling.preview.Preview
import com.muhammadfarhan.profilmahasiswa.model.DefaultStudentProfile
import com.muhammadfarhan.profilmahasiswa.ui.theme.ProfilMahasiswaTheme

private val viewPreviewState = StudentProfileUiState(
    savedProfile = DefaultStudentProfile
)

private val viewWithPhotoPreviewState = StudentProfileUiState(
    savedProfile = DefaultStudentProfile.copy(profileImageUri = "https://example.com/photo.jpg")
)

private val editPreviewState = StudentProfileUiState(
    savedProfile = DefaultStudentProfile,
    draftProfile = DefaultStudentProfile.copy(name = "Muhammad Farhan A."),
    isEditing = true
)

private val editWithPhotoPreviewState = StudentProfileUiState(
    savedProfile = DefaultStudentProfile,
    draftProfile = DefaultStudentProfile.copy(profileImageUri = "https://example.com/photo.jpg"),
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
private fun PreviewProfileScreen(uiState: StudentProfileUiState, darkTheme: Boolean = false) {
    StudentProfileScreen(
        uiState = uiState,
        isDarkTheme = darkTheme,
        onToggleTheme = {},
        snackbarHostState = SnackbarHostState(),
        onEditClick = {},
        onSaveClick = {},
        onCancelClick = {},
        onNameChange = {},
        onStudyProgramChange = {},
        onEmailChange = {},
        onPhoneChange = {},
        onPickPhoto = {},
        onViewGradesClick = {},
        onBack = {}
    )
}

@Preview(showBackground = true, showSystemUi = true, name = "Profil - Terang")
@Composable
private fun ProfileLightPreview() {
    ProfilMahasiswaTheme(darkTheme = false) {
        PreviewProfileScreen(viewPreviewState, darkTheme = false)
    }
}

@Preview(showBackground = true, showSystemUi = true, name = "Profil - Gelap")
@Composable
private fun ProfileDarkPreview() {
    ProfilMahasiswaTheme(darkTheme = true) {
        PreviewProfileScreen(viewPreviewState, darkTheme = true)
    }
}

@Preview(showBackground = true, showSystemUi = true, name = "Profil dengan Foto")
@Composable
private fun ProfileWithPhotoPreview() {
    ProfilMahasiswaTheme {
        PreviewProfileScreen(viewWithPhotoPreviewState)
    }
}

@Preview(showBackground = true, showSystemUi = true, name = "Edit - Valid")
@Composable
private fun ProfileEditPreview() {
    ProfilMahasiswaTheme {
        PreviewProfileScreen(editPreviewState)
    }
}

@Preview(showBackground = true, showSystemUi = true, name = "Edit dengan Foto")
@Composable
private fun ProfileEditWithPhotoPreview() {
    ProfilMahasiswaTheme {
        PreviewProfileScreen(editWithPhotoPreviewState)
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

@Preview(
    showBackground = true,
    showSystemUi = true,
    name = "Landscape",
    device = "spec:width=640dp,height=360dp,dpi=420"
)
@Composable
private fun ProfileLandscapePreview() {
    ProfilMahasiswaTheme {
        PreviewProfileScreen(viewPreviewState)
    }
}
