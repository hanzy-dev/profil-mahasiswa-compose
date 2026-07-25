package com.muhammadfarhan.profilmahasiswa.app

enum class ThemeMode {
    SYSTEM,
    LIGHT,
    DARK
}

/**
 * Resolves the effective dark-theme flag from a [ThemeMode] and the
 * current system dark-theme setting. This is a pure function that can be
 * tested without Compose or Android framework dependencies.
 */
fun resolveDarkTheme(themeMode: ThemeMode, systemDarkTheme: Boolean): Boolean =
    when (themeMode) {
        ThemeMode.SYSTEM -> systemDarkTheme
        ThemeMode.LIGHT -> false
        ThemeMode.DARK -> true
    }

/**
 * Returns the next explicit [ThemeMode] when the user taps the toggle.
 * SYSTEM is the initial default; once the user toggles, the app moves to
 * an explicit LIGHT or DARK selection.
 *
 * @param isCurrentlyDark the *effective* dark-theme state (after SYSTEM resolution)
 */
fun nextThemeMode(isCurrentlyDark: Boolean): ThemeMode =
    if (isCurrentlyDark) ThemeMode.LIGHT else ThemeMode.DARK
