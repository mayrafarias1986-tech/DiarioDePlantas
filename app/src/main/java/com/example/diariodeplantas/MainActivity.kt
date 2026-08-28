package com.example.diariodeplantas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.diariodeplantas.data.local.PlantaDatabase
import com.example.diariodeplantas.data.local.PlantaRepository
import com.example.diariodeplantas.data.local.UserPreferencesRepository
import com.example.diariodeplantas.ui.PlantaViewModel
import com.example.diariodeplantas.ui.SettingsScreen
import com.example.diariodeplantas.ui.SettingsViewModel
import com.example.diariodeplantas.ui.screens.AddPlantaScreen
import com.example.diariodeplantas.ui.screens.ApiCatalogScreen
import com.example.diariodeplantas.ui.screens.HomeScreen
import com.example.diariodeplantas.ui.theme.DiarioDePlantasTheme
import com.example.diariodeplantas.ui.viewmodel.ApiPlantViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = PlantaDatabase.getDatabase(this)
        val repository = PlantaRepository(database.plantaDao())
        val userPreferencesRepository = UserPreferencesRepository(this)

        setContent {
            val settingsViewModel: SettingsViewModel = viewModel(
                factory = object : ViewModelProvider.Factory {
                    override fun <T : ViewModel> create(modelClass: Class<T>): T {
                        return SettingsViewModel(userPreferencesRepository) as T
                    }
                }
            )
            val isDarkTheme by settingsViewModel.isDarkMode.collectAsState(initial = false)

            DiarioDePlantasTheme(darkTheme = isDarkTheme) {
                val navController = rememberNavController()

                val plantaViewModel: PlantaViewModel = viewModel(
                    factory = object : ViewModelProvider.Factory {
                        override fun <T : ViewModel> create(modelClass: Class<T>): T {
                            return PlantaViewModel(repository) as T
                        }
                    }
                )
                val apiPlantViewModel: ApiPlantViewModel = viewModel()

                NavHost(navController = navController, startDestination = "home") {
                    composable("home") {
                        HomeScreen(
                            viewModel = plantaViewModel,
                            onNavegarAgregar = { navController.navigate("add") },
                            onNavegarAjustes = { navController.navigate("settings") },
                            onNavegarApi = { navController.navigate("apiCatalog") }
                        )
                    }
                    composable("add") {
                        AddPlantaScreen(
                            viewModel = plantaViewModel,
                            onVolver = { navController.popBackStack() }
                        )
                    }
                    composable("settings") {
                        SettingsScreen(
                            isDarkMode = isDarkTheme,
                            onDarkModeChanged = { enabled -> settingsViewModel.toggleDarkMode(enabled) },
                            onBackClick = { navController.popBackStack() }
                        )
                    }
                    composable("apiCatalog") {
                        ApiCatalogScreen(
                            viewModel = apiPlantViewModel,
                            onVolver = { navController.popBackStack() }
                        )
                    }
                }
            }
        }
    }
}