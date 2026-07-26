package com.muhammadfarhan.profilmahasiswa.screens.profile

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.muhammadfarhan.profilmahasiswa.R
import com.muhammadfarhan.profilmahasiswa.ui.components.AppTopBar
import com.muhammadfarhan.profilmahasiswa.ui.components.ThemeToggleButton

@Composable
fun StudentProfileScreen(
    uiState: StudentProfileUiState,
    isDarkTheme: Boolean,
    onToggleTheme: () -> Unit,
    snackbarHostState: SnackbarHostState,
    onEditClick: () -> Unit,
    onSaveClick: () -> Unit,
    onCancelClick: () -> Unit,
    onNameChange: (String) -> Unit,
    onStudyProgramChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPhoneChange: (String) -> Unit,
    onPickPhoto: () -> Unit,
    onViewGradesClick: () -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val focusManager = LocalFocusManager.current
    val nameFocusRequester = remember { FocusRequester() }
    val studyProgramFocusRequester = remember { FocusRequester() }
    val emailFocusRequester = remember { FocusRequester() }
    val phoneFocusRequester = remember { FocusRequester() }

    Scaffold(
        modifier = modifier.testTag(ProfileTestTags.Screen),
        snackbarHost = { SnackbarHost(hostState = snackbarHostState, modifier = Modifier.testTag(ProfileTestTags.Snackbar)) },
        topBar = {
            AppTopBar(
                title = stringResource(
                    if (uiState.isEditing) R.string.title_edit_profile
                    else R.string.title_profile
                ),
                onBack = onBack,
                backButtonModifier = Modifier.testTag(ProfileTestTags.Back),
                actions = { ThemeToggleButton(isDarkTheme, onToggleTheme) }
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
                
                ProfileHeader(
                    profile = uiState.displayedProfile,
                    isEditing = uiState.isEditing,
                    onPickPhoto = onPickPhoto
                )
                
                Spacer(modifier = Modifier.height(24.dp))
                
                AnimatedContent(
                    targetState = uiState.isEditing,
                    transitionSpec = {
                        fadeIn(animationSpec = tween(250)) togetherWith fadeOut(animationSpec = tween(250))
                    },
                    label = "ProfileContentTransition"
                ) { isEditing ->
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        if (isEditing) {
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
                            isEditing = isEditing,
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
                            isEditing = isEditing,
                            saveEnabled = uiState.canSave,
                            onEditClick = onEditClick,
                            onSaveClick = {
                                focusManager.clearFocus()
                                onSaveClick()
                            },
                            onCancelClick = {
                                focusManager.clearFocus()
                                onCancelClick()
                            },
                            onViewGradesClick = onViewGradesClick
                        )
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}
