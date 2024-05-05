package com.example.forhealth.data.database

import android.app.Application
import com.example.forhealth.data.repositories.OfflineHabitTrackerRepository

class App(): Application() {
    val habitTrackerRepository by lazy { OfflineHabitTrackerRepository(
        AppDatabase.getDatabase(this).getHabitTrackerDao())
    }
}