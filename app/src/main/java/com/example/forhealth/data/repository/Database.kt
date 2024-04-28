package com.example.forhealth.data.repository

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.forhealth.data.models.HabitTrackerItem
import com.example.forhealth.data.source.HabitTrackerDao


@androidx.room.Database(
    entities = [HabitTrackerItem::class],
    version = 1,
    exportSchema = false,
)
abstract class Database:RoomDatabase() {

    abstract fun getHabitTrackerDao(): HabitTrackerDao

    companion object {
        @Volatile
        private var Instance: Database? = null

        fun getDatabase(context: Context): Database {
            // if the Instance is not null, return it, otherwise create a new database instance.
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, Database::class.java, "shopping_list_database")
                    .build()
                    .also { Instance = it }
            }
        }
    }
}