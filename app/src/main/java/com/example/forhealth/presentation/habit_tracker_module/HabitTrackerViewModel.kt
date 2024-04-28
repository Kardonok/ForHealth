package com.example.forhealth.presentation.habit_tracker_module

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.forhealth.data.repository.App
import com.example.forhealth.data.repository.Database
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class HabitTrackerViewModel(val database:Database):ViewModel() {

    val itemsList = database.getHabitTrackerDao().getAllHabits()

    val habit = mutableStateOf("")
    val addCardIsOpen = mutableStateOf(false)
    fun removeFromDatabase()
    {
        viewModelScope.launch {  }
    }
    fun addToDatabase()
    {
        viewModelScope.launch {  }
    }

    fun updateDatabaseItem()
    {
        viewModelScope.launch {  }
    }

    fun fixCurrentTime()
    {

    }

    fun timeСalculation()
    {

    }

    fun timeFormator()
    {

    }

    companion object{
        val factory: ViewModelProvider.Factory = object: ViewModelProvider.Factory{
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                val database = (checkNotNull(extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]) as App).database
                return HabitTrackerViewModel(database) as T
            }
        }
    }
}