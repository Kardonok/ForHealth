package com.example.forhealth.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


/**данный класс описывает столбцы в таблице, предназначенной для хранения привычек
 *
 * в будущем будет добавлено поле, предназначенное для хранения id пользователя
*/
@Entity(tableName = "habit_tracker_list")
data class HabitTrackerItem(
    @ColumnInfo(name = "habit_name") val habitName:String,
    @ColumnInfo(name = "start_time") val startTime:Long,
    @ColumnInfo(name = "max_score") val maxScore:Long=0,
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "habit_id") val id:Int=0,
)
