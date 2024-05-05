package com.example.forhealth.data.repositories

import com.example.forhealth.data.models.HabitTrackerItem
import com.example.forhealth.data.sources.HabitTrackerDao
import kotlinx.coroutines.flow.Flow


/**
    Тут реализуется паттерн Repository и принцип DIP из SOLID.

    Пока что есть репозиторий только для работы с данными из локальной базы данных.
    Но скоро сюда будет добавлен репозиторий для работы со внешними данными.
*/
interface HabitTrackerRepository
{
    suspend fun insertHabit(habitTrackerItem: HabitTrackerItem)


    suspend fun deleteHabit(habitTrackerItem: HabitTrackerItem)


    suspend fun updateHabit(habitTrackerItem: HabitTrackerItem)

    fun getAllHabits(): Flow<List<HabitTrackerItem>>
}


/**
    Репозиторий для работы с данными из локальной таблицы трекера привычек
*/
class OfflineHabitTrackerRepository(private val habitTrackerDao:HabitTrackerDao):HabitTrackerRepository {
    override suspend fun insertHabit(habitTrackerItem: HabitTrackerItem) {
        habitTrackerDao.insert(habitTrackerItem)
    }

    override suspend fun deleteHabit(habitTrackerItem: HabitTrackerItem) {
        habitTrackerDao.delete(habitTrackerItem)
    }

    override suspend fun updateHabit(habitTrackerItem: HabitTrackerItem) {
        habitTrackerDao.update(habitTrackerItem)
    }

    override fun getAllHabits(): Flow<List<HabitTrackerItem>> {
        return habitTrackerDao.getAllHabits()
    }
}