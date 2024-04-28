package com.example.forhealth.data.repository

import android.app.Application

class App(): Application() {
    val database by lazy { Database.getDatabase(this)}
}