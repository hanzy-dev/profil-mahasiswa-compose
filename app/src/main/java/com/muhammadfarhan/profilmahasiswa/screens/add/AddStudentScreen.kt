package com.muhammadfarhan.profilmahasiswa.screens.add

import androidx.annotation.StringRes
import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.muhammadfarhan.profilmahasiswa.R
import com.muhammadfarhan.profilmahasiswa.ui.components.AppTopBar
import com.muhammadfarhan.profilmahasiswa.ui.components.ThemeToggleButton

@Composable
fun AddStudentScreen(
    uiState: AddStudentUiState,
    isDarkTheme: Boolean,
    onToggleTheme: () -> Unit,
    onFormChange: (AddStudentForm) -> Unit,
    onSave: () -> Unit,
    onCancel: () -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val focusManager = LocalFocusManager.current
    val requesters = remember { List(6) { FocusRequester() } }
    val form = uiState.form

    Scaffold(
        modifier = modifier.testTag(AddStudentTestTags.Screen),
        topBar = {
            AppTopBar(
                title = stringResource(R.string.title_add_student),
                onBack = onBack,
                actions = { ThemeToggleButton(isDarkTheme, onToggleTheme) }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .imePadding()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Card(
                modifier = Modifier.fillMaxWidth().widthIn(max = 600.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                )
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = stringResource(R.string.add_student_intro),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    
                    AddStudentField(
                        value = form.name,
                        onValueChange = { onFormChange(form.copy(name = it)) },
                        label = R.string.label_name,
                        icon = Icons.Default.Person,
                        error = errorText(uiState.errors.name, AddField.Name),
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next,
                        requester = requesters[0],
                        onNext = { requesters[1].requestFocus() },
                        tag = AddStudentTestTags.Name
                    )
                    AddStudentField(
                        value = form.studentId,
                        onValueChange = { onFormChange(form.copy(studentId = it)) },
                        label = R.string.label_student_id,
                        icon = Icons.Default.Badge,
                        error = errorText(uiState.errors.studentId, AddField.StudentId),
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next,
                        requester = requesters[1],
                        onNext = { requesters[2].requestFocus() },
                        tag = AddStudentTestTags.StudentId
                    )
                    AddStudentField(
                        value = form.studyProgram,
                        onValueChange = { onFormChange(form.copy(studyProgram = it)) },
                        label = R.string.label_study_program,
                        icon = Icons.Default.School,
                        error = errorText(uiState.errors.studyProgram, AddField.StudyProgram),
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next,
                        requester = requesters[2],
                        onNext = { requesters[3].requestFocus() },
                        tag = AddStudentTestTags.StudyProgram
                    )
                    AddStudentField(
                        value = form.semester,
                        onValueChange = { onFormChange(form.copy(semester = it)) },
                        label = R.string.label_semester,
                        icon = Icons.Default.CalendarToday,
                        error = errorText(uiState.errors.semester, AddField.Semester),
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next,
                        requester = requesters[3],
                        onNext = { requesters[4].requestFocus() },
                        tag = AddStudentTestTags.Semester
                    )
                    AddStudentField(
                        value = form.email,
                        onValueChange = { onFormChange(form.copy(email = it)) },
                        label = R.string.label_email,
                        icon = Icons.Default.Email,
                        error = errorText(uiState.errors.email, AddField.Email),
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next,
                        requester = requesters[4],
                        onNext = { requesters[5].requestFocus() },
                        tag = AddStudentTestTags.Email
                    )
                    AddStudentField(
                        value = form.phone,
                        onValueChange = { onFormChange(form.copy(phone = it)) },
                        label = R.string.label_phone,
                        icon = Icons.Default.Phone,
                        error = errorText(uiState.errors.phone, AddField.Phone),
                        keyboardType = KeyboardType.Phone,
                        imeAction = ImeAction.Done,
                        requester = requesters[5],
                        onNext = { focusManager.clearFocus() },
                        tag = AddStudentTestTags.Phone
                    )
                }
            }
            
            Row(
                modifier = Modifier.fillMaxWidth().widthIn(max = 600.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedButton(
                    onClick = { focusManager.clearFocus(); onCancel() },
                    modifier = Modifier.weight(1f).heightIn(min = 48.dp)
                        .testTag(AddStudentTestTags.Cancel),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(stringResource(R.string.action_cancel))
                }
                
                Button(
                    onClick = { focusManager.clearFocus(); onSave() },
                    enabled = uiState.canSave,
                    modifier = Modifier.weight(1f).heightIn(min = 48.dp)
                        .testTag(AddStudentTestTags.Save),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    AnimatedContent(
                        targetState = uiState.canSave,
                        label = "SaveButtonIcon"
                    ) { canSave ->
                        if (canSave) {
                            Icon(Icons.Default.Check, contentDescription = null)
                        } else {
                            Icon(Icons.Default.Save, contentDescription = null)
                        }
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(stringResource(R.string.action_save))
                }
            }
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
private fun AddStudentField(
    value: String,
    onValueChange: (String) -> Unit,
    @StringRes label: Int,
    icon: ImageVector,
    error: String?,
    keyboardType: KeyboardType,
    imeAction: ImeAction,
    requester: FocusRequester,
    onNext: () -> Unit,
    tag: String
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(stringResource(label)) },
        leadingIcon = {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = if (error != null) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary
            )
        },
        supportingText = error?.let { message -> { Text(message) } },
        isError = error != null,
        singleLine = true,
        shape = RoundedCornerShape(12.dp),
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = imeAction),
        keyboardActions = KeyboardActions(
            onNext = { onNext() },
            onDone = { onNext() }
        ),
        modifier = Modifier.fillMaxWidth().focusRequester(requester).testTag(tag)
    )
}

private enum class AddField { Name, StudentId, StudyProgram, Semester, Email, Phone }

@Composable
private fun errorText(error: AddStudentFieldError?, field: AddField): String? {
    if (error == null) return null
    val resource = when (field to error) {
        AddField.Name to AddStudentFieldError.Required -> R.string.error_name_required
        AddField.Name to AddStudentFieldError.TooLong -> R.string.error_name_too_long
        AddField.StudentId to AddStudentFieldError.Required -> R.string.error_student_id_required
        AddField.StudentId to AddStudentFieldError.DigitsOnly -> R.string.error_student_id_digits
        AddField.StudentId to AddStudentFieldError.InvalidLength -> R.string.error_student_id_length
        AddField.StudentId to AddStudentFieldError.Duplicate -> R.string.error_student_id_duplicate
        AddField.StudyProgram to AddStudentFieldError.Required -> R.string.error_study_program_required
        AddField.StudyProgram to AddStudentFieldError.TooLong -> R.string.error_study_program_too_long
        AddField.Semester to AddStudentFieldError.Required -> R.string.error_semester_required
        AddField.Semester to AddStudentFieldError.DigitsOnly -> R.string.error_semester_number
        AddField.Semester to AddStudentFieldError.InvalidRange -> R.string.error_semester_range
        AddField.Email to AddStudentFieldError.Required -> R.string.error_email_required
        AddField.Email to AddStudentFieldError.InvalidFormat -> R.string.error_email_invalid
        AddField.Phone to AddStudentFieldError.Required -> R.string.error_phone_required
        AddField.Phone to AddStudentFieldError.InvalidFormat -> R.string.error_phone_invalid
        else -> return null
    }
    return stringResource(resource)
}
