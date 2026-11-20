package com.example.bookflow.data.repository

import com.example.bookflow.data.model.AppThemeMode
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {

    val themeMode: Flow<AppThemeMode>

    suspend fun setThemeMode(themeMode: AppThemeMode)
}