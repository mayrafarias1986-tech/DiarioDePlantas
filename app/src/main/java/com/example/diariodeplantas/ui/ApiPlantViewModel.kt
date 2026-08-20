package com.example.diariodeplantas.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.diariodeplantas.data.remote.ApiUiState
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
                val api = PlantApiService.create()
                val lista = api.getPlantCatalog()
                ApiUiState.Success(lista)
            } catch (e: Exception) {
                ApiUiState.Error
            }
        }
    }
}