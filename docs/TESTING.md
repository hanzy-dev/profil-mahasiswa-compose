# Dokumentasi Pengujian - Profil Mahasiswa

Dokumen ini merangkum strategi pengujian, cakupan fitur, dan hasil akhir verifikasi kualitas untuk aplikasi **Profil Mahasiswa**.

## Strategi Pengujian
Proyek ini menggunakan pendekatan *Double-Loop Testing* yang menggabungkan pengujian unit logika bisnis dengan pengujian antarmuka (UI) fungsional.

### 1. Unit Testing (JVM)
Berfokus pada validasi model data, fungsi murni (pure functions), dan restorasi state.
- **Lokasi:** `app/src/test/java/`
- **Total:** 70 Skenario
- **Cakupan Utama:**
    - Validasi format NIM, Email, dan Telepon.
    - Logika perhitungan statistik nilai (rata-rata dan nilai tertinggi).
    - Mekanisme simpan/restorasi `StudentAppState` melalui `Saver`.
    - Integritas data saat penambahan dan pembaharuan mahasiswa.

### 2. Instrumentation Testing (Compose)
Berfokus pada alur pengguna (user workflow), navigasi, dan integrasi antar komponen UI.
- **Lokasi:** `app/src/androidTest/java/`
- **Total:** 41 Skenario
- **Cakupan Utama:**
    - Workflow navigasi: Home → Add → Profile → Grades.
    - Interaksi Photo Picker dan draf foto profil.
    - Responsivitas tema (Dark/Light mode).
    - Validasi UI dan pesan error bahasa Indonesia.
    - Ketahanan draf form saat rotasi layar atau rekreasi Activity.

## Hasil Akhir Verifikasi
Data berdasarkan eksekusi terakhir pada lingkungan pengembangan.

| Kategori | Status | Keterangan |
| :--- | :---: | :--- |
| **Unit Test (JVM)** | ✅ Lulus | 70/70 Passed |
| **Instrumentation Test** | ✅ Lulus | 41/41 Passed (Emulator API 34) |
| **Android Lint** | ✅ Bersih | 0 Errors / 0 Warnings |
| **Build Stability** | ✅ Stabil | Debug & AndroidTest APK berhasil dibuat |

## Perintah Pengujian (PowerShell)

Eksekusi semua tes unit:
```powershell
.\gradlew.bat testDebugUnitTest
```

Eksekusi semua tes instrumentasi (memerlukan emulator):
```powershell
.\gradlew.bat connectedDebugAndroidTest
```

Verifikasi kualitas kode:
```powershell
.\gradlew.bat lintDebug
```

## Matriks QA Manual

| Fitur | Skenario | Hasil |
| :--- | :--- | :---: |
| **Navigasi** | Berpindah antar layar tanpa tumpang tindih destinasi | ✅ |
| **Form UX** | Fokus berpindah otomatis dan keyboard menutup saat selesai | ✅ |
| **State** | Draf edit profil tidak hilang saat berganti tema | ✅ |
| **Aksesibilitas** | Ukuran tombol minimal 48dp dan deskripsi TalkBack akurat | ✅ |
| **Responsivitas** | Tampilan tetap proporsional pada mode lanskap dan font besar | ✅ |
| **Photo Picker** | Memilih foto dari galeri memperbarui draf avatar | ✅ |

---
*Laporan ini dihasilkan secara otomatis sebagai bagian dari Final Quality Gate Batch 7F.*
