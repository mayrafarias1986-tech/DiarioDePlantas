package com.example.diariodeplantas.data.local

import com.example.diariodeplantas.data.local.PlantaDao
import com.example.diariodeplantas.data.local.PlantaEntity
import kotlinx.coroutines.flow.Flow

class PlantaRepository(private val plantaDao: PlantaDao) {
    val allPlantas: Flow<List<PlantaEntity>> = plantaDao.getAllPlantas()

    suspend fun insert(planta: PlantaEntity) {
        plantaDao.insertPlanta(planta)
    }

    suspend fun delete(planta: PlantaEntity) {
        plantaDao.deletePlanta(planta)
    }
}