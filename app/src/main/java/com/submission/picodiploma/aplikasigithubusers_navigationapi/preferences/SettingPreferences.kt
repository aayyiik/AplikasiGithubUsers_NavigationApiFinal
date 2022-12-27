package com.submission.picodiploma.aplikasigithubusers_navigationapi.preferences

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


private val Context.prefStoreData by preferencesDataStore("setting")
class SettingPreferences constructor(context: Context){
    private val settingThemeDataStore = context.prefStoreData
    private val themeKey = booleanPreferencesKey("theme_setting")

    fun getThemeSetting(): Flow<Boolean> = settingThemeDataStore.data.map {
        preferences -> preferences[themeKey] ?: false
    }

    suspend fun saveThemeSetting(isDark: Boolean){
        settingThemeDataStore.edit { preferences ->
            preferences[themeKey] = isDark
        }
    }

}