package com.example.forhealth.presentation.habit_tracker_module.components

import androidx.compose.runtime.Composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding

import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.TextField


import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog

@Preview(showBackground = true)
@Composable
fun HabitTrackerModulePreview()
{
    HabitTrackerModule()
}

@Composable
fun HabitTrackerModule(modifier: Modifier=Modifier) {
    Column(modifier = modifier
        .fillMaxWidth()
        .padding(16.dp)) {
        HabitCard()
        HabitCard()
        HabitCard()
        HabitCard()
    }
}

@Composable
fun HabitCard(modifier: Modifier=Modifier)
{
    Card(modifier=modifier) {
        Column(horizontalAlignment = Alignment.CenterHorizontally,
            modifier= modifier
                .fillMaxWidth()
                .padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier=modifier.fillMaxWidth()) {
                //надо заменить на iconbutton
                Button(onClick = { /*TODO*/ }) {
                    
                }
                Text(text = "Привычка")
                //надо заменить на iconbutton
                Button(onClick = { /*TODO*/ }) {

                }
            }
            Text(text = "--00:00:00--", fontSize = 16.sp)
        }
    }
}

@Composable
fun AddCard(modifier: Modifier=Modifier)
{
    Dialog(onDismissRequest = { /*TODO*/ }) {
        Card {
            TextField(value = "", onValueChange = {})
            Button(onClick = { /*TODO*/ }) {
                
            }
        }
    }
}