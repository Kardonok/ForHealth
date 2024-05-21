package com.example.forhealth.presentation.water_module

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.forhealth.data.database.App
import com.example.forhealth.data.models.WaterItem
import com.example.forhealth.data.repositories.WaterRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.ZoneOffset

class WaterViewModel(private val waterRepository: WaterRepository): ViewModel() {
    val waterItemList = waterRepository.getAllWaterItems()

    var customWaterAmount: Int by mutableStateOf(0)
    var textFieldValue by mutableStateOf("")

    fun updateTextFieldValue(newValue: String) {
        if (newValue.all { it.isDigit() }) {
            textFieldValue = newValue
            customWaterAmount = newValue.toIntOrNull() ?: 0
        }
    }

    private val _waterState = MutableStateFlow(WaterState(usedWaterAmount = 0, totalWaterAmount = 3500, addWaterCardIsOpen = false))
    val waterState: StateFlow<WaterState> = _waterState

    init {
        viewModelScope.launch {
            waterRepository.getWaterAmountCount().collect { waterAmount ->
                if(waterAmount!=null)
                {
                    _waterState.update {
                        it.copy(usedWaterAmount = waterAmount)
                    }
                }
                else
                {
                    _waterState.update {
                        it.copy(usedWaterAmount = 0)
                    }
                }
            }
        }
    }

    fun deleteFromDatabase(waterItem: WaterItem)
    {
        viewModelScope.launch {
            waterRepository.deleteWaterItem(waterItem)
        }
    }

    fun addToDatabase(waterAmount:Int)
    {
        if(waterAmount!=0) {
            viewModelScope.launch {
                val newWaterItem = WaterItem(waterAmount = waterAmount, drinkingTime = currentTime())
                waterRepository.insertWaterItem(newWaterItem)
            }
        }
        _waterState.update {
            it.copy(addWaterCardIsOpen = false)
        }
    }

    fun waterCardIsOpen(addWaterCardIsOpen: Boolean)
    {
        _waterState.update {
            it.copy(addWaterCardIsOpen = addWaterCardIsOpen)
        }
    }

    fun currentTime(): Long {
        return LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)
    }

    fun timeFormatter(seconds: Long): String {
        val hours = (seconds / 3600) % 24
        val minutes = (seconds % 3600) / 60
        return String.format("%02d:%02d", hours, minutes)
    }

    companion object{
        val factory: ViewModelProvider.Factory = object: ViewModelProvider.Factory{
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                val database = (checkNotNull(extras[APPLICATION_KEY]) as App).waterRepository
                return WaterViewModel(database) as T
            }
        }
    }
}

data class WaterState(
    var usedWaterAmount:Int,
    var totalWaterAmount:Int,
    var addWaterCardIsOpen:Boolean,
)