package com.example.forhealth.presentation.habit_tracker_module.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
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

import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue


import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.forhealth.data.models.HabitTrackerItem
import com.example.forhealth.presentation.habit_tracker_module.HabitTrackerViewModel


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
        AddCard(addHabit = {word:String-> habitTrackerViewModel.addToDatabase(word) }, habitTrackerViewModel = habitTrackerViewModel)
    }

    Column(horizontalAlignment=Alignment.CenterHorizontally,modifier = modifier) {
        LazyColumn(modifier = modifier
            .fillMaxWidth()) {
            items(itemList.value){
                    item->
                AnimatedVisibility(visible = true, enter = fadeIn() + expandVertically()) {
                    Box(modifier = modifier.padding(top=16.dp, start = 16.dp, end=16.dp))
                    {
                        HabitCard(
                            habitTrackerItem = item,
                            habitTrackerViewModel = habitTrackerViewModel,
                            currentTime=currentTime)
                    }
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

//Отдельная карточка привычки
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
                //Кнопка удаления привычки
                IconButton(onClick = {  habitTrackerViewModel.deleteFromDatabase(habitTrackerItem) }) {
                    Icon(Icons.Filled.Close, contentDescription = "Close Icon")
                }
                Text(text = habitTrackerItem.habitName, fontSize = 24.sp, fontFamily = FontFamily.SansSerif)
                //Кнопка сброса таймера и фиксации времени
                IconButton(onClick = { habitTrackerViewModel.updateDatabaseItem(habitTrackerItem) }) {
                    Icon(Icons.Filled.Refresh, contentDescription = "Reset Icon")
                }
            }
            Spacer(modifier = modifier.height(8.dp))
            //Поле отображающее время прошедшее с начала отсчета
            Text(text = "--${habitTrackerViewModel.timeFormator(currentTime-habitTrackerItem.startTime)}--",fontFamily = FontFamily.SansSerif, fontSize = 30.sp)
            Spacer(modifier = modifier.height(16.dp))
            //Поле отображающее максимальное время, которое смог выдержать пользователь
            Text(text = "Лучшая череда: ${habitTrackerViewModel.timeFormator(habitTrackerItem.maxScore)}",fontFamily = FontFamily.SansSerif, fontSize = 16.sp)
            Spacer(modifier = modifier.height(8.dp))
        }
    }
}

//карточка добавления привычки, возможно она излишне минималистичка, ее бы я исправил
@Composable
fun AddCard(addHabit: (habit:String) -> Unit,habitTrackerViewModel: HabitTrackerViewModel,modifier: Modifier=Modifier)
{
    Dialog(onDismissRequest = { /*TODO*/ }) {
        Card() {
            Column(horizontalAlignment = Alignment.End, modifier = modifier.padding(top=8.dp,start=8.dp,end=8.dp)) {
                OutlinedTextField(value = habitTrackerViewModel.habit,
                    onValueChange = { habitTrackerViewModel.checkInput(it) },
                    label = { Text("Название привычки") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),)
                TextButton(onClick = { addHabit(habitTrackerViewModel.habit)
                    habitTrackerViewModel.addCardIsOpen=false}) {
                    Text(text = "ОK",fontFamily = FontFamily.SansSerif,)
                }
            }

        }
    }
}
