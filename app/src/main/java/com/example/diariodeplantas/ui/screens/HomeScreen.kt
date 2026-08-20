package com.example.diariodeplantas.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.diariodeplantas.data.local.PlantaEntity
import com.example.diariodeplantas.ui.PlantaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: PlantaViewModel,
    onNavegarAgregar: () -> Unit,
    onNavegarAjustes: () -> Unit,
    onNavegarApi: () -> Unit
) {
    val plantas by viewModel.plantas.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mi Diario de Plantas") },
                actions = {
                    IconButton(onClick = onNavegarApi) {
                        Icon(Icons.Default.Info, contentDescription = "Catálogo Remoto")
                    }
                    IconButton(onClick = onNavegarAjustes) {
                        Icon(Icons.Default.Settings, contentDescription = "Ajustes")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onNavegarAgregar) {
                Icon(Icons.Default.Add, contentDescription = "Agregar Planta")
            }
        }
    ) { paddingValues ->
        if (plantas.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Aún no has agregado ninguna planta 🌱",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(plantas) { planta ->
                    PlantaItem(
                        planta = planta,
                        onEliminar = { viewModel.eliminarPlanta(planta) }
                    )
                }
            }
        }
    }
}

@Composable
fun PlantaItem(
    planta: PlantaEntity,
    onEliminar: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = planta.fotoUri ?: "https://via.placeholder.com/150",
                contentDescription = planta.nombre,
                modifier = Modifier
                    .size(80.dp)
                    .clip(MaterialTheme.shapes.medium),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = planta.nombre,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = planta.nombreCientifico,
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Tipo: ${planta.tipo}",
                    style = MaterialTheme.typography.labelSmall
                )
            }

            IconButton(onClick = onEliminar) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Eliminar",
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}