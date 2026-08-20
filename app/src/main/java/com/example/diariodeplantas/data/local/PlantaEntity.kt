package com.example.diariodeplantas.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "plantas")
data class PlantaEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nombre: String,
    val nombreCientifico: String,
    val tipo: String, // Interior, Exterior, Cactus, etc.
    val fechaRiego: String,
    val fotoUri: String? = null,
    val latitud: Double? = null,
    val longitud: Double? = null
)
