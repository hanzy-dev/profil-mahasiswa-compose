package com.muhammadfarhan.profilmahasiswa.screens.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.muhammadfarhan.profilmahasiswa.R
import com.muhammadfarhan.profilmahasiswa.ui.theme.ProfilMahasiswaTheme

import androidx.compose.material3.Scaffold
import com.muhammadfarhan.profilmahasiswa.ui.components.AppTopBar
import com.muhammadfarhan.profilmahasiswa.ui.components.ThemeToggleButton

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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.student_not_found_title),
                style = MaterialTheme.typography.headlineSmall,
                textAlign = TextAlign.Center
            )
            Text(
                text = stringResource(R.string.student_not_found_message),
                modifier = Modifier.padding(top = 8.dp, bottom = 24.dp),
                textAlign = TextAlign.Center
            )
            Button(
                onClick = onBack,
                modifier = Modifier.testTag(StudentNotFoundTestTags.BackButton)
            ) {
                Text(text = stringResource(R.string.back_to_list))
            }
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
