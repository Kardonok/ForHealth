package com.example.forhealth.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import com.example.forhealth.R

@Composable
fun NavBar(navController:NavHostController, modifier: Modifier=Modifier)
{
    val currentRoute = navController.currentBackStackEntry?.destination?.route

    Row(modifier= modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {

        IconButton(onClick = { if (currentRoute != Screen.Water.route) { navController.navigate(Screen.Water.route) } })
        {
            Icon(painterResource(id = R.drawable.water_icon), contentDescription = "Water")
        }

        IconButton(onClick = { /* Обработка клика */ }) {
            Icon(painterResource(id = R.drawable.alarm_icon), contentDescription = "Smart alarm")
        }

        IconButton(onClick = { if (currentRoute != Screen.Profile.route) { navController.navigate(Screen.Profile.route) } })
        {
            Icon(painterResource(id = R.drawable.profile_icon), contentDescription = "Profile")
        }

        IconButton(onClick = { if (currentRoute != Screen.WomanHealth.route) { navController.navigate(Screen.WomanHealth.route) } }) {
            Icon(painterResource(id = R.drawable.woman_health_icon), contentDescription = "Woman health")
        }


        IconButton(onClick = { if (currentRoute != Screen.HabitTracker.route) { navController.navigate(Screen.HabitTracker.route) } })
        {
            Icon(painterResource(id = R.drawable.list_icon), contentDescription = "Habit tracker")
        }

    }
}

