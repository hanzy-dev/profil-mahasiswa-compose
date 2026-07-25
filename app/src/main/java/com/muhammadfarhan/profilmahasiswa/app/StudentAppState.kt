package com.muhammadfarhan.profilmahasiswa.app

import androidx.compose.runtime.saveable.Saver
import com.muhammadfarhan.profilmahasiswa.model.DefaultStudentProfile
import com.muhammadfarhan.profilmahasiswa.model.StudentProfile

data class StudentAppState(
    val students: List<StudentProfile>,
    val themeMode: ThemeMode = ThemeMode.SYSTEM
)

val DefaultStudentAppState = StudentAppState(
    students = listOf(DefaultStudentProfile)
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

private const val FieldsPerStudent = 6

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
    }
}

fun restoreStudentAppState(values: List<String>): StudentAppState {
    return runCatching {
        val themeMode = ThemeMode.valueOf(values[0])
        val studentCount = values[1].toInt()
        require(studentCount >= 0)
        require(values.size == 2 + studentCount * FieldsPerStudent)
        val students = List(studentCount) { index ->
            val offset = 2 + index * FieldsPerStudent
            StudentProfile(
                name = values[offset],
                studentId = values[offset + 1],
                studyProgram = values[offset + 2],
                semester = values[offset + 3].toInt(),
                email = values[offset + 4],
                phone = values[offset + 5]
            ).also {
                require(it.studentId.isNotBlank())
                require(it.semester > 0)
            }
        }
        require(students.map(StudentProfile::studentId).distinct().size == students.size)
        StudentAppState(students = students, themeMode = themeMode)
    }.getOrElse { DefaultStudentAppState }
}

val StudentAppStateSaver = Saver<StudentAppState, ArrayList<String>>(
    save = { state -> saveStudentAppState(state) },
    restore = { values -> restoreStudentAppState(values) }
)
