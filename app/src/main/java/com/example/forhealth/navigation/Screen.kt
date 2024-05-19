package com.example.forhealth.navigation

sealed class Screen(val rout: String) {
    object Profile: Screen("profile")
    object HabitTracker : Screen("habit_tracker")

    object Greeting : Screen("greeting")
    object Login : Screen("login")
    object Registration : Screen("registration")

    object Loading : Screen("loading")

    object Water : Screen("water")
}