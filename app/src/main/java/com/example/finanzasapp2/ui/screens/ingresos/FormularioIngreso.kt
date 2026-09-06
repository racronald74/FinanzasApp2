package com.example.finanzasapp2.ui.screens.ingresos

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.MaterialTheme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.text.SimpleDateFormat
import android.app.DatePickerDialog
import android.content.Context
import java.util.Calendar
import java.util.Locale
import com.example.finanzasapp2.domain.business.FinanzasRules

// Formulario utilizado para registrar o editar un ingreso.
@Composable
fun FormularioIngreso(
    ingresoEditar: com.example.finanzasapp2.data.model.Ingreso? = null,

    onGuardar: (
        monto: Double,
        fecha: String,
        categoria: String,
        descripcion: String
    ) -> Unit,

    onCancelar: () -> Unit
) {

    // Carga los datos existentes cuando se está editando un ingreso.
    var monto by remember {
        mutableStateOf(ingresoEditar?.monto?.toString() ?: "")
    }

    var fecha by remember {
        mutableStateOf(ingresoEditar?.fecha ?: "")
    }

    var categoria by remember {
        mutableStateOf(ingresoEditar?.categoria ?: "")
    }

    var descripcion by remember {
        mutableStateOf(ingresoEditar?.descripcion ?: "")
    }

    // Controla si se muestra la lista de categorías.
    var mostrarCategorias by remember {
        mutableStateOf(false)
    }

    // Controla si se muestra el calendario.
    var mostrarCalendario by remember {
        mutableStateOf(false)
    }

    // Guarda el mensaje de error que se mostrará al usuario.
    var mensajeError by remember {
        mutableStateOf("")
    }

    // Contexto necesario para mostrar el selector de fecha de Android.
    val contexto = androidx.compose.ui.platform.LocalContext.current

// Obtiene la fecha actual para inicializar el calendario.
    val calendario = Calendar.getInstance()

    // Categorías disponibles para los ingresos.
    val categorias = listOf(
        "Salario",
        "Freelance",
        "Negocio",
        "Inversiones",
        "Bonificación",
        "Otros"
    )

    AlertDialog(
        onDismissRequest = onCancelar,

        // Cambia el título dependiendo de si se crea o edita.
        title = {
            Text(
                if (ingresoEditar == null) {
                    "Registrar ingreso"
                } else {
                    "Editar ingreso"
                }
            )
        },

        text = {

            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                // Campo para indicar el monto del ingreso.
                OutlinedTextField(
                    value = monto,
                    onValueChange = {
                        monto = it
                    },
                    label = {
                        Text("Monto")
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                // Campo para seleccionar la categoría.
                Box(
                    modifier = Modifier.fillMaxWidth()
                ) {

                    OutlinedTextField(
                        value = categoria,
                        onValueChange = {},
                        readOnly = true,
                        label = {
                            Text("Categoría")
                        },
                        modifier = Modifier.fillMaxWidth()
                    )

                    // Permite pulsar sobre todo el campo.
                    Box(
                        modifier = Modifier
                            .matchParentSize()
                            .clickable {
                                mostrarCategorias = true
                            }
                    )

                    // Lista de categorías disponibles.
                    DropdownMenu(
                        expanded = mostrarCategorias,
                        onDismissRequest = {
                            mostrarCategorias = false
                        }
                    ) {

                        categorias.forEach { opcion ->

                            DropdownMenuItem(
                                text = {
                                    Text(opcion)
                                },
                                onClick = {
                                    categoria = opcion
                                    mostrarCategorias = false
                                }
                            )
                        }
                    }
                }

                // Campo para seleccionar la fecha.
                Box(
                    modifier = Modifier.fillMaxWidth()
                ) {

                    OutlinedTextField(
                        value = fecha,
                        onValueChange = {},
                        readOnly = true,
                        label = {
                            Text("Fecha")
                        },
                        modifier = Modifier.fillMaxWidth()
                    )

                    // Permite pulsar sobre todo el campo.
                    Box(
                        modifier = Modifier
                            .matchParentSize()
                            .clickable {
                                mostrarCalendario = true
                            }
                    )
                }

                // Campo para la descripción.
                OutlinedTextField(
                    value = descripcion,
                    onValueChange = {
                        descripcion = it
                    },
                    label = {
                        Text("Descripción (opcional)")
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                // Muestra el mensaje cuando algún dato no cumple las reglas.
                if (mensajeError.isNotBlank()) {

                    Text(
                        text = mensajeError,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        },

        // Botón para guardar los cambios o crear el ingreso.
        confirmButton = {

            TextButton(
                onClick = {

                    val valor = monto.toDoubleOrNull()

                    // Limpia el mensaje anterior.
                    mensajeError = ""

                    // Valida el monto utilizando las reglas del proyecto.
                    if (valor == null || !FinanzasRules.montoValido(valor)) {

                        mensajeError = "El monto debe ser mayor a 0."

                        // Valida que la categoría no esté vacía.
                    } else if (!FinanzasRules.categoriaValida(categoria)) {

                        mensajeError = "Debe seleccionar una categoría."

                        // Valida que la descripción no supere los 200 caracteres.
                    } else if (!FinanzasRules.descripcionValida(descripcion)) {

                        mensajeError = "La descripción no puede superar los 200 caracteres."

                        // Valida que exista una fecha.
                    } else if (fecha.isBlank()) {

                        mensajeError = "Debe seleccionar una fecha."

                    } else {

                        // Si todas las validaciones son correctas,
                        // se envían los datos para guardar o actualizar.
                        onGuardar(
                            valor,
                            fecha,
                            categoria,
                            descripcion
                        )
                    }
                }
            ) {
                Text(
                    if (ingresoEditar == null) {
                        "Guardar"
                    } else {
                        "Actualizar"
                    }
                )
            }
        },

        // Botón para cancelar.
        dismissButton = {

            TextButton(
                onClick = onCancelar
            ) {
                Text("Cancelar")
            }
        }
    )

    // Calendario para seleccionar la fecha.
    if (mostrarCalendario) {

        DatePickerDialog(
            contexto,
            { _, año, mes, dia ->

                // Construye la fecha seleccionada.
                val fechaSeleccionada = Calendar.getInstance().apply {
                    set(año, mes, dia)
                }

                // Formato utilizado por la aplicación.
                val formato = SimpleDateFormat(
                    "dd/MM/yyyy",
                    Locale.getDefault()
                )

                // Actualiza la fecha del formulario.
                fecha = formato.format(
                    fechaSeleccionada.time
                )

                // Cierra el calendario.
                mostrarCalendario = false
            },
            calendario.get(Calendar.YEAR),
            calendario.get(Calendar.MONTH),
            calendario.get(Calendar.DAY_OF_MONTH)
        ).show()
    }
}