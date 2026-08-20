package com.example.diariodeplantas.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [PlantaEntity::class], version = 1, exportSchema = false)
abstract class PlantaDatabase : RoomDatabase() {

    abstract fun plantaDao(): PlantaDao

    companion object {
        @Volatile
        private var INSTANCE: PlantaDatabase? = null

        fun getDatabase(context: Context): PlantaDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PlantaDatabase::class.java,
                    "planta_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}