package com.muhammadfarhan.profilmahasiswa.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.semantics.ProgressBarRangeInfo
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.muhammadfarhan.profilmahasiswa.ui.components.AppLoading
import com.muhammadfarhan.profilmahasiswa.ui.components.EmptyState
import com.muhammadfarhan.profilmahasiswa.ui.theme.ProfilMahasiswaTheme
import org.junit.Rule
import org.junit.Test

class ProfessionalUxTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun appLoadingDisplaysMessage() {
        val testMessage = "Memuat data..."
        composeRule.setContent {
            ProfilMahasiswaTheme {
                AppLoading(message = testMessage)
            }
        }

        composeRule.onNodeWithText(testMessage).assertIsDisplayed()
        // Check for indeterminate progress bar
        composeRule.onNode(hasProgressBarRangeInfo(ProgressBarRangeInfo.Indeterminate)).assertExists()
    }

    @Test
    fun emptyStateDisplaysContentAndAction() {
        val title = "Kosong"
        val message = "Tidak ada data"
        var actionClicked = false

        composeRule.setContent {
            ProfilMahasiswaTheme {
                EmptyState(
                    icon = Icons.Default.Info,
                    title = title,
                    message = message,
                    action = {
                        Button(onClick = { actionClicked = true }) {
                            Text("Klik Saya")
                        }
                    }
                )
            }
        }

        composeRule.onNodeWithText(title).assertIsDisplayed()
        composeRule.onNodeWithText(message).assertIsDisplayed()
        composeRule.onNodeWithText("Klik Saya").performClick()
        assert(actionClicked)
    }
}
