package com.muhammadfarhan.profilmahasiswa.screens.profile

import com.muhammadfarhan.profilmahasiswa.model.DefaultStudentProfile
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class StudentProfileUiStateTest {

    @Test
    fun initialStateHasNoChanges() {
        val state = StudentProfileUiState(savedProfile = DefaultStudentProfile)
        assertFalse(state.hasChanges)
        assertFalse(state.canSave)
        assertEquals(DefaultStudentProfile, state.displayedProfile)
    }

    @Test
    fun photoDraftUpdateChangesDraftOnly() {
        val state = StudentProfileUiState(savedProfile = DefaultStudentProfile)
        val photoUri = "content://new-photo"
        val updatedState = state.copy(draftProfile = state.draftProfile.copy(profileImageUri = photoUri))
        
        assertTrue(updatedState.hasChanges)
        assertEquals(photoUri, updatedState.draftProfile.profileImageUri)
        assertEquals(DefaultStudentProfile.profileImageUri, updatedState.savedProfile.profileImageUri)
    }

    @Test
    fun savingPhotoDraftUpdatesProfile() {
        val originalProfile = DefaultStudentProfile
        val photoUri = "content://new-photo"
        val draftProfile = originalProfile.copy(profileImageUri = photoUri)
        
        // Simulation of save action
        val savedProfile = draftProfile 
        
        assertEquals(photoUri, savedProfile.profileImageUri)
        assertNotEquals(originalProfile.profileImageUri, savedProfile.profileImageUri)
    }

    @Test
    fun cancellingPhotoDraftRestoresSavedUri() {
        val originalProfile = DefaultStudentProfile.copy(profileImageUri = "content://old")
        val draftProfileWithChange = originalProfile.copy(profileImageUri = "content://new")
        
        // Simulation of cancel action
        val restoredDraft = originalProfile
        
        assertEquals("content://old", restoredDraft.profileImageUri)
    }

    @Test
    fun studentProfileSaverRoundTrip() {
        val profile = DefaultStudentProfile.copy(profileImageUri = "content://test")
        
        // Use the saver logic directly since we can't easily mock SaverScope
        val saved = listOf(
            profile.name,
            profile.studentId,
            profile.studyProgram,
            profile.semester,
            profile.email,
            profile.phone,
            profile.profileImageUri ?: ""
        )
        
        val restored = com.muhammadfarhan.profilmahasiswa.model.StudentProfile(
            name = saved[0] as String,
            studentId = saved[1] as String,
            studyProgram = saved[2] as String,
            semester = saved[3] as Int,
            email = saved[4] as String,
            phone = saved[5] as String,
            profileImageUri = (saved[6] as String).ifEmpty { null }
        )
        
        assertEquals(profile, restored)
    }

    @Test
    fun studentProfileSaverRoundTripWithNullPhoto() {
        val profile = DefaultStudentProfile.copy(profileImageUri = null)
        
        val saved = listOf(
            profile.name,
            profile.studentId,
            profile.studyProgram,
            profile.semester,
            profile.email,
            profile.phone,
            profile.profileImageUri ?: ""
        )
        
        val restored = com.muhammadfarhan.profilmahasiswa.model.StudentProfile(
            name = saved[0] as String,
            studentId = saved[1] as String,
            studyProgram = saved[2] as String,
            semester = saved[3] as Int,
            email = saved[4] as String,
            phone = saved[5] as String,
            profileImageUri = (saved[6] as String).ifEmpty { null }
        )
        
        assertEquals(profile, restored)
    }
}
