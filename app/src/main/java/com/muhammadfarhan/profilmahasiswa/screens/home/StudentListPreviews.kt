package com.muhammadfarhan.profilmahasiswa.screens.home

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.muhammadfarhan.profilmahasiswa.model.DefaultStudentProfile
import com.muhammadfarhan.profilmahasiswa.ui.theme.ProfilMahasiswaTheme

@Preview(showBackground = true, showSystemUi = true, name = "Daftar - Terang")
@Composable
private fun StudentListLightPreview() {
    ProfilMahasiswaTheme(darkTheme = false) {
        StudentListScreen(listOf(DefaultStudentProfile), {})
    }
}

@Preview(showBackground = true, showSystemUi = true, name = "Daftar - Gelap")
@Composable
private fun StudentListDarkPreview() {
    ProfilMahasiswaTheme(darkTheme = true) {
        StudentListScreen(listOf(DefaultStudentProfile), {})
    }
}
