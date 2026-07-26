package com.muhammadfarhan.profilmahasiswa.screens.grades

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.muhammadfarhan.profilmahasiswa.app.FarhanGrades
import com.muhammadfarhan.profilmahasiswa.model.DefaultStudentProfile
import com.muhammadfarhan.profilmahasiswa.ui.theme.ProfilMahasiswaTheme

@Preview(showBackground = true, showSystemUi = true, name = "Grades Light")
@Composable
fun GradesLightPreview() {
    ProfilMahasiswaTheme(darkTheme = false) {
        StudentGradesScreen(
            student = DefaultStudentProfile,
            grades = FarhanGrades,
            isDarkTheme = false,
            onToggleTheme = {},
            onBack = {}
        )
    }
}

@Preview(showBackground = true, showSystemUi = true, name = "Grades Dark")
@Composable
fun GradesDarkPreview() {
    ProfilMahasiswaTheme(darkTheme = true) {
        StudentGradesScreen(
            student = DefaultStudentProfile,
            grades = FarhanGrades,
            isDarkTheme = true,
            onToggleTheme = {},
            onBack = {}
        )
    }
}

@Preview(showBackground = true, showSystemUi = true, name = "Grades Empty")
@Composable
fun GradesEmptyPreview() {
    ProfilMahasiswaTheme {
        StudentGradesScreen(
            student = DefaultStudentProfile,
            grades = emptyList(),
            isDarkTheme = false,
            onToggleTheme = {},
            onBack = {}
        )
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
    name = "Grades Small Screen",
    device = "spec:width=360dp,height=640dp,dpi=420"
)
@Composable
fun GradesSmallScreenPreview() {
    ProfilMahasiswaTheme {
        StudentGradesScreen(
            student = DefaultStudentProfile,
            grades = FarhanGrades,
            isDarkTheme = false,
            onToggleTheme = {},
            onBack = {}
        )
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
    name = "Grades Font Scale 1.5x",
    fontScale = 1.5f
)
@Composable
fun GradesFontScalePreview() {
    ProfilMahasiswaTheme {
        StudentGradesScreen(
            student = DefaultStudentProfile,
            grades = FarhanGrades,
            isDarkTheme = false,
            onToggleTheme = {},
            onBack = {}
        )
    }
}
