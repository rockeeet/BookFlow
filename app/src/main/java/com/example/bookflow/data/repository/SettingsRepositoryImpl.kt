package com.example.bookflow.data.repository

import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.bookflow.data.local.userPreferencesDataStore
import com.example.bookflow.data.model.AppThemeMode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import android.content.Context

class SettingsRepositoryImpl(
    private val context: Context
) : SettingsRepository {

    private companion object {
        val THEME_MODE = stringPreferencesKey("theme_mode")
    }

    override val themeMode: Flow<AppThemeMode> =
        context.userPreferencesDataStore.data.map { preferences ->
            val savedTheme = preferences[THEME_MODE]

            AppThemeMode.entries.firstOrNull { it.name == savedTheme }
                ?: AppThemeMode.SYSTEM
        }

    override suspend fun setThemeMode(themeMode: AppThemeMode) {
        context.userPreferencesDataStore.edit { preferences ->
            preferences[THEME_MODE] = themeMode.name
        }
    }
}