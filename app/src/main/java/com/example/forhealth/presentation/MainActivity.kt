package com.example.forhealth.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.forhealth.navigation.Screen
import com.example.forhealth.presentation.habit_tracker_module.components.HabitTrackerModule
import com.example.forhealth.presentation.profile_module.components.ProfileModule
import com.example.forhealth.presentation.ui.theme.ForHealthTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ForHealthTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()

                    NavHost(navController= navController, startDestination = Screen.HabitTracker.rout)
                    {
                        composable(route= Screen.HabitTracker.rout){
                            HabitTrackerModule(navController=navController)
                        }
                        composable(route=Screen.Profile.rout){
                            ProfileModule(navController=navController)
                        }
                    }
                }
            }
        }
    }
}

