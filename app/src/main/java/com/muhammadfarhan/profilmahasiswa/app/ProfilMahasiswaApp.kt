package com.muhammadfarhan.profilmahasiswa.app

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.muhammadfarhan.profilmahasiswa.navigation.AppNavigation
import com.muhammadfarhan.profilmahasiswa.ui.theme.ProfilMahasiswaTheme

@Composable
fun ProfilMahasiswaApp() {
    var appState by rememberSaveable(stateSaver = StudentAppStateSaver) {
        mutableStateOf(DefaultStudentAppState)
    }
    val systemDarkTheme = isSystemInDarkTheme()
    val isDarkTheme = resolveDarkTheme(appState.themeMode, systemDarkTheme)

    ProfilMahasiswaTheme(darkTheme = isDarkTheme) {
        AppNavigation(
            students = appState.students,
            gradesByStudentId = appState.gradesByStudentId,
            isDarkTheme = isDarkTheme,
            onToggleTheme = {
                appState = appState.updateThemeMode(nextThemeMode(isDarkTheme))
            },
            onProfileSaved = { profile ->
                appState = appState.updateStudent(profile)
            },
            onStudentCreated = { profile ->
                appState = appState.addStudent(profile)
            }
        )
    }
}
