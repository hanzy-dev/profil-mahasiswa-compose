package com.muhammadfarhan.profilmahasiswa.ui.components

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.tooling.preview.Preview
import com.muhammadfarhan.profilmahasiswa.R
import com.muhammadfarhan.profilmahasiswa.ui.theme.ProfilMahasiswaTheme

const val THEME_TOGGLE_BUTTON_TAG = "theme_toggle_button"

@Composable
fun ThemeToggleButton(
    isDarkTheme: Boolean,
    onToggleTheme: () -> Unit,
    modifier: Modifier = Modifier
) {
    val stateDescriptionStr = stringResource(
        if (isDarkTheme) R.string.state_dark_theme_active else R.string.state_light_theme_active
    )
    val actionDescriptionStr = stringResource(
        if (isDarkTheme) R.string.action_enable_light_theme else R.string.action_enable_dark_theme
    )

    IconButton(
        onClick = onToggleTheme,
        modifier = modifier
            .testTag(THEME_TOGGLE_BUTTON_TAG)
            .semantics {
                stateDescription = stateDescriptionStr
            }
    ) {
        AnimatedContent(
            targetState = isDarkTheme,
            transitionSpec = {
                (fadeIn(animationSpec = tween(200)) + scaleIn(initialScale = 0.8f))
                    .togetherWith(fadeOut(animationSpec = tween(200)) + scaleOut(targetScale = 0.8f))
            },
            label = "ThemeIconTransition"
        ) { targetIsDark ->
            Icon(
                imageVector = if (targetIsDark) Icons.Filled.LightMode else Icons.Filled.DarkMode,
                contentDescription = actionDescriptionStr,
                tint = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
    }
}

@Preview(showBackground = true, name = "Theme Toggle - Light")
@Composable
private fun ThemeToggleButtonLightPreview() {
    ProfilMahasiswaTheme(darkTheme = false) {
        ThemeToggleButton(isDarkTheme = false, onToggleTheme = {})
    }
}

@Preview(showBackground = true, name = "Theme Toggle - Dark")
@Composable
private fun ThemeToggleButtonDarkPreview() {
    ProfilMahasiswaTheme(darkTheme = true) {
        ThemeToggleButton(isDarkTheme = true, onToggleTheme = {})
    }
}
