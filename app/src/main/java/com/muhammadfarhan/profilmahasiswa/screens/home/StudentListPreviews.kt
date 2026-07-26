package com.muhammadfarhan.profilmahasiswa.screens.home

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material3.SnackbarHostState
import com.muhammadfarhan.profilmahasiswa.model.DefaultStudentProfile
import com.muhammadfarhan.profilmahasiswa.model.StudentProfile
import com.muhammadfarhan.profilmahasiswa.ui.theme.ProfilMahasiswaTheme

@Preview(showBackground = true, showSystemUi = true, name = "Daftar - Terang")
@Composable
private fun StudentListLightPreview() {
    ProfilMahasiswaTheme(darkTheme = false) {
        PreviewList(listOf(DefaultStudentProfile))
    }
}

@Preview(showBackground = true, showSystemUi = true, name = "Daftar - Gelap")
@Composable
private fun StudentListDarkPreview() {
    ProfilMahasiswaTheme(darkTheme = true) {
        PreviewList(previewStudents, darkTheme = true)
    }
}

@Preview(showBackground = true, showSystemUi = true, name = "Daftar - Banyak")
@Composable
private fun StudentListMultiplePreview() {
    ProfilMahasiswaTheme { PreviewList(previewStudents) }
}

@Preview(showBackground = true, showSystemUi = true, name = "Daftar - Kosong")
@Composable
private fun StudentListEmptyPreview() {
    ProfilMahasiswaTheme { PreviewList(emptyList()) }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
    name = "Daftar - Layar Kecil",
    device = "spec:width=360dp,height=640dp,dpi=420"
)
@Composable
private fun StudentListSmallScreenPreview() {
    ProfilMahasiswaTheme { PreviewList(previewStudents) }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
    name = "Daftar - Teks Besar",
    fontScale = 1.5f
)
@Composable
private fun StudentListLargeTextPreview() {
    ProfilMahasiswaTheme { PreviewList(previewStudents) }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
    name = "Daftar - Landscape",
    device = "spec:width=640dp,height=360dp,dpi=420"
)
@Composable
private fun StudentListLandscapePreview() {
    ProfilMahasiswaTheme { PreviewList(previewStudents) }
}

@Composable
private fun PreviewList(students: List<StudentProfile>, darkTheme: Boolean = false) {
    StudentListScreen(
        students = students,
        isDarkTheme = darkTheme,
        onToggleTheme = {},
        snackbarHostState = SnackbarHostState(),
        onStudentClick = {},
        onAddStudent = {}
    )
}

private val previewStudents = listOf(
    DefaultStudentProfile,
    StudentProfile("Mahasiswa Demo 01", "10000001", "Program Demo", 2,
        "demo01@example.com", "+62 8xx-xxxx-xxxx"),
    StudentProfile("Mahasiswa Demo 02", "10000002", "Program Demo", 4,
        "demo02@example.com", "+62 8xx-xxxx-xxxx")
)
