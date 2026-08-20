package com.example.diariodeplantas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.diariodeplantas.data.local.PlantaDatabase
import com.example.diariodeplantas.data.local.PlantaRepository
import com.example.diariodeplantas.data.local.UserPreferencesRepository
import com.example.diariodeplantas.ui.ApiPlantViewModel
import com.example.diariodeplantas.ui.PlantaViewModel
import com.example.diariodeplantas.ui.SettingsScreen
import com.example.diariodeplantas.ui.SettingsViewModel
import com.example.diariodeplantas.ui.screens.AddPlantaScreen
import com.example.diariodeplantas.ui.screens.ApiCatalogScreen
import com.example.diariodeplantas.ui.screens.HomeScreen
import com.example.diariodeplantas.ui.theme.DiarioDePlantasTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = PlantaDatabase.getDatabase(this)
        val plantaRepo = PlantaRepository(database.plantaDao())
        val userPrefRepo = UserPreferencesRepository(this)

        setContent {
            val settingsViewModel = SettingsViewModel(userPrefRepo)
            val isDarkMode by settingsViewModel.isDarkMode.collectAsState()

            val plantaViewModel = PlantaViewModel(plantaRepo)
            val apiViewModel: ApiPlantViewModel = viewModel()

            DiarioDePlantasTheme(darkTheme = isDarkMode) {
                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = "home") {
                    composable(route = "home") {
                        HomeScreen(
                            viewModel = plantaViewModel,
                            onNavegarAgregar = { navController.navigate("add") },
                            onNavegarAjustes = { navController.navigate("settings") },
                            onNavegarApi = { navController.navigate("api") }
                        )
                    }
                    composable(route = "add") {
                        AddPlantaScreen(
                            viewModel = plantaViewModel,
                            onVolver = { navController.popBackStack() }
                        )
                    }
                    composable(route = "settings") {
                        SettingsScreen(
                            isDarkMode = isDarkMode,
                            onDarkModeChanged = { settingsViewModel.toggleDarkMode(it) },
                            onBackClick = { navController.popBackStack() }
                        )
                    }
                    composable(route = "api") {
                        ApiCatalogScreen(
                            viewModel = apiViewModel,
                            onVolver = { navController.popBackStack() }
                        )
                    }
                }
            }
        }
    }
}