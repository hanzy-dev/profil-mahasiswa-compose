package com.muhammadfarhan.profilmahasiswa.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.material3.SnackbarHostState
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

@Composable
fun AppNavigation(
    students: List<StudentProfile>,
    onProfileSaved: (StudentProfile) -> Unit,
    modifier: Modifier = Modifier,
    startDestination: String = AppRoutes.STUDENTS,
    navController: NavHostController = rememberNavController()
) {
    val profileSnackbarHostState = remember { SnackbarHostState() }
    val navigationScope = rememberCoroutineScope()
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
                onStudentClick = { studentId ->
                    navController.navigate(AppRoutes.studentDetail(studentId))
                }
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
                    snackbarHostState = profileSnackbarHostState,
                    snackbarCoroutineScope = navigationScope
                )
            }
        }
    }
}
