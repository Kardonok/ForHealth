package com.example.forhealth.data.repositories


import com.example.forhealth.data.models.WaterItem
import com.example.forhealth.data.sources.WaterDao
import kotlinx.coroutines.flow.Flow

interface WaterRepository
{
    suspend fun insertWaterItem(waterItem: WaterItem)


    suspend fun deleteWaterItem(waterItem: WaterItem)


    suspend fun updateWaterItem(waterItem: WaterItem)

    fun getAllWaterItems(): Flow<List<WaterItem>>
}


/**
Репозиторий для работы с данными из локальной таблицы воды
 */
class OfflineWaterRepository(private val waterDao: WaterDao):WaterRepository {
    override suspend fun insertWaterItem(waterItem: WaterItem) {
        waterDao.insert(waterItem)
    }

    override suspend fun deleteWaterItem(waterItem: WaterItem) {
        waterDao.delete(waterItem)
    }

    override suspend fun updateWaterItem(waterItem: WaterItem) {
        waterDao.update(waterItem)
    }

    override fun getAllWaterItems(): Flow<List<WaterItem>> {
        return waterDao.getAllWaterItems()
    }

}