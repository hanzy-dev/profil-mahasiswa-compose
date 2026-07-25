package com.muhammadfarhan.profilmahasiswa.screens.add

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.muhammadfarhan.profilmahasiswa.ui.theme.ProfilMahasiswaTheme

private val completedForm = AddStudentForm(
    name = "Mahasiswa Demo 01",
    studentId = "10000001",
    studyProgram = "Program Demo",
    semester = "2",
    email = "demo01@example.com",
    phone = "+62 812-0000-0000"
)

@Composable
private fun AddPreview(state: AddStudentUiState, dark: Boolean = false) {
    ProfilMahasiswaTheme(darkTheme = dark) {
        AddStudentScreen(state, {}, {}, {}, {})
    }
}

@Preview(showBackground = true, showSystemUi = true, name = "Tambah - Terang")
@Composable private fun BlankLightPreview() = AddPreview(AddStudentUiState())

@Preview(showBackground = true, showSystemUi = true, name = "Tambah - Gelap")
@Composable private fun BlankDarkPreview() = AddPreview(AddStudentUiState(), true)

@Preview(showBackground = true, showSystemUi = true, name = "Tambah - Error")
@Composable private fun ErrorPreview() = AddPreview(
    AddStudentUiState(
        errors = AddStudentFieldErrors(
            name = AddStudentFieldError.Required,
            studentId = AddStudentFieldError.Duplicate,
            semester = AddStudentFieldError.InvalidRange
        )
    )
)

@Preview(showBackground = true, showSystemUi = true, name = "Tambah - Lengkap")
@Composable private fun CompletePreview() = AddPreview(
    AddStudentUiState(form = completedForm, canSave = true)
)

@Preview(
    showBackground = true,
    showSystemUi = true,
    name = "Tambah - Layar Kecil",
    device = "spec:width=360dp,height=640dp,dpi=420"
)
@Composable private fun SmallPreview() = AddPreview(AddStudentUiState(form = completedForm))
