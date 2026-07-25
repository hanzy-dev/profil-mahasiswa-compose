package com.muhammadfarhan.profilmahasiswa.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.material3.SnackbarHostState
import androidx.compose.ui.res.stringResource
import com.muhammadfarhan.profilmahasiswa.R
import com.muhammadfarhan.profilmahasiswa.screens.add.AddStudentRoute
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.muhammadfarhan.profilmahasiswa.model.StudentProfile
import com.muhammadfarhan.profilmahasiswa.screens.common.StudentNotFoundScreen
import com.muhammadfarhan.profilmahasiswa.screens.home.StudentListScreen
import com.muhammadfarhan.profilmahasiswa.screens.profile.StudentProfileRoute
import kotlinx.coroutines.launch
import kotlinx.coroutines.CoroutineStart

@Composable
fun AppNavigation(
    students: List<StudentProfile>,
    onProfileSaved: (StudentProfile) -> Unit,
    onStudentCreated: (StudentProfile) -> Unit,
    modifier: Modifier = Modifier,
    startDestination: String = AppRoutes.STUDENTS,
    navController: NavHostController = rememberNavController()
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val navigationScope = rememberCoroutineScope()
    val studentAddedMessage = stringResource(R.string.student_added_success)
    val profileUpdatedMessage = stringResource(R.string.profile_update_success)
    val returnToStudents = {
        if (!navController.popBackStack()) {
            navController.navigate(AppRoutes.STUDENTS) {
                popUpTo(navController.graph.id) { inclusive = true }
            }
        }
        Unit
    }

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(AppRoutes.STUDENTS) {
            StudentListScreen(
                students = students,
                snackbarHostState = snackbarHostState,
                onAddStudent = { navController.navigate(AppRoutes.ADD_STUDENT) },
                onStudentClick = { studentId ->
                    navController.navigate(AppRoutes.studentDetail(studentId))
                }
            )
        }
        composable(AppRoutes.ADD_STUDENT) {
            AddStudentRoute(
                existingStudentIds = students.mapTo(mutableSetOf(), StudentProfile::studentId),
                onStudentCreated = { profile ->
                    onStudentCreated(profile)
                    navController.popBackStack()
                    navigationScope.launch {
                        snackbarHostState.showSnackbar(studentAddedMessage)
                    }
                },
                onBack = returnToStudents
            )
        }
        composable(
            route = AppRoutes.STUDENT_DETAIL,
            arguments = listOf(
                navArgument(AppRoutes.STUDENT_ID) { type = NavType.StringType }
            )
        ) { entry ->
            val studentId = entry.arguments?.getString(AppRoutes.STUDENT_ID)
            val student = students.find { it.studentId == studentId }
            if (student == null) {
                StudentNotFoundScreen(onBack = returnToStudents)
            } else {
                StudentProfileRoute(
                    profile = student,
                    onProfileSaved = onProfileSaved,
                    onBack = returnToStudents,
                    snackbarHostState = snackbarHostState,
                    onProfileSaveSuccess = {
                        navigationScope.launch(start = CoroutineStart.UNDISPATCHED) {
                            snackbarHostState.showSnackbar(profileUpdatedMessage)
                        }
                    }
                )
            }
        }
    }
}
