package com.muhammadfarhan.profilmahasiswa

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.muhammadfarhan.profilmahasiswa.screens.profile.StudentProfileRoute
import com.muhammadfarhan.profilmahasiswa.ui.theme.ProfilMahasiswaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            ProfilMahasiswaTheme {
                StudentProfileRoute()
            }
        }
    }
}
