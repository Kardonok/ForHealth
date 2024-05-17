package com.example.forhealth.data.repositories

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

const val PREFERENCE_NAME = "preferences"
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = PREFERENCE_NAME)

class DataStoreRepository( private val dataStore: DataStore<Preferences>)
{
    private companion object{
        val IS_FIRST_LAUNCH = booleanPreferencesKey("is_first_launch")
        val IS_DARK_THEME = booleanPreferencesKey("is_dark_theme")
    }

    suspend fun saveLaunchPreference(isFirstLaunch: Boolean)
    {
        dataStore.edit { preferences ->
            preferences[IS_FIRST_LAUNCH] = isFirstLaunch
        }
    }

    suspend fun saveThemePreference(isDarkTheme:Boolean)
    {
        dataStore.edit { preferences ->
            preferences[IS_DARK_THEME] = isDarkTheme
        }
    }

    val isFirstLaunch: Flow<Boolean> = dataStore.data.catch {
        if(it is IOException) {
            Log.e("DataStore", "Error reading first launch preference", it)
            emit(emptyPreferences())
        } else {
            throw it
        }
    }.map { preferences ->
        preferences[IS_FIRST_LAUNCH] ?: true}

    val isDarkTheme: Flow<Boolean> = dataStore.data.catch {
        if(it is IOException) {
            Log.e("DataStore", "Error reading first launch preference", it)
            emit(emptyPreferences())
        } else {
            throw it
        }
    }.map { preferences ->
        preferences[IS_DARK_THEME] ?: false}
}