package com.example.diariodeplantas.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.diariodeplantas.data.remote.ApiUiState
import com.example.diariodeplantas.data.remote.PlantApiItem
import com.example.diariodeplantas.data.remote.PlantApiService
import kotlinx.coroutines.launch

class ApiPlantViewModel : ViewModel() {

    var uiState: ApiUiState by mutableStateOf(ApiUiState.Loading)
        private set

    init {
        obtenerCatalogoRemoto()
    }

    fun obtenerCatalogoRemoto() {
        viewModelScope.launch {
            uiState = ApiUiState.Loading
            uiState = try {
                val listResult = PlantApiService.create().getPlantCatalog()
                if (listResult.isNotEmpty()) {
                    ApiUiState.Success(listResult)
                } else {
                    ApiUiState.Success(obtenerDatosRespaldo())
                }
            } catch (e: Exception) {
                // Si la red del emulador falla o bloquea la petición, muestra el catálogo de respaldo
                ApiUiState.Success(obtenerDatosRespaldo())
            }
        }
    }

    private fun obtenerDatosRespaldo(): List<PlantApiItem> {
        return listOf(
            PlantApiItem(
                id = 1,
                name = "Lengua de suegra",
                scientificName = "Dracaena trifasciata",
                category = "Interior",
                waterRequirement = "1 por semana"
            ),
            PlantApiItem(
                id = 2,
                name = "Monstera Deliciosa",
                scientificName = "Monstera deliciosa",
                category = "Interior",
                waterRequirement = "Cada 10 días"
            ),
            PlantApiItem(
                id = 3,
                name = "Cactus San Pedro",
                scientificName = "Echinopsis pachanoi",
                category = "Exterior",
                waterRequirement = "Cada 15 días"
            )
        )
    }
}