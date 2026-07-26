package com.muhammadfarhan.profilmahasiswa.screens.grades

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Assignment
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.muhammadfarhan.profilmahasiswa.R
import com.muhammadfarhan.profilmahasiswa.model.CourseGrade
import com.muhammadfarhan.profilmahasiswa.model.StudentProfile
import com.muhammadfarhan.profilmahasiswa.ui.components.AppTopBar
import com.muhammadfarhan.profilmahasiswa.ui.components.EmptyState
import com.muhammadfarhan.profilmahasiswa.ui.components.ThemeToggleButton

@Composable
fun StudentGradesScreen(
    student: StudentProfile?,
    grades: List<CourseGrade>,
    isDarkTheme: Boolean,
    onToggleTheme: () -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    if (student == null) {
        Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = stringResource(R.string.student_not_found_message))
        }
        return
    }

    Scaffold(
        modifier = modifier.testTag(StudentGradesTestTags.Screen),
        topBar = {
            AppTopBar(
                title = stringResource(R.string.title_grades),
                onBack = onBack,
                backButtonModifier = Modifier.testTag(StudentGradesTestTags.Back),
                actions = { ThemeToggleButton(isDarkTheme, onToggleTheme) }
            )
        }
    ) { innerPadding ->
        Box(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
            AnimatedContent(
                targetState = grades.isEmpty(),
                transitionSpec = {
                    fadeIn(animationSpec = tween(250)) togetherWith fadeOut(animationSpec = tween(250))
                },
                label = "GradesListEmptyTransition"
            ) { isEmpty ->
                if (isEmpty) {
                    EmptyState(
                        icon = Icons.AutoMirrored.Filled.Assignment,
                        title = stringResource(R.string.grades_empty_title),
                        message = stringResource(R.string.grades_empty_message),
                        modifier = Modifier.testTag(StudentGradesTestTags.EmptyState)
                    )
                } else {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp)
                    ) {
                        Spacer(modifier = Modifier.height(16.dp))
                        StudentSummaryCard(student = student)
                        Spacer(modifier = Modifier.height(16.dp))

                        val averageScore = grades.map { it.numericScore }.average()
                        val highestScore = grades.maxOf { it.numericScore }

                        GradesSummarySection(
                            courseCount = grades.size,
                            averageScore = averageScore,
                            highestScore = highestScore
                        )
                        Spacer(modifier = Modifier.height(16.dp))

                        LazyColumn(
                            modifier = Modifier
                                .weight(1f)
                                .testTag(StudentGradesTestTags.GradeList),
                            contentPadding = PaddingValues(bottom = 16.dp),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            items(grades, key = { it.courseCode }) { grade ->
                                GradeRow(
                                    grade = grade,
                                    modifier = Modifier.animateItem()
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
