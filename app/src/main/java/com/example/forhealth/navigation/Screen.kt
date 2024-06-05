package com.example.forhealth.navigation

sealed class Screen(val route: String) {
    object Profile: Screen("profile")
    object HabitTracker : Screen("habit_tracker")
    object Registration : Screen("registration")
    object Loading : Screen("loading")
    object Water : Screen("water")
    object WomanHealth : Screen("woman_health")
}