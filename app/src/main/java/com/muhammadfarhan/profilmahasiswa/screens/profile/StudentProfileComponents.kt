package com.muhammadfarhan.profilmahasiswa.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.unit.dp
import com.muhammadfarhan.profilmahasiswa.R
import com.muhammadfarhan.profilmahasiswa.model.StudentProfile

@Composable
fun ProfileHeader(
    profile: StudentProfile,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ProfileAvatar()
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = profile.name,
            style = MaterialTheme.typography.headlineSmall
        )
        Text(
            text = stringResource(R.string.student_id_format, profile.studentId),
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(12.dp))
        AcademicInfo(profile = profile)
    }
}

@Composable
fun ProfileAvatar(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primaryContainer)
                .border(3.dp, MaterialTheme.colorScheme.primary, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = stringResource(
                    R.string.content_description_profile_photo
                ),
                modifier = Modifier.size(60.dp),
                tint = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .size(28.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.secondary)
                .border(2.dp, MaterialTheme.colorScheme.surface, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = stringResource(
                    R.string.content_description_active_student
                ),
                modifier = Modifier.size(16.dp),
                tint = MaterialTheme.colorScheme.onSecondary
            )
        }
    }
}

@Composable
fun AcademicInfo(
    profile: StudentProfile,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .background(
                color = MaterialTheme.colorScheme.secondaryContainer,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(horizontal = 14.dp, vertical = 10.dp),
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
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = stringResource(
                R.string.academic_info_format,
                profile.studyProgram,
                profile.semester
            ),
            color = MaterialTheme.colorScheme.onSecondaryContainer,
            fontWeight = FontWeight.Medium
        )
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
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(
                text = stringResource(R.string.contact_information),
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(16.dp))
            ContactField(
                icon = Icons.Default.Email,
                label = stringResource(R.string.label_email),
                value = profile.email,
                enabled = isEditing,
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
                icon = Icons.Default.Phone,
                label = stringResource(R.string.label_phone),
                value = profile.phone,
                enabled = isEditing,
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
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(
                text = stringResource(R.string.profile_information),
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(16.dp))
            ContactField(
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

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(10.dp)
            )
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primaryContainer),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = MaterialTheme.colorScheme.primary
            )
        }
        Spacer(modifier = Modifier.width(12.dp))
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            enabled = enabled,
            isError = errorMessage != null,
            label = { Text(label) },
            supportingText = errorMessage?.let { message ->
                { Text(text = message) }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType,
                imeAction = imeAction
            ),
            keyboardActions = if (imeAction == ImeAction.Done) {
                KeyboardActions(onDone = { onImeAction() })
            } else {
                KeyboardActions(onNext = { onImeAction() })
            },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester)
        )
    }
}

@Composable
fun ProfileActions(
    isEditing: Boolean,
    saveEnabled: Boolean,
    onEditClick: () -> Unit,
    onSaveClick: () -> Unit,
    onCancelClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    if (isEditing) {
        Row(modifier = modifier.fillMaxWidth()) {
            OutlinedButton(
                onClick = onCancelClick,
                modifier = Modifier
                    .weight(1f)
                    .height(50.dp)
            ) {
                Text(text = stringResource(R.string.action_cancel))
            }
            Spacer(modifier = Modifier.width(12.dp))
            Button(
                onClick = onSaveClick,
                enabled = saveEnabled,
                modifier = Modifier
                    .weight(1f)
                    .height(50.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = stringResource(R.string.action_save))
            }
        }
    } else {
        Button(
            onClick = onEditClick,
            modifier = modifier
                .fillMaxWidth()
                .height(50.dp),
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
                fontWeight = FontWeight.Medium
            )
        }
    }
}
