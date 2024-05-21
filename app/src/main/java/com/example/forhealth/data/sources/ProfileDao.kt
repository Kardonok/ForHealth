package com.example.forhealth.data.sources

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.forhealth.data.models.ProfileItem
import kotlinx.coroutines.flow.Flow

@Dao
interface ProfileDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: ProfileItem)

    @Delete
    suspend fun delete(item: ProfileItem)

    @Update
    suspend fun update(item: ProfileItem)

    @Query("SELECT * FROM profile_list")
    fun getProfile(): Flow<ProfileItem>
}
