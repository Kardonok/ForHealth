package com.example.forhealth.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "water_list")
data class WaterItem(
    @ColumnInfo(name = "water_amount") val waterAmount: Int,
    @ColumnInfo(name = "drinking_time") val drinkingTime: Long,
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "water_id") val id:Int=0,
)