package com.example.forhealth.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun NavBar(navController:NavHostController, modifier: Modifier=Modifier)
{
    Row(modifier= modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {

        IconButton(onClick = { /* Обработка клика */ }) {
            Icon(Icons.Filled.Notifications, contentDescription = "Add Icon")
        }


        IconButton(onClick = { /* Обработка клика */ }) {
            Icon(Icons.Filled.DateRange, contentDescription = "Add Icon")
        }


        IconButton(onClick = {navController.navigate(Screen.HabitTracker.rout)}) {
            Icon(Icons.Filled.List, contentDescription = "Add Icon")
        }

        IconButton(onClick = {navController.navigate(Screen.Profile.rout) }) {
            Icon(Icons.Filled.AccountCircle, contentDescription = "Add Icon")
        }
    }
}
