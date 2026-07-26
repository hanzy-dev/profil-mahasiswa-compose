package com.muhammadfarhan.profilmahasiswa.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.muhammadfarhan.profilmahasiswa.ui.theme.ProfilMahasiswaTheme

@Preview(showBackground = true, name = "Loading - Terang")
@Composable
fun AppLoadingPreview() {
    ProfilMahasiswaTheme {
        AppLoading(message = "Memuat data...")
    }
}

@Preview(showBackground = true, name = "Empty State - Terang")
@Composable
fun EmptyStatePreview() {
    ProfilMahasiswaTheme {
        EmptyState(
            icon = Icons.Default.Search,
            title = "Tidak ada hasil",
            message = "Coba cari dengan kata kunci lain.",
            action = {
                Button(onClick = {}) {
                    Text("Coba Lagi")
                }
            }
        )
    }
}

@Preview(showBackground = true, name = "Section Title")
@Composable
fun AppSectionTitlePreview() {
    ProfilMahasiswaTheme {
        AppSectionTitle(title = "Informasi Akademik")
    }
}

@Preview(showBackground = true, name = "Info Row")
@Composable
fun InfoRowPreview() {
    ProfilMahasiswaTheme {
        InfoRow(
            label = "Email",
            value = "mahasiswa@example.com",
            icon = Icons.Default.Info
        )
    }
}
