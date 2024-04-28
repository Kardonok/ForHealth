package com.example.forhealth.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "habit_tracker_list")
data class HabitTrackerItem(
    @ColumnInfo(name = "habit_score") val habitName:String,
    @ColumnInfo(name = "start_score") val startTime:Long,
    @ColumnInfo(name = "max_score") val maxScore:Long,
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "habit_id") val id:Int,
)
