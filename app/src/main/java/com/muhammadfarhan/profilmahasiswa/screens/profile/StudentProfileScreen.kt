package com.muhammadfarhan.profilmahasiswa.screens.profile

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.muhammadfarhan.profilmahasiswa.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentProfileScreen(
    uiState: StudentProfileUiState,
    snackbarHostState: SnackbarHostState,
    onEditClick: () -> Unit,
    onSaveClick: () -> Unit,
    onCancelClick: () -> Unit,
    onNameChange: (String) -> Unit,
    onStudyProgramChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPhoneChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val focusManager = LocalFocusManager.current
    val nameFocusRequester = remember { FocusRequester() }
    val studyProgramFocusRequester = remember { FocusRequester() }
    val emailFocusRequester = remember { FocusRequester() }
    val phoneFocusRequester = remember { FocusRequester() }

    Scaffold(
        modifier = modifier,
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
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
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .imePadding(),
            contentAlignment = Alignment.TopCenter
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .widthIn(max = 600.dp)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(16.dp))
                ProfileHeader(profile = uiState.displayedProfile)
                Spacer(modifier = Modifier.height(24.dp))
                if (uiState.isEditing) {
                    ProfileDetailsCard(
                        profile = uiState.draftProfile,
                        fieldErrors = uiState.fieldErrors,
                        nameFocusRequester = nameFocusRequester,
                        studyProgramFocusRequester = studyProgramFocusRequester,
                        onNameNext = { studyProgramFocusRequester.requestFocus() },
                        onStudyProgramNext = { emailFocusRequester.requestFocus() },
                        onNameChange = onNameChange,
                        onStudyProgramChange = onStudyProgramChange
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
                ContactInformationCard(
                    profile = uiState.displayedProfile,
                    isEditing = uiState.isEditing,
                    fieldErrors = uiState.fieldErrors,
                    emailFocusRequester = emailFocusRequester,
                    phoneFocusRequester = phoneFocusRequester,
                    onEmailNext = { phoneFocusRequester.requestFocus() },
                    onPhoneDone = { focusManager.clearFocus() },
                    onEmailChange = onEmailChange,
                    onPhoneChange = onPhoneChange
                )
                Spacer(modifier = Modifier.height(24.dp))
                ProfileActions(
                    isEditing = uiState.isEditing,
                    saveEnabled = uiState.canSave,
                    onEditClick = onEditClick,
                    onSaveClick = {
                        focusManager.clearFocus()
                        onSaveClick()
                    },
                    onCancelClick = {
                        focusManager.clearFocus()
                        onCancelClick()
                    }
                )
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}
