package com.example.forhealth.data.repositories

import com.example.forhealth.data.models.HabitTrackerItem
import com.example.forhealth.data.models.ProfileItem
import com.example.forhealth.data.sources.ProfileDao
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {

    suspend fun insertProfile(profileItem: ProfileItem)

    suspend fun deleteProfile(profileItem: ProfileItem)

    suspend fun updateProfile(profileItem: ProfileItem)

    fun getProfile(): Flow<ProfileItem>

}

class OfflineProfileRepository(private val profileDao: ProfileDao):ProfileRepository
{
    override suspend fun insertProfile(profileItem: ProfileItem) {
        profileDao.insert(profileItem)
    }

    override suspend fun deleteProfile(profileItem: ProfileItem) {
        profileDao.delete(profileItem)
    }

    override suspend fun updateProfile(profileItem: ProfileItem) {
        profileDao.update(profileItem)
    }

    override fun getProfile(): Flow<ProfileItem> {
        return profileDao.getProfile()
    }
}