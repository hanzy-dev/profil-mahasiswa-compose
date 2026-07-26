package com.muhammadfarhan.profilmahasiswa.screens.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SearchOff
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.muhammadfarhan.profilmahasiswa.R
import com.muhammadfarhan.profilmahasiswa.ui.components.AppTopBar
import com.muhammadfarhan.profilmahasiswa.ui.components.EmptyState
import com.muhammadfarhan.profilmahasiswa.ui.components.ThemeToggleButton
import com.muhammadfarhan.profilmahasiswa.ui.theme.ProfilMahasiswaTheme

object StudentNotFoundTestTags {
    const val Screen = "student_not_found_screen"
    const val BackButton = "student_not_found_back_button"
}

@Composable
fun StudentNotFoundScreen(
    isDarkTheme: Boolean,
    onToggleTheme: () -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier.testTag(StudentNotFoundTestTags.Screen),
        topBar = {
            AppTopBar(
                title = stringResource(R.string.app_name),
                onBack = onBack,
                actions = { ThemeToggleButton(isDarkTheme, onToggleTheme) }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            EmptyState(
                icon = Icons.Default.SearchOff,
                title = stringResource(R.string.student_not_found_title),
                message = stringResource(R.string.student_not_found_message),
                action = {
                    Button(
                        onClick = onBack,
                        modifier = Modifier.testTag(StudentNotFoundTestTags.BackButton)
                    ) {
                        Text(text = stringResource(R.string.back_to_list))
                    }
                }
            )
        }
    }
}

@Preview(showBackground = true, name = "Not Found - Light")
@Composable
private fun StudentNotFoundLightPreview() {
    ProfilMahasiswaTheme(darkTheme = false) {
        StudentNotFoundScreen(isDarkTheme = false, onToggleTheme = {}, onBack = {})
    }
}

@Preview(showBackground = true, name = "Not Found - Dark")
@Composable
private fun StudentNotFoundDarkPreview() {
    ProfilMahasiswaTheme(darkTheme = true) {
        StudentNotFoundScreen(isDarkTheme = true, onToggleTheme = {}, onBack = {})
    }
}
