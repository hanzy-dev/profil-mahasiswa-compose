package com.muhammadfarhan.profilmahasiswa.screens.profile

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
import com.muhammadfarhan.profilmahasiswa.R
import com.muhammadfarhan.profilmahasiswa.model.DefaultStudentProfile
import com.muhammadfarhan.profilmahasiswa.ui.theme.ProfilMahasiswaTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentProfileScreen(
    uiState: StudentProfileUiState,
    onEditClick: () -> Unit,
    onSaveClick: () -> Unit,
    onCancelClick: () -> Unit,
    onNameChange: (String) -> Unit,
    onStudyProgramChange: (String) -> Unit,
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
            ProfileHeader(profile = uiState.displayedProfile)
            Spacer(modifier = Modifier.height(24.dp))
            if (uiState.isEditing) {
                ProfileDetailsCard(
                    profile = uiState.draftProfile,
                    onNameChange = onNameChange,
                    onStudyProgramChange = onStudyProgramChange
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
            ContactInformationCard(
                profile = uiState.displayedProfile,
                isEditing = uiState.isEditing,
                onEmailChange = onEmailChange,
                onPhoneChange = onPhoneChange
            )
            Spacer(modifier = Modifier.height(24.dp))
            ProfileActions(
                isEditing = uiState.isEditing,
                saveEnabled = uiState.hasChanges,
                onEditClick = onEditClick,
                onSaveClick = onSaveClick,
                onCancelClick = onCancelClick
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
            uiState = StudentProfileUiState(savedProfile = DefaultStudentProfile),
            onEditClick = {},
            onSaveClick = {},
            onCancelClick = {},
            onNameChange = {},
            onStudyProgramChange = {},
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
                savedProfile = DefaultStudentProfile,
                draftProfile = DefaultStudentProfile.copy(name = "Muhammad Farhan A."),
                isEditing = true
            ),
            onEditClick = {},
            onSaveClick = {},
            onCancelClick = {},
            onNameChange = {},
            onStudyProgramChange = {},
            onEmailChange = {},
            onPhoneChange = {}
        )
    }
}
