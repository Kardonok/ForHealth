package com.example.forhealth.data.source

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.forhealth.data.models.HabitTrackerItem
import kotlinx.coroutines.flow.Flow


@Dao
interface HabitTrackerDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: HabitTrackerItem)

    @Delete
    suspend fun delete(item: HabitTrackerItem)

    @Update
    suspend fun update(item: HabitTrackerItem)

    @Query("SELECT start_time FROM habit_tracker_list WHERE habit_name=:habitName")
    fun getHabit(habitName: String): Long

    @Query("SELECT * FROM habit_tracker_list")
    fun getAllHabits(): Flow<List<HabitTrackerItem>>
}