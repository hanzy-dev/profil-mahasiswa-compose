package com.muhammadfarhan.profilmahasiswa.app

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class ThemeTest {
    @Test
    fun `effective SYSTEM mode follows a light system state`() {
        assertFalse(resolveDarkTheme(ThemeMode.SYSTEM, systemDarkTheme = false))
    }

    @Test
    fun `effective SYSTEM mode follows a dark system state`() {
        assertTrue(resolveDarkTheme(ThemeMode.SYSTEM, systemDarkTheme = true))
    }

    @Test
    fun `LIGHT always resolves to false`() {
        assertFalse(resolveDarkTheme(ThemeMode.LIGHT, systemDarkTheme = true))
        assertFalse(resolveDarkTheme(ThemeMode.LIGHT, systemDarkTheme = false))
    }

    @Test
    fun `DARK always resolves to true`() {
        assertTrue(resolveDarkTheme(ThemeMode.DARK, systemDarkTheme = false))
        assertTrue(resolveDarkTheme(ThemeMode.DARK, systemDarkTheme = true))
    }

    @Test
    fun `toggling from effective light returns DARK`() {
        assertEquals(ThemeMode.DARK, nextThemeMode(isCurrentlyDark = false))
    }

    @Test
    fun `toggling from effective dark returns LIGHT`() {
        assertEquals(ThemeMode.LIGHT, nextThemeMode(isCurrentlyDark = true))
    }
}
