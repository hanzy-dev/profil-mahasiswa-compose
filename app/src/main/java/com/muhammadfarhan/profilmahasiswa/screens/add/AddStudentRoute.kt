package com.muhammadfarhan.profilmahasiswa.screens.add

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.muhammadfarhan.profilmahasiswa.model.StudentProfile

@Composable
fun AddStudentRoute(
    existingStudentIds: Set<String>,
    onStudentCreated: (StudentProfile) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    var form by rememberSaveable(stateSaver = AddStudentFormSaver) {
        mutableStateOf(AddStudentForm())
    }
    var errors by rememberSaveable(stateSaver = AddStudentErrorsSaver) {
        mutableStateOf(AddStudentFieldErrors())
    }
    val currentErrors = validateAddStudentForm(form, existingStudentIds)

    AddStudentScreen(
        uiState = AddStudentUiState(
            form = form,
            errors = errors,
            canSave = !currentErrors.hasErrors
        ),
        onFormChange = {
            form = it
            errors = validateAddStudentForm(it, existingStudentIds)
        },
        onSave = {
            val normalized = normalizeAddStudentForm(form)
            val submissionErrors = validateAddStudentForm(normalized, existingStudentIds)
            form = normalized
            errors = submissionErrors
            if (!submissionErrors.hasErrors) onStudentCreated(normalized.toStudentProfile())
        },
        onCancel = onBack,
        onBack = onBack,
        modifier = modifier
    )
}
