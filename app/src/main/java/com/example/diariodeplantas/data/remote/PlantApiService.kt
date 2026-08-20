
package com.example.diariodeplantas.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

// Modelo de datos para la API
data class PlantApiItem(
    val id: Int,
    val name: String,
    val category: String,
    val waterRequirement: String
)

// Estados de la UI para manejar Carga, Éxito y Error
sealed interface ApiUiState {
    data class Success(val plants: List<PlantApiItem>) : ApiUiState
    object Error : ApiUiState
    object Loading : ApiUiState
}

// Interfaz de Retrofit
interface PlantApiService {
    @GET("plants.json")
    suspend fun getPlantCatalog(): List<PlantApiItem>

    companion object {
        private const val BASE_URL = "https://raw.githubusercontent.com/example/"

        fun create(): PlantApiService {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(PlantApiService::class.java)
        }
    }
}