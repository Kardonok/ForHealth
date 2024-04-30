package com.example.forhealth.data.repository

import android.app.Application
import com.example.forhealth.data.repository.AppDatabase

class App(): Application() {
    val database by lazy { AppDatabase.getDatabase(this)}
}