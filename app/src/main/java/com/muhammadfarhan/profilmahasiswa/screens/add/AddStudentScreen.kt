package com.muhammadfarhan.profilmahasiswa.screens.add

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
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
    val requesters = List(6) { remember { FocusRequester() } }
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
        Box(
            modifier = Modifier.fillMaxSize().padding(innerPadding).imePadding(),
            contentAlignment = Alignment.TopCenter
        ) {
            Column(
                modifier = Modifier.fillMaxWidth().widthIn(max = 600.dp)
                    .verticalScroll(rememberScrollState()).padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(text = stringResource(R.string.add_student_intro))
                AddStudentField(
                    value = form.name,
                    onValueChange = { onFormChange(form.copy(name = it)) },
                    label = R.string.label_name,
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
                    error = errorText(uiState.errors.phone, AddField.Phone),
                    keyboardType = KeyboardType.Phone,
                    imeAction = ImeAction.Done,
                    requester = requesters[5],
                    onNext = { focusManager.clearFocus() },
                    tag = AddStudentTestTags.Phone
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(modifier = Modifier.fillMaxWidth()) {
                    OutlinedButton(
                        onClick = { focusManager.clearFocus(); onCancel() },
                        modifier = Modifier.weight(1f).heightIn(min = 48.dp)
                            .testTag(AddStudentTestTags.Cancel)
                    ) { Text(stringResource(R.string.action_cancel)) }
                    Spacer(Modifier.width(12.dp))
                    Button(
                        onClick = { focusManager.clearFocus(); onSave() },
                        enabled = uiState.canSave,
                        modifier = Modifier.weight(1f).heightIn(min = 48.dp)
                            .testTag(AddStudentTestTags.Save)
                    ) { Text(stringResource(R.string.action_save)) }
                }
            }
        }
    }
}

@Composable
private fun AddStudentField(
    value: String,
    onValueChange: (String) -> Unit,
    @StringRes label: Int,
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
        supportingText = error?.let { message -> { Text(message) } },
        isError = error != null,
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = imeAction),
        keyboardActions = if (imeAction == ImeAction.Done) {
            KeyboardActions(onDone = { onNext() })
        } else KeyboardActions(onNext = { onNext() }),
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
