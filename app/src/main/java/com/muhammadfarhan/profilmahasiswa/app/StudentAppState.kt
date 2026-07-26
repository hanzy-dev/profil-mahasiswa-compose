package com.muhammadfarhan.profilmahasiswa.app

import androidx.compose.runtime.saveable.Saver
import com.muhammadfarhan.profilmahasiswa.model.CourseGrade
import com.muhammadfarhan.profilmahasiswa.model.DefaultStudentProfile
import com.muhammadfarhan.profilmahasiswa.model.StudentProfile

data class StudentAppState(
    val students: List<StudentProfile>,
    val themeMode: ThemeMode = ThemeMode.SYSTEM,
    val gradesByStudentId: Map<String, List<CourseGrade>> = emptyMap()
)

val FarhanGrades = listOf(
    CourseGrade("IF101", "Pemrograman Mobile", 85, "A"),
    CourseGrade("IF102", "Basis Data", 80, "A-"),
    CourseGrade("IF103", "Rekayasa Perangkat Lunak", 78, "B+"),
    CourseGrade("IF104", "Jaringan Komputer", 82, "A"),
    CourseGrade("IF105", "Analisis dan Perancangan Sistem", 75, "B+"),
    CourseGrade("IF106", "Interaksi Manusia dan Komputer", 88, "A")
)

val DefaultStudentAppState = StudentAppState(
    students = listOf(DefaultStudentProfile),
    gradesByStudentId = mapOf(DefaultStudentProfile.studentId to FarhanGrades)
)

fun StudentAppState.findStudent(studentId: String): StudentProfile? =
    students.find { it.studentId.trim() == studentId.trim() }

fun StudentAppState.containsStudent(studentId: String): Boolean {
    val normalizedId = studentId.trim()
    return normalizedId.isNotEmpty() &&
        students.any { it.studentId.trim() == normalizedId }
}

fun StudentAppState.addStudent(profile: StudentProfile): StudentAppState {
    val normalizedId = profile.studentId.trim()
    if (normalizedId.isEmpty() || containsStudent(normalizedId)) return this
    if (profile.name.isBlank() || profile.studyProgram.isBlank() ||
        profile.semester !in 1..14 || profile.email.isBlank() || profile.phone.isBlank()
    ) return this
    return copy(students = students + profile.copy(studentId = normalizedId))
}

fun StudentAppState.updateStudent(profile: StudentProfile): StudentAppState {
    if (students.none { it.studentId == profile.studentId }) return this
    return copy(students = students.map { current ->
        if (current.studentId == profile.studentId) profile else current
    })
}

fun StudentAppState.updateThemeMode(themeMode: ThemeMode): StudentAppState =
    copy(themeMode = themeMode)

fun StudentAppState.getGradesForStudent(studentId: String): List<CourseGrade> =
    gradesByStudentId[studentId] ?: emptyList()

fun List<CourseGrade>.averageScore(): Double =
    if (isEmpty()) 0.0 else map { it.numericScore }.average()

fun List<CourseGrade>.highestScore(): Int =
    if (isEmpty()) 0 else maxOf { it.numericScore }

private const val FieldsPerStudent = 7

fun saveStudentAppState(state: StudentAppState): ArrayList<String> = arrayListOf<String>().apply {
    add(state.themeMode.name)
    add(state.students.size.toString())
    state.students.forEach { student ->
        add(student.name)
        add(student.studentId)
        add(student.studyProgram)
        add(student.semester.toString())
        add(student.email)
        add(student.phone)
        add(student.profileImageUri ?: "")
    }
    add(state.gradesByStudentId.size.toString())
    state.gradesByStudentId.forEach { (studentId, grades) ->
        add(studentId)
        add(grades.size.toString())
        grades.forEach { grade ->
            add(grade.courseCode)
            add(grade.courseName)
            add(grade.numericScore.toString())
            add(grade.letterGrade)
        }
    }
}

fun restoreStudentAppState(values: List<String>): StudentAppState {
    return runCatching {
        var cursor = 0
        val themeMode = ThemeMode.valueOf(values[cursor++])
        val studentCount = values[cursor++].toInt()
        val students = List(studentCount) {
            val name = values[cursor++]
            val studentId = values[cursor++]
            val studyProgram = values[cursor++]
            val semester = values[cursor++].toInt()
            val email = values[cursor++]
            val phone = values[cursor++]
            val imageUri = values[cursor++].ifEmpty { null }
            StudentProfile(name, studentId, studyProgram, semester, email, phone, imageUri)
        }
        
        val gradesMapCount = values[cursor++].toInt()
        val gradesByStudentId = mutableMapOf<String, List<CourseGrade>>()
        repeat(gradesMapCount) {
            val studentId = values[cursor++]
            val gradeCount = values[cursor++].toInt()
            val grades = List(gradeCount) {
                val code = values[cursor++]
                val name = values[cursor++]
                val score = values[cursor++].toInt()
                val letter = values[cursor++]
                CourseGrade(code, name, score, letter)
            }
            gradesByStudentId[studentId] = grades
        }
        
        StudentAppState(students = students, themeMode = themeMode, gradesByStudentId = gradesByStudentId)
    }.getOrElse { DefaultStudentAppState }
}

val StudentAppStateSaver = Saver<StudentAppState, ArrayList<String>>(
    save = { state -> saveStudentAppState(state) },
    restore = { values -> restoreStudentAppState(values) }
)
