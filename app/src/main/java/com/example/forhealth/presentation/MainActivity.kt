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
import com.example.forhealth.data.database.App
import com.example.forhealth.navigation.Screen
import com.example.forhealth.presentation.habit_tracker_module.components.HabitTrackerModule
import com.example.forhealth.presentation.loading_module.components.LoadingModule
import com.example.forhealth.presentation.profile_module.components.ProfileModule
import com.example.forhealth.presentation.registration_module.components.RegistrationModule
import com.example.forhealth.presentation.ui.theme.ForHealthTheme
import com.example.forhealth.presentation.water_module.components.WaterModule
import com.example.forhealth.presentation.woman_health_module.components.WomanHealthModule

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
                    val app = applicationContext as App
                    val dataStoreRepository = app.dataStoreRepository

                    val navController = rememberNavController()

                    NavHost(navController= navController, startDestination = Screen.Loading.route)
                    {
                        composable(route= Screen.HabitTracker.route){
                            HabitTrackerModule(navController=navController)
                        }
                        composable(route=Screen.Profile.route){
                            ProfileModule(navController=navController)
                        }
                        composable(route=Screen.Registration.route){
                            RegistrationModule(navController=navController)
                        }
                        composable(route=Screen.Loading.route){
                            LoadingModule(navController = navController, dataStoreRepository = dataStoreRepository)
                        }
                        composable(route=Screen.Water.route){
                            WaterModule(navController = navController)
                        }
                        composable(route=Screen.WomanHealth.route){
                            WomanHealthModule(navController = navController)
                        }
                    }
                }
            }
        }
    }
}

