package com.example.finanzasapp2.ui.screens.gastos

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add

import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

import com.example.finanzasapp2.R
import com.example.finanzasapp2.data.model.Gasto
import com.example.finanzasapp2.ui.components.EmptyState
import com.example.finanzasapp2.ui.components.HeaderFinanzas
import com.example.finanzasapp2.formatearDinero

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
                    EmptyState(
                        titulo = "No hay gastos registrados",
                        mensaje = "Registra tu primer gasto para comenzar a controlar tus finanzas."
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