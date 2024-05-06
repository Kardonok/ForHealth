package com.example.forhealth.navigation

sealed class Screen(val rout: String) {
    object Profile: Screen("profile")
    object HabitTracker : Screen("habit_tracker")
}