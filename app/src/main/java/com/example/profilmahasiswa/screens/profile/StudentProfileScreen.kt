package com.example.profilmahasiswa.screens.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.profilmahasiswa.R
import com.example.profilmahasiswa.model.DefaultStudentProfile
import com.example.profilmahasiswa.ui.theme.ProfilMahasiswaTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentProfileScreen(
    uiState: StudentProfileUiState,
    onEditClick: () -> Unit,
    onDoneClick: () -> Unit,
    onEmailChange: (String) -> Unit,
    onPhoneChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(
                            if (uiState.isEditing) {
                                R.string.title_edit_profile
                            } else {
                                R.string.title_profile
                            }
                        ),
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            ProfileHeader(profile = uiState.profile)
            Spacer(modifier = Modifier.height(24.dp))
            ContactInformationCard(
                profile = uiState.profile,
                isEditing = uiState.isEditing,
                onEmailChange = onEmailChange,
                onPhoneChange = onPhoneChange
            )
            Spacer(modifier = Modifier.height(24.dp))
            ProfileActionButton(
                isEditing = uiState.isEditing,
                onClick = if (uiState.isEditing) onDoneClick else onEditClick
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
    name = "Profil Mahasiswa"
)
@Composable
private fun StudentProfileViewPreview() {
    ProfilMahasiswaTheme {
        StudentProfileScreen(
            uiState = StudentProfileUiState(profile = DefaultStudentProfile),
            onEditClick = {},
            onDoneClick = {},
            onEmailChange = {},
            onPhoneChange = {}
        )
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
    name = "Edit Profil"
)
@Composable
private fun StudentProfileEditPreview() {
    ProfilMahasiswaTheme {
        StudentProfileScreen(
            uiState = StudentProfileUiState(
                profile = DefaultStudentProfile,
                isEditing = true
            ),
            onEditClick = {},
            onDoneClick = {},
            onEmailChange = {},
            onPhoneChange = {}
        )
    }
}
