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
import com.example.forhealth.data.repositories.dataStore
import com.example.forhealth.navigation.Screen
import com.example.forhealth.presentation.habit_tracker_module.components.HabitTrackerModule
import com.example.forhealth.presentation.loading_module.components.LoadingModule
import com.example.forhealth.presentation.profile_module.components.ProfileModule
import com.example.forhealth.presentation.registration_module.components.GreetingModule
import com.example.forhealth.presentation.registration_module.components.LoginModule
import com.example.forhealth.presentation.registration_module.components.RegistrationModule
import com.example.forhealth.presentation.ui.theme.ForHealthTheme
import com.example.forhealth.presentation.water_module.components.WaterModule

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

                    NavHost(navController= navController, startDestination = Screen.Loading.rout)
                    {
                        composable(route= Screen.HabitTracker.rout){
                            HabitTrackerModule(navController=navController)
                        }
                        composable(route=Screen.Profile.rout){
                            ProfileModule(navController=navController)
                        }
                        composable(route=Screen.Greeting.rout){
                            GreetingModule(navController=navController)
                        }
                        composable(route=Screen.Login.rout){
                            LoginModule(navController=navController)
                        }
                        composable(route=Screen.Registration.rout){
                            RegistrationModule(navController=navController)
                        }
                        composable(route=Screen.Loading.rout){
                            LoadingModule(navController = navController, dataStoreRepository = dataStoreRepository)
                        }
                        composable(route=Screen.Water.rout){
                            WaterModule(navController = navController)
                        }
                    }
                }
            }
        }
    }
}

