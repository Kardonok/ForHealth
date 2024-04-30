package com.example.forhealth.presentation.habit_tracker_module

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.forhealth.data.models.HabitTrackerItem
import com.example.forhealth.data.repository.App
import com.example.forhealth.data.repository.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.ZoneOffset

class HabitTrackerViewModel(private val database: AppDatabase):ViewModel() {

    val itemsList = database.getHabitTrackerDao().getAllHabits()

    var habit:String by mutableStateOf("")
    var addCardIsOpen:Boolean by mutableStateOf(false)


    private val _currentTime = MutableStateFlow(fixCurrentTime())
    val currentTime: StateFlow<Long> = _currentTime

    init {
        updateCurrentTime()
    }

    private fun updateCurrentTime() {
        viewModelScope.launch {
            while (true) {
                _currentTime.emit(fixCurrentTime())
                delay(1000) // Обновление каждую секунду
            }
        }
    }


    fun deleteFromDatabase(habitTrackerItem: HabitTrackerItem)
    {
        viewModelScope.launch {
            database.getHabitTrackerDao().delete(habitTrackerItem)
        }
    }

    fun addToDatabase(habitName:String)
    {
        viewModelScope.launch {
            val newHabitTrackerItem = HabitTrackerItem(habitName=habitName, startTime = fixCurrentTime())
            database.getHabitTrackerDao().insert(newHabitTrackerItem)
        }
    }

    fun updateDatabaseItem(habitTrackerItem: HabitTrackerItem) {
        viewModelScope.launch {
            var maxScore:Long = habitTrackerItem.maxScore
            if(maxScore<(fixCurrentTime()-habitTrackerItem.startTime)){
                maxScore = fixCurrentTime()-habitTrackerItem.startTime
            }
            val updateItem = habitTrackerItem.copy(startTime = fixCurrentTime(), maxScore =maxScore )
            database.getHabitTrackerDao().update(updateItem)
        }
    }


    fun fixCurrentTime(): Long {
        return LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)
    }

    fun timeFormator(seconds:Long):String
    {
        val h = seconds / 3600
        val m = seconds % 3600 / 60
        val s = seconds % 60

        return String.format("%02d:%02d:%02d", h, m, s)
    }

    companion object{
        val factory: ViewModelProvider.Factory = object: ViewModelProvider.Factory{
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                val database = (checkNotNull(extras[APPLICATION_KEY]) as App).database
                return HabitTrackerViewModel(database) as T
            }
        }
    }
}