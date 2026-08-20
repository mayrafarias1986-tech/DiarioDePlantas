package com.example.diariodeplantas.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PlantaDao {
    @Query("SELECT * FROM plantas ORDER BY id DESC")
    fun getAllPlantas(): Flow<List<PlantaEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlanta(planta: PlantaEntity)

    @Delete
    suspend fun deletePlanta(planta: PlantaEntity)
}
