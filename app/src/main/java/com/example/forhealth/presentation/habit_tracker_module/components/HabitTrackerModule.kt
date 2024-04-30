package com.example.forhealth.presentation.habit_tracker_module.components

import androidx.compose.foundation.background
import androidx.compose.runtime.Composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Refresh

import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue


import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.forhealth.data.models.HabitTrackerItem
import com.example.forhealth.presentation.habit_tracker_module.HabitTrackerViewModel
import org.intellij.lang.annotations.JdkConstants.FontStyle
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

@Preview(showBackground = true)
@Composable
fun HabitTrackerModulePreview()
{
    HabitTrackerModule()
}

@Composable
fun HabitTrackerModule(modifier: Modifier=Modifier,habitTrackerViewModel: HabitTrackerViewModel=viewModel(factory=HabitTrackerViewModel.factory)) {

    val itemList = habitTrackerViewModel.itemsList.collectAsState(initial = emptyList())

    val currentTime by habitTrackerViewModel.currentTime.collectAsState(initial = habitTrackerViewModel.fixCurrentTime())

    if(habitTrackerViewModel.addCardIsOpen)
    {
        AddCard(addCardIsClosed = {word:String-> habitTrackerViewModel.addToDatabase(word) }, habitTrackerViewModel = habitTrackerViewModel)
    }

    Column(horizontalAlignment=Alignment.CenterHorizontally,modifier = modifier) {
        LazyColumn(modifier = modifier
            .fillMaxWidth()) {
            items(itemList.value){
                    item->
                Box(modifier = modifier.padding(top=16.dp, start = 16.dp, end=16.dp))
                {
                    HabitCard(
                        habitTrackerItem = item,
                        habitTrackerViewModel = habitTrackerViewModel,
                        currentTime=currentTime)
                }
            }
            item {

                IconButton(onClick = { habitTrackerViewModel.addCardIsOpen = true }, modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)) {
                    Icon(Icons.Filled.Add, contentDescription = "Add Icon")
                }
            }
        }
    }
}


@Composable
fun HabitCard(currentTime:Long,habitTrackerItem: HabitTrackerItem, habitTrackerViewModel: HabitTrackerViewModel, modifier: Modifier=Modifier)
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
                IconButton(onClick = {  habitTrackerViewModel.deleteFromDatabase(habitTrackerItem) }) {
                    Icon(Icons.Filled.Close, contentDescription = "Close Icon")
                }
                Text(text = habitTrackerItem.habitName, fontSize = 24.sp, fontFamily = FontFamily.SansSerif)
                //надо заменить на iconbutton
                IconButton(onClick = { habitTrackerViewModel.updateDatabaseItem(habitTrackerItem) }) {
                    Icon(Icons.Filled.Refresh, contentDescription = "Reset Icon")
                }
            }
            Spacer(modifier = modifier.height(8.dp))
            Text(text = "--${habitTrackerViewModel.timeFormator(currentTime-habitTrackerItem.startTime)}--",fontFamily = FontFamily.SansSerif, fontSize = 30.sp)
            Spacer(modifier = modifier.height(16.dp))
            Text(text = "Лучшая череда: ${habitTrackerViewModel.timeFormator(habitTrackerItem.maxScore)}",fontFamily = FontFamily.SansSerif, fontSize = 16.sp)
            Spacer(modifier = modifier.height(8.dp))
        }
    }
}

@Composable
fun AddCard(addCardIsClosed: (habit:String) -> Unit,habitTrackerViewModel: HabitTrackerViewModel,modifier: Modifier=Modifier)
{
    Dialog(onDismissRequest = { /*TODO*/ }) {
        Card() {
            Column(horizontalAlignment = Alignment.End, modifier = modifier.padding(16.dp)) {
                TextField(value = habitTrackerViewModel.habit, onValueChange = {habitTrackerViewModel.habit=it})
                Button(onClick = { addCardIsClosed(habitTrackerViewModel.habit)
                    habitTrackerViewModel.addCardIsOpen=false}) {
                    Text(text = "Ок")
                }
            }

        }
    }
}