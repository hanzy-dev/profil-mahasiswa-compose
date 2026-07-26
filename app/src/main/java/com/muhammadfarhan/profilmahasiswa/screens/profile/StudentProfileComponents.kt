package com.muhammadfarhan.profilmahasiswa.screens.profile

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.muhammadfarhan.profilmahasiswa.R
import com.muhammadfarhan.profilmahasiswa.model.StudentProfile
import com.muhammadfarhan.profilmahasiswa.ui.components.AppSectionTitle
import com.muhammadfarhan.profilmahasiswa.ui.components.InfoRow

@Composable
fun ProfileHeader(
    profile: StudentProfile,
    isEditing: Boolean = false,
    onPickPhoto: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ProfileAvatar(
            imageUri = profile.profileImageUri,
            isEditing = isEditing,
            onPickPhoto = onPickPhoto
        )
        Spacer(modifier = Modifier.height(12.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .background(
                    color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.1f),
                    shape = CircleShape
                )
                .padding(horizontal = 12.dp, vertical = 4.dp)
        ) {
            Icon(
                imageVector = Icons.Default.CheckCircle,
                contentDescription = null,
                modifier = Modifier.size(16.dp),
                tint = MaterialTheme.colorScheme.secondary
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = stringResource(R.string.active_student_status),
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.secondary,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = profile.name,
            modifier = Modifier.testTag(ProfileTestTags.DisplayedName),
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )
        Text(
            text = stringResource(R.string.student_id_format, profile.studentId),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(16.dp))
        AcademicInfo(profile = profile)
    }
}

@Composable
fun ProfileAvatar(
    imageUri: String?,
    isEditing: Boolean = false,
    onPickPhoto: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Surface(
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .border(4.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.2f), CircleShape),
            shape = CircleShape,
            color = MaterialTheme.colorScheme.primaryContainer,
            tonalElevation = 2.dp
        ) {
            AnimatedContent(
                targetState = imageUri,
                transitionSpec = {
                    fadeIn(animationSpec = tween(250)) togetherWith fadeOut(animationSpec = tween(250))
                },
                label = "AvatarImageTransition"
            ) { targetUri ->
                if (targetUri != null) {
                    AsyncImage(
                        model = targetUri,
                        contentDescription = stringResource(R.string.content_description_student_photo),
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = stringResource(
                            R.string.content_description_profile_photo
                        ),
                        modifier = Modifier.size(60.dp),
                        tint = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }
        }
        
        AnimatedVisibility(
            visible = isEditing,
            enter = scaleIn() + fadeIn(),
            exit = scaleOut() + fadeOut(),
            modifier = Modifier.align(Alignment.BottomEnd)
        ) {
            val description = stringResource(
                if (imageUri == null) R.string.action_pick_profile_photo 
                else R.string.action_change_profile_photo
            )
            FilledIconButton(
                onClick = onPickPhoto,
                modifier = Modifier
                    .size(48.dp) // Professional touch target size
                    .border(2.dp, MaterialTheme.colorScheme.surface, CircleShape)
                    .semantics { contentDescription = description }
                    .testTag(ProfileTestTags.PhotoAction),
                colors = IconButtonDefaults.filledIconButtonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Icon(
                    imageVector = Icons.Default.CameraAlt,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}

@Composable
fun AcademicInfo(
    profile: StudentProfile,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        color = MaterialTheme.colorScheme.secondaryContainer,
        shape = RoundedCornerShape(16.dp),
        tonalElevation = 1.dp
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.School,
                contentDescription = stringResource(
                    R.string.content_description_study_program
                ),
                modifier = Modifier.size(20.dp),
                tint = MaterialTheme.colorScheme.onSecondaryContainer
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = stringResource(
                    R.string.academic_info_format,
                    profile.studyProgram,
                    profile.semester
                ),
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
fun ContactInformationCard(
    profile: StudentProfile,
    isEditing: Boolean,
    fieldErrors: ProfileFieldErrors,
    emailFocusRequester: FocusRequester,
    phoneFocusRequester: FocusRequester,
    onEmailNext: () -> Unit,
    onPhoneDone: () -> Unit,
    onEmailChange: (String) -> Unit,
    onPhoneChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
        )
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            AppSectionTitle(title = stringResource(R.string.contact_information))
            Spacer(modifier = Modifier.height(8.dp))
            
            if (isEditing) {
                ContactField(
                    testTag = ProfileTestTags.Email,
                    icon = Icons.Default.Email,
                    label = stringResource(R.string.label_email),
                    value = profile.email,
                    enabled = true,
                    error = fieldErrors.email,
                    requiredError = R.string.error_email_required,
                    tooLongError = null,
                    invalidFormatError = R.string.error_email_invalid,
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next,
                    focusRequester = emailFocusRequester,
                    onImeAction = onEmailNext,
                    onValueChange = onEmailChange
                )
                Spacer(modifier = Modifier.height(12.dp))
                ContactField(
                    testTag = ProfileTestTags.Phone,
                    icon = Icons.Default.Phone,
                    label = stringResource(R.string.label_phone),
                    value = profile.phone,
                    enabled = true,
                    error = fieldErrors.phone,
                    requiredError = R.string.error_phone_required,
                    tooLongError = null,
                    invalidFormatError = R.string.error_phone_invalid,
                    keyboardType = KeyboardType.Phone,
                    imeAction = ImeAction.Done,
                    focusRequester = phoneFocusRequester,
                    onImeAction = onPhoneDone,
                    onValueChange = onPhoneChange
                )
            } else {
                InfoRow(
                    label = stringResource(R.string.label_email),
                    value = profile.email,
                    icon = Icons.Default.Email,
                    modifier = Modifier.testTag(ProfileTestTags.Email)
                )
                Spacer(modifier = Modifier.height(8.dp))
                InfoRow(
                    label = stringResource(R.string.label_phone),
                    value = profile.phone,
                    icon = Icons.Default.Phone,
                    modifier = Modifier.testTag(ProfileTestTags.Phone)
                )
            }
        }
    }
}

@Composable
fun ProfileDetailsCard(
    profile: StudentProfile,
    fieldErrors: ProfileFieldErrors,
    nameFocusRequester: FocusRequester,
    studyProgramFocusRequester: FocusRequester,
    onNameNext: () -> Unit,
    onStudyProgramNext: () -> Unit,
    onNameChange: (String) -> Unit,
    onStudyProgramChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
        )
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            AppSectionTitle(title = stringResource(R.string.profile_information))
            Spacer(modifier = Modifier.height(8.dp))
            
            ContactField(
                testTag = ProfileTestTags.Name,
                icon = Icons.Default.Person,
                label = stringResource(R.string.label_name),
                value = profile.name,
                enabled = true,
                error = fieldErrors.name,
                requiredError = R.string.error_name_required,
                tooLongError = R.string.error_name_too_long,
                invalidFormatError = null,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next,
                focusRequester = nameFocusRequester,
                onImeAction = onNameNext,
                onValueChange = onNameChange
            )
            Spacer(modifier = Modifier.height(12.dp))
            ContactField(
                testTag = ProfileTestTags.StudyProgram,
                icon = Icons.Default.School,
                label = stringResource(R.string.label_study_program),
                value = profile.studyProgram,
                enabled = true,
                error = fieldErrors.studyProgram,
                requiredError = R.string.error_study_program_required,
                tooLongError = R.string.error_study_program_too_long,
                invalidFormatError = null,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next,
                focusRequester = studyProgramFocusRequester,
                onImeAction = onStudyProgramNext,
                onValueChange = onStudyProgramChange
            )
        }
    }
}

@Composable
fun ContactField(
    testTag: String,
    icon: ImageVector,
    label: String,
    value: String,
    enabled: Boolean,
    error: ProfileFieldError? = null,
    requiredError: Int,
    tooLongError: Int?,
    invalidFormatError: Int?,
    keyboardType: KeyboardType,
    imeAction: ImeAction,
    focusRequester: FocusRequester,
    onImeAction: () -> Unit,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val errorMessage = when (error) {
        ProfileFieldError.Required -> stringResource(requiredError)
        ProfileFieldError.TooLong -> tooLongError?.let { stringResource(it) }
        ProfileFieldError.InvalidFormat -> invalidFormatError?.let { stringResource(it) }
        null -> null
    }

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        enabled = enabled,
        isError = errorMessage != null,
        label = { Text(label) },
        leadingIcon = {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = if (errorMessage != null) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary
            )
        },
        supportingText = errorMessage?.let { message ->
            { Text(text = message) }
        },
        shape = RoundedCornerShape(12.dp),
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = imeAction
        ),
        keyboardActions = KeyboardActions(
            onNext = { onImeAction() },
            onDone = { onImeAction() }
        ),
        singleLine = true,
        modifier = modifier
            .fillMaxWidth()
            .testTag(testTag)
            .focusRequester(focusRequester)
    )
}

@Composable
fun ProfileActions(
    isEditing: Boolean,
    saveEnabled: Boolean,
    onEditClick: () -> Unit,
    onSaveClick: () -> Unit,
    onCancelClick: () -> Unit,
    onViewGradesClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        if (isEditing) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedButton(
                    onClick = onCancelClick,
                    modifier = Modifier
                        .weight(1f)
                        .heightIn(min = 52.dp)
                        .testTag(ProfileTestTags.Cancel),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(text = stringResource(R.string.action_cancel))
                }
                
                Button(
                    onClick = onSaveClick,
                    enabled = saveEnabled,
                    modifier = Modifier
                        .weight(1f)
                        .heightIn(min = 52.dp)
                        .testTag(ProfileTestTags.Save),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = stringResource(R.string.action_save),
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            }
        } else {
            Button(
                onClick = onEditClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 52.dp)
                    .testTag(ProfileTestTags.Edit),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = stringResource(R.string.action_edit_profile),
                    style = MaterialTheme.typography.labelLarge
                )
            }
            
            OutlinedButton(
                onClick = onViewGradesClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 52.dp)
                    .testTag(ProfileTestTags.ViewGrades),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Assessment,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = stringResource(R.string.action_view_grades),
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }
    }
}
