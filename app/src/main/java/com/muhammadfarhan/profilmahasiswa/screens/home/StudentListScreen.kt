package com.muhammadfarhan.profilmahasiswa.screens.home

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.muhammadfarhan.profilmahasiswa.R
import com.muhammadfarhan.profilmahasiswa.model.DefaultStudentProfile
import com.muhammadfarhan.profilmahasiswa.model.StudentProfile
import com.muhammadfarhan.profilmahasiswa.ui.components.AppTopBar
import com.muhammadfarhan.profilmahasiswa.ui.components.EmptyState
import com.muhammadfarhan.profilmahasiswa.ui.components.ThemeToggleButton

@Composable
fun StudentListScreen(
    students: List<StudentProfile>,
    isDarkTheme: Boolean,
    onToggleTheme: () -> Unit,
    snackbarHostState: SnackbarHostState,
    onStudentClick: (String) -> Unit,
    onAddStudent: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier.testTag(StudentListTestTags.Screen),
        topBar = {
            AppTopBar(
                title = stringResource(R.string.title_student_list),
                actions = { ThemeToggleButton(isDarkTheme, onToggleTheme) }
            )
        },
        snackbarHost = { 
            SnackbarHost(
                hostState = snackbarHostState, 
                modifier = Modifier.testTag("student_list_snackbar")
            ) 
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddStudent,
                modifier = Modifier.testTag(StudentListTestTags.AddStudentFab),
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = stringResource(R.string.add_student_content_description)
                )
            }
        }
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize().padding(padding)) {
            AnimatedContent(
                targetState = students.isEmpty(),
                transitionSpec = {
                    fadeIn(animationSpec = tween(250)) togetherWith fadeOut(animationSpec = tween(250))
                },
                label = "StudentListEmptyTransition"
            ) { isEmpty ->
                if (isEmpty) {
                    EmptyState(
                        icon = Icons.Default.People,
                        title = stringResource(R.string.student_list_empty_title),
                        message = stringResource(R.string.student_list_empty_message),
                        action = {
                            Button(
                                onClick = onAddStudent,
                                modifier = Modifier.testTag(StudentListTestTags.EmptyStateAction)
                            ) {
                                Icon(Icons.Default.Add, contentDescription = null)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(stringResource(R.string.add_student_action))
                            }
                        }
                    )
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 88.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        item {
                            Text(
                                text = stringResource(R.string.student_count_format, students.size),
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.padding(bottom = 4.dp)
                            )
                        }
                        items(students, key = { it.studentId }) { student ->
                            StudentCard(
                                student = student,
                                onClick = { onStudentClick(student.studentId) },
                                isPrimary = student.studentId == DefaultStudentProfile.studentId,
                                modifier = Modifier
                                    .animateItem()
                                    .testTag(
                                        if (student.studentId == DefaultStudentProfile.studentId) {
                                            StudentListTestTags.PrimaryStudentCard
                                        } else {
                                            StudentListTestTags.studentCard(student.studentId)
                                        }
                                    )
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun StudentCard(
    student: StudentProfile,
    isPrimary: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val openDescription = stringResource(R.string.open_student_profile, student.name)
    Card(
        modifier = modifier
            .fillMaxWidth()
            .semantics { contentDescription = openDescription }
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                modifier = Modifier.size(56.dp),
                shape = CircleShape,
                color = MaterialTheme.colorScheme.primaryContainer
            ) {
                if (student.profileImageUri != null) {
                    AsyncImage(
                        model = student.profileImageUri,
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize().clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                        modifier = Modifier.padding(12.dp),
                        tint = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = student.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = stringResource(R.string.student_id_format, student.studentId),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = stringResource(
                        R.string.academic_info_format,
                        student.studyProgram,
                        student.semester
                    ),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                )
                Surface(
                    color = if (isPrimary) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondaryContainer,
                    shape = RoundedCornerShape(4.dp),
                    modifier = Modifier.padding(top = 4.dp)
                ) {
                    Text(
                        text = stringResource(
                            if (isPrimary) R.string.primary_profile_label
                            else R.string.student_label
                        ),
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                        style = MaterialTheme.typography.labelSmall,
                        color = if (isPrimary) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }
            }
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
            )
        }
    }
}
