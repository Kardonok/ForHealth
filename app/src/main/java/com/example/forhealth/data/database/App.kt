package com.example.forhealth.data.database

import android.app.Application
import com.example.forhealth.data.repositories.DataStoreRepository
import com.example.forhealth.data.repositories.OfflineHabitTrackerRepository
import com.example.forhealth.data.repositories.OfflineProfileRepository
import com.example.forhealth.data.repositories.dataStore

class App(): Application() {
    val habitTrackerRepository by lazy { OfflineHabitTrackerRepository(
        AppDatabase.getDatabase(this).getHabitTrackerDao())
    }
    val profileRepository by lazy { OfflineProfileRepository(
        AppDatabase.getDatabase(this).getProfileDao())
    }
    val dataStoreRepository by lazy { DataStoreRepository(dataStore) }
}