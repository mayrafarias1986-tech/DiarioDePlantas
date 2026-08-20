package com.example.diariodeplantas.ui.screens

import android.Manifest
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.diariodeplantas.data.local.PlantaEntity
import com.example.diariodeplantas.ui.PlantaViewModel
import com.google.android.gms.location.LocationServices

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPlantaScreen(
    viewModel: PlantaViewModel,
    onVolver: () -> Unit
) {
    var nombre by remember { mutableStateOf("") }
    var nombreCientifico by remember { mutableStateOf("") }
    var tipo by remember { mutableStateOf("") }
    var fechaRiego by remember { mutableStateOf("") }
    var fotoUri by remember { mutableStateOf<Uri?>(null) }
    var latitud by remember { mutableStateOf<Double?>(null) }
    var longitud by remember { mutableStateOf<Double?>(null) }

    val context = LocalContext.current
    val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? -> fotoUri = uri }

    val locationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            try {
                fusedLocationClient.lastLocation.addOnSuccessListener { loc ->
                    loc?.let {
                        latitud = it.latitude
                        longitud = it.longitude
                    }
                }
            } catch (e: SecurityException) { e.printStackTrace() }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Nueva Planta") },
                navigationIcon = {
                    IconButton(onClick = onVolver) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre común (ej: Monstera)") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = nombreCientifico,
                onValueChange = { nombreCientifico = it },
                label = { Text("Nombre científico") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = tipo,
                onValueChange = { tipo = it },
                label = { Text("Tipo (Interior, Exterior, Cactus...)") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = fechaRiego,
                onValueChange = { fechaRiego = it },
                label = { Text("Frecuencia de riego") },
                modifier = Modifier.fillMaxWidth()
            )

            fotoUri?.let { uri ->
                AsyncImage(
                    model = uri,
                    contentDescription = "Foto seleccionada",
                    modifier = Modifier.fillMaxWidth().height(180.dp)
                )
            }

            Button(
                onClick = { galleryLauncher.launch("image/*") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (fotoUri == null) "Seleccionar Foto" else "Cambiar Foto")
            }

            OutlinedButton(
                onClick = {
                    locationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (latitud == null) "Capturar Ubicación GPS" else "GPS: $latitud, $longitud")
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = {
                    if (nombre.isNotBlank()) {
                        viewModel.agregarPlanta(
                            PlantaEntity(
                                nombre = nombre,
                                nombreCientifico = nombreCientifico,
                                tipo = tipo,
                                fechaRiego = fechaRiego,
                                fotoUri = fotoUri?.toString(),
                                latitud = latitud,
                                longitud = longitud
                            )
                        )
                        onVolver()
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = nombre.isNotBlank()
            ) {
                Text("Guardar Planta")
            }
        }
    }
}