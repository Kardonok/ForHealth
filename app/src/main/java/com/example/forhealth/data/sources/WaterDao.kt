package com.example.forhealth.data.sources

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.forhealth.data.models.WaterItem

import kotlinx.coroutines.flow.Flow


@Dao
interface WaterDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: WaterItem)

    @Delete
    suspend fun delete(item: WaterItem)

    @Update
    suspend fun update(item: WaterItem)

    @Query("SELECT * FROM water_list ORDER BY drinking_time DESC")
    fun getAllWaterItems(): Flow<List<WaterItem>>

    @Query("SELECT SUM(water_amount) FROM water_list")//нужно будет дополнить что время большен нуля сегодняшнего дня
    fun getWaterAmountCount(): Flow<Int?>
}