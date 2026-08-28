package com.example.diariodeplantas.data.remote

import com.google.gson.annotations.SerializedName
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

data class PlantApiItem(
    @SerializedName("id") val id: Int? = 0,
    @SerializedName("nombreComun") val name: String? = "",
    @SerializedName("nombreCientifico") val scientificName: String? = "",
    @SerializedName("tipo") val category: String? = "",
    @SerializedName("frecuenciaRiego") val waterRequirement: String? = "",
    @SerializedName("imagenUrl") val imageUrl: String? = ""
)

sealed interface ApiUiState {
    data class Success(val plants: List<PlantApiItem>) : ApiUiState
    object Error : ApiUiState
    object Loading : ApiUiState
}

interface PlantApiService {
    @GET("maestro/plantas.json")
    suspend fun getPlantCatalog(): List<PlantApiItem>

    companion object {
        private const val BASE_URL = "https://raw.githubusercontent.com/mayrafarias1986-tech/DiarioDePlantas/"

        fun create(): PlantApiService {
            val client = OkHttpClient.Builder()
                .addInterceptor { chain ->
                    val request = chain.request().newBuilder()
                        .addHeader("User-Agent", "Mozilla/5.0")
                        .build()
                    chain.proceed(request)
                }
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(PlantApiService::class.java)
        }
    }
}