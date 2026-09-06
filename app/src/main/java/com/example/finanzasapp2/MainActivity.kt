// Permite utilizar las APIs experimentales de Material 3 en todo este archivo.
@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)


package com.example.finanzasapp2

import android.os.Bundle

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.background
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add

import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.Payments

import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.AlertDialog
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.NavigationBarItemDefaults

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight

import com.example.finanzasapp2.ui.screens.SplashScreen
import androidx.room.Room

import com.example.finanzasapp2.data.local.FinanzasDatabase
import com.example.finanzasapp2.data.model.Gasto
import com.example.finanzasapp2.data.model.Ingreso
import com.example.finanzasapp2.data.repository.FinanzasRepository
import com.example.finanzasapp2.ui.screens.gastos.FormularioGasto
import com.example.finanzasapp2.ui.screens.ingresos.FormularioIngreso
import com.example.finanzasapp2.ui.theme.FinanzasApp2Theme
import com.example.finanzasapp2.viewmodel.FinanzasViewModel
import com.example.finanzasapp2.ui.components.HeaderFinanzas
import java.text.NumberFormat
import java.util.Locale

enum class Pantalla {
    INGRESOS,
    GASTOS
}

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.statusBarColor = android.graphics.Color.rgb(70, 118, 184)
        window.navigationBarColor = android.graphics.Color.BLACK

        enableEdgeToEdge()

        setContent {

            var mostrarSplash by remember {
                mutableStateOf(true)
            }

            if (mostrarSplash) {

                SplashScreen(
                    onFinished = {
                        mostrarSplash = false
                    }
                )

            } else {

                FinanzasApp()

            }
        }
    }
}

@Composable
fun FinanzasApp() {

    val context = LocalContext.current

    val database = remember {
        Room.databaseBuilder(
            context,
            FinanzasDatabase::class.java,
            "finanzas_database"
        ).build()
    }

    val repository = remember {
        FinanzasRepository(
            database.ingresoDao(),
            database.gastoDao()
        )
    }

    val viewModel = remember {
        FinanzasViewModel(repository)
    }

    // Observa los ingresos almacenados en Room.
// La interfaz se actualiza automáticamente cuando cambian.
    val ingresos by viewModel.ingresos.collectAsState()

// Observa los gastos almacenados en Room.
// La interfaz se actualiza automáticamente cuando cambian.
    val gastos by viewModel.gastos.collectAsState()

    var pantallaActual by remember {
        mutableStateOf(Pantalla.INGRESOS)
    }

    var mostrarFormularioIngreso by remember {
        mutableStateOf(false)
    }

    var mostrarFormularioGasto by remember {
        mutableStateOf(false)
    }

    // Guarda el gasto que actualmente se está editando.
// Si es null, no hay ningún gasto seleccionado para editar.
    var gastoEditando by remember {
        mutableStateOf<Gasto?>(null)
    }

    // Guarda el ingreso que actualmente se está editando.
// Si es null, no hay ningún ingreso seleccionado para editar.
    var ingresoEditando by remember {
        mutableStateOf<Ingreso?>(null)
    }

    // Guarda el gasto que el usuario seleccionó para eliminar.
// Si es null, no hay ningún gasto pendiente de confirmación.
    var gastoEliminar by remember {
        mutableStateOf<Gasto?>(null)
    }

// Guarda el ingreso que el usuario seleccionó para eliminar.
// Si es null, no hay ningún ingreso pendiente de confirmación.
    var ingresoEliminar by remember {
        mutableStateOf<Ingreso?>(null)
    }

    val totalIngresos = ingresos.sumOf { it.monto }
    val totalGastos = gastos.sumOf { it.monto }
    val saldoDisponible = totalIngresos - totalGastos

    Scaffold(
        modifier = Modifier.fillMaxSize(),

        bottomBar = {

            NavigationBar(
                containerColor = Color(0xFFF7F5FC),
                tonalElevation = 0.dp
            ) {

                NavigationBarItem(
                    selected = pantallaActual == Pantalla.INGRESOS,
                    onClick = {
                        pantallaActual = Pantalla.INGRESOS
                    },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Payments,
                            contentDescription = "Ingresos",
                            modifier = Modifier.size(30.dp)
                        )
                    },
                    label = {
                        Text(
                            text = "Ingresos",
                            fontSize = 16.sp
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color(0xFF4CAF50),
                        selectedTextColor = Color(0xFF4CAF50),
                        indicatorColor = Color.Transparent
                    )
                )

                NavigationBarItem(
                    selected = pantallaActual == Pantalla.GASTOS,
                    onClick = {
                        pantallaActual = Pantalla.GASTOS
                    },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.AccountBalanceWallet,
                            contentDescription = "Gastos",
                            modifier = Modifier.size(30.dp)
                        )
                    },
                    label = {
                        Text(
                            text = "Gastos",
                            fontSize = 16.sp
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color(0xFFF44336),
                        selectedTextColor = Color(0xFFF44336),
                        indicatorColor = Color.Transparent
                    )
                )
            }
        }
    ) { paddingValues ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when (pantallaActual) {

                Pantalla.INGRESOS -> {

                    PantallaIngresos(
                        ingresos = ingresos,
                        totalIngresos = totalIngresos,
                        saldoDisponible = saldoDisponible,

                        // Abre el formulario vacío para registrar un nuevo ingreso.
                        onAgregar = {
                            ingresoEditando = null
                            mostrarFormularioIngreso = true
                        },

                        onEliminar = { id ->

                            // Busca el ingreso seleccionado.
                            ingresoEliminar = ingresos.find { it.id == id }
                        },

                        // Selecciona el ingreso que se desea editar.
                        onEditar = { id ->

                            // Busca el ingreso seleccionado.
                            ingresoEditando = ingresos.find { it.id == id }

                            // Abre el formulario de edición.
                            mostrarFormularioIngreso = true
                        }
                    )
                }

                Pantalla.GASTOS -> {

                    PantallaGastos(
                        gastos = gastos,
                        totalGastos = totalGastos,
                        saldoDisponible = saldoDisponible,

                        // Abre el formulario vacío para registrar un nuevo gasto.
                        onAgregar = {
                            gastoEditando = null
                            mostrarFormularioGasto = true
                        },

                        // Elimina el gasto seleccionado de la base de datos.
                        onEliminar = { id ->

                            // Busca el gasto seleccionado.
                            gastoEliminar = gastos.find { it.id == id }
                        },

                        // Selecciona el gasto que se desea editar.
                        onEditar = { id ->

                            // Busca el gasto seleccionado.
                            gastoEditando = gastos.find { it.id == id }

                            // Abre el formulario de edición.
                            mostrarFormularioGasto = true
                        }
                    )
                }
            }
        }
    }

    // Muestra el formulario para registrar o editar un ingreso.
    if (mostrarFormularioIngreso) {

        FormularioIngreso(
            ingresoEditar = ingresoEditando,

            onGuardar = { monto, fecha, categoria, descripcion ->

                // Si no hay un ingreso seleccionado, se crea uno nuevo.
                if (ingresoEditando == null) {

                    viewModel.insertarIngreso(
                        Ingreso(
                            monto = monto,
                            fecha = fecha,
                            categoria = categoria,
                            descripcion = descripcion
                        )
                    )

                } else {

                    // Si existe un ingreso seleccionado, se actualizan sus datos.
                    viewModel.actualizarIngreso(
                        ingresoEditando!!.copy(
                            monto = monto,
                            fecha = fecha,
                            categoria = categoria,
                            descripcion = descripcion
                        )
                    )
                }

                // Limpia el ingreso seleccionado.
                ingresoEditando = null

                // Cierra el formulario.
                mostrarFormularioIngreso = false
            },

            onCancelar = {

                // Limpia el ingreso seleccionado.
                ingresoEditando = null

                // Cierra el formulario.
                mostrarFormularioIngreso = false
            }
        )
    }

    // Muestra el formulario para registrar un nuevo gasto.
    if (mostrarFormularioGasto) {

        FormularioGasto(
            gastoEditar = gastoEditando,

            onGuardar = { nombre, monto, categoria, fecha, descripcion ->

                if (gastoEditando == null) {

                    // Registra un nuevo gasto.
                    viewModel.insertarGasto(
                        Gasto(
                            nombre = nombre,
                            monto = monto,
                            categoria = categoria,
                            fecha = fecha,
                            descripcion = descripcion
                        )
                    )

                } else {

                    // Actualiza el gasto existente conservando su ID.
                    viewModel.actualizarGasto(
                        gastoEditando!!.copy(
                            nombre = nombre,
                            monto = monto,
                            categoria = categoria,
                            fecha = fecha,
                            descripcion = descripcion
                        )
                    )
                }

                // Cierra el formulario después de guardar o actualizar.
                mostrarFormularioGasto = false

                // Limpia el gasto seleccionado.
                gastoEditando = null
            },

            // Cierra el formulario sin guardar cambios.
            onCancelar = {
                mostrarFormularioGasto = false
                gastoEditando = null
            }
        )
    }

    // Muestra la confirmación antes de eliminar un ingreso.
    if (ingresoEliminar != null) {

        AlertDialog(
            onDismissRequest = {
                ingresoEliminar = null
            },

            title = {
                Text("Eliminar ingreso")
            },

            text = {
                Text("¿Está seguro de que desea eliminar este ingreso?")
            },

            confirmButton = {

                TextButton(
                    onClick = {

                        // Elimina el ingreso después de confirmar.
                        ingresoEliminar?.let { ingreso ->
                            viewModel.eliminarIngreso(ingreso)
                        }

                        // Cierra el cuadro de confirmación.
                        ingresoEliminar = null
                    }
                ) {
                    Text("Eliminar")
                }
            },

            dismissButton = {

                TextButton(
                    onClick = {
                        ingresoEliminar = null
                    }
                ) {
                    Text("Cancelar")
                }
            }
        )
    }

// Muestra la confirmación antes de eliminar un gasto.
    if (gastoEliminar != null) {

        AlertDialog(
            onDismissRequest = {
                gastoEliminar = null
            },

            title = {
                Text("Eliminar gasto")
            },

            text = {
                Text("¿Está seguro de que desea eliminar este gasto?")
            },

            confirmButton = {

                TextButton(
                    onClick = {

                        // Elimina el gasto después de confirmar.
                        gastoEliminar?.let { gasto ->
                            viewModel.eliminarGasto(gasto)
                        }

                        // Cierra el cuadro de confirmación.
                        gastoEliminar = null
                    }
                ) {
                    Text("Eliminar")
                }
            },

            dismissButton = {

                TextButton(
                    onClick = {
                        gastoEliminar = null
                    }
                ) {
                    Text("Cancelar")
                }
            }
        )
    }
}

@Composable
fun PantallaIngresos(
    ingresos: List<Ingreso>,
    totalIngresos: Double,
    saldoDisponible: Double,
    onAgregar: () -> Unit,
    onEliminar: (Int) -> Unit,
    onEditar: (Int) -> Unit
) {

    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        Image(
            painter = painterResource(
                id = R.drawable.fondo_splash
            ),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier.fillMaxSize()
        ) {

        HeaderFinanzas(
            titulo = "Ingresos"
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 3.dp
            ),
            border = BorderStroke(
                1.dp,
                Color(0xFFE2E2E2)
            )
        ) {

                Column(
                    modifier = Modifier.padding(16.dp)
                ) {

                    Text(
                        text = "Resumen de ingresos",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(
                        modifier = Modifier.height(12.dp)
                    )

                    Text(
                        text = "Total ingresos"
                    )

                    Text(
                        text = formatearDinero(totalIngresos),
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF4CAF50)
                    )

                    Spacer(
                        modifier = Modifier.height(8.dp)
                    )

                    Text(
                        text = "Saldo disponible"
                    )

                    Text(
                        text = formatearDinero(saldoDisponible),
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF4CAF50)
                    )
                }
            }

            Spacer(
                modifier = Modifier.height(15.dp)
            )

            Text(
                text = "Historial de ingresos",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 12.dp)
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            if (ingresos.isEmpty()) {

                Text(
                    text = "No hay ingresos registrados."
                )

            } else {

                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    items(ingresos) { ingreso ->

                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 12.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            elevation = CardDefaults.cardElevation(
                                defaultElevation = 3.dp
                            ),
                            border = BorderStroke(
                                1.dp,
                                Color(0xFFE2E2E2)
                            )
                        ) {

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {

                                Column(
                                    modifier = Modifier.weight(1f)
                                ) {

                                    Text(
                                        text = ingreso.categoria,
                                        style =
                                            MaterialTheme.typography.titleMedium
                                    )

                                    Text(
                                        text = ingreso.descripcion.ifBlank {
                                            "Sin descripción"
                                        }
                                    )

                                    Text(
                                        text = ingreso.fecha
                                    )

                                    Text(
                                        text =
                                            formatearDinero(ingreso.monto),
                                        style =
                                            MaterialTheme.typography.titleMedium
                                    )
                                }

                                Column {

                                    // Botón para eliminar el ingreso.
                                    IconButton(
                                        onClick = {
                                            onEliminar(ingreso.id)
                                        }
                                    ) {

                                        Icon(
                                            imageVector = Icons.Default.Delete,
                                            contentDescription = "Eliminar"
                                        )
                                    }

                                    // Botón para editar el ingreso.
                                    IconButton(
                                        onClick = {
                                            onEditar(ingreso.id)
                                        }
                                    ) {

                                        Icon(
                                            imageVector = Icons.Default.Edit,
                                            contentDescription = "Editar"
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

        Button(
            onClick = onAgregar,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .padding(horizontal = 12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(
                    red = 70,
                    green = 118,
                    blue = 184
                )
            )
        ) {

                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null
                )

                Spacer(
                    modifier = Modifier.padding(4.dp)
                )

                Text("Registrar nuevo ingreso")
            }
            Spacer(
                modifier = Modifier.height(12.dp)
            )
        }
        }
    }

@Composable
fun PantallaGastos(
    gastos: List<Gasto>,
    totalGastos: Double,
    saldoDisponible: Double,
    onAgregar: () -> Unit,
    onEliminar: (Int) -> Unit,
    onEditar: (Int) -> Unit
) {

    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        Image(
            painter = painterResource(
                id = R.drawable.fondo_splash
            ),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier.fillMaxSize()
        ) {

            HeaderFinanzas(
                titulo = "Gastos"
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 3.dp
                ),
                border = BorderStroke(
                    1.dp,
                    Color(0xFFE2E2E2)
                )
            ) {

                Column(
                    modifier = Modifier.padding(16.dp)
                ) {

                    Text(
                        text = "Resumen de gastos",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(
                        modifier = Modifier.height(12.dp)
                    )

                    Text("Total gastado")

                    Text(
                        text = formatearDinero(totalGastos),
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFF44336)
                    )

                    Spacer(
                        modifier = Modifier.height(8.dp)
                    )

                    Text("Disponible")

                    Text(
                        text = formatearDinero(saldoDisponible),
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF4CAF50)
                    )
                }
            }

            Spacer(
                modifier = Modifier.height(15.dp)
            )

            Text(
                text = "Lista de gastos",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 12.dp)
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            if (gastos.isEmpty()) {

                Text(
                    text = "No hay gastos registrados."
                )

            } else {

                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    items(gastos) { gasto ->

                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 12.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            elevation = CardDefaults.cardElevation(
                                defaultElevation = 3.dp
                            ),
                            border = BorderStroke(
                                1.dp,
                                Color(0xFFE2E2E2)
                            )
                        ) {

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {

                                Column(
                                    modifier = Modifier.weight(1f)
                                ) {

                                    Text(
                                        text = gasto.nombre,
                                        style =
                                            MaterialTheme.typography.titleMedium
                                    )

                                    Text(
                                        text = gasto.descripcion.ifBlank {
                                            "Sin descripción"
                                        }
                                    )

                                    Text(
                                        text = gasto.categoria
                                    )

                                    Text(
                                        text = gasto.fecha
                                    )

                                    Text(
                                        text =
                                            formatearDinero(gasto.monto),
                                        style =
                                            MaterialTheme.typography.titleMedium
                                    )
                                }

                                Column {

                                    // Botón para eliminar el gasto.
                                    IconButton(
                                        onClick = {
                                            onEliminar(gasto.id)
                                        }
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Delete,
                                            contentDescription = "Eliminar"
                                        )
                                    }

                                    // Botón para editar el gasto.
                                    IconButton(
                                        onClick = {
                                            onEditar(gasto.id)
                                        }
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Edit,
                                            contentDescription = "Editar"
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            Button(
                onClick = onAgregar,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .padding(horizontal = 12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(
                        red = 70,
                        green = 118,
                        blue = 184
                    )
                )
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null
                )

                Spacer(
                    modifier = Modifier.padding(4.dp)
                )

                Text("Registrar nuevo gasto")
            }
            Spacer(
                modifier = Modifier.height(12.dp)
            )
        }
    }
    }

fun formatearDinero(valor: Double): String {

    val formato =
        NumberFormat.getNumberInstance(Locale("es", "CO"))

    formato.maximumFractionDigits = 0

    return "$${formato.format(valor)}"
}