package com.example.forhealth.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.forhealth.data.models.HabitTrackerItem
import com.example.forhealth.data.models.ProfileItem
import com.example.forhealth.data.sources.HabitTrackerDao
import com.example.forhealth.data.sources.ProfileDao

/** Главная база данных всего приложения
 *
 *  метод getHabitTrackerDao() служит для получения Dao для HabitTracker
 */
@Database(entities = [HabitTrackerItem::class,ProfileItem::class],
    version = 1,
    exportSchema = false,
)
abstract class AppDatabase: RoomDatabase() {

    abstract fun getHabitTrackerDao(): HabitTrackerDao
    abstract fun getProfileDao(): ProfileDao

    companion object {
        @Volatile
        private var Instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            // if the Instance is not null, return it, otherwise create a new database instance.
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, AppDatabase::class.java, "database")
                    .build()
                    .also { Instance = it }
            }
        }
    }
}