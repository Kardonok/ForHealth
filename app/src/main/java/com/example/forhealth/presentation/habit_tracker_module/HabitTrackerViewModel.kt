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
import com.example.forhealth.data.database.App
import com.example.forhealth.data.repositories.HabitTrackerRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.ZoneOffset

class HabitTrackerViewModel(private val habitTrackerRepository: HabitTrackerRepository):ViewModel() {

    val itemsList = habitTrackerRepository.getAllHabits()

    //поле хранящее название привычки при вводе в поле добавления привычки
    var habit:String by mutableStateOf("")
    //просто перемычка для карточки добавления
    var addCardIsOpen:Boolean by mutableStateOf(false)

    //вычисление времени просиходит так: берем start_time привычки, текущее время и вычисляем по формуле: текущее время - start_time
    private val _currentTime = MutableStateFlow(fixCurrentTime())
    val currentTime: StateFlow<Long> = _currentTime

    init {
        updateCurrentTime()
    }

    //эта функция запускается в другом потоке и обновляет все таймеры с промежутком в секунду
    private fun updateCurrentTime() {
        viewModelScope.launch {
            while (true) {
                _currentTime.emit(fixCurrentTime())
                delay(1000) // Обновление каждую секунду
            }
        }
    }

    //Проверка ввода, пока просто поставил ограничение ввода на 15 символов, если ввести больше интерфейс может поплыть
    fun checkInput(word:String)
    {
        if(word.length<15)
            habit=word
    }

    fun deleteFromDatabase(habitTrackerItem: HabitTrackerItem)
    {
        viewModelScope.launch {
            habitTrackerRepository.deleteHabit(habitTrackerItem)
        }
    }

    fun addToDatabase(habitName:String)
    {
        viewModelScope.launch {
            val newHabitTrackerItem = HabitTrackerItem(habitName=habitName, startTime = fixCurrentTime())
            habitTrackerRepository.insertHabit(newHabitTrackerItem)
            habit=""
        }
    }

    fun updateDatabaseItem(habitTrackerItem: HabitTrackerItem) {
        viewModelScope.launch {
            var maxScore:Long = habitTrackerItem.maxScore
            if(maxScore<(fixCurrentTime()-habitTrackerItem.startTime)){
                maxScore = fixCurrentTime()-habitTrackerItem.startTime
            }
            val updateItem = habitTrackerItem.copy(startTime = fixCurrentTime(), maxScore =maxScore )
            habitTrackerRepository.updateHabit(updateItem)
        }

    }


    //беру время в секундах, если по другому, то просто делаю временную метку
    fun fixCurrentTime(): Long {
        return LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)
    }

    //тк используется java.time время я беру в секундах, ну короче там идет отсчет от какого то числа я его и беру
    //а потом форматирую так как нам надо
    fun timeFormator(seconds:Long):String
    {
        val h = seconds / 3600
        val m = seconds % 3600 / 60
        val s = seconds % 60

        return String.format("%02d:%02d:%02d", h, m, s)
    }

    //это неприятное последствие отсутствия dependency injection в будущем надо исправить ибо это нарушение паттернов
    companion object{
        val factory: ViewModelProvider.Factory = object: ViewModelProvider.Factory{
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                val database = (checkNotNull(extras[APPLICATION_KEY]) as App).habitTrackerRepository
                return HabitTrackerViewModel(database) as T
            }
        }
    }
}