package com.example.diariodeplantas.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.diariodeplantas.data.local.PlantaRepository
import com.example.diariodeplantas.data.local.PlantaEntity
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class PlantaViewModel(private val repository: PlantaRepository) : ViewModel() {

    // Convierte el Flow en un StateFlow optimizado para Jetpack Compose
    val plantas: StateFlow<List<PlantaEntity>> = repository.allPlantas
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun agregarPlanta(planta: PlantaEntity) {
        viewModelScope.launch {
            repository.insert(planta)
        }
    }

    fun eliminarPlanta(planta: PlantaEntity) {
        viewModelScope.launch {
            repository.delete(planta)
        }
    }
}