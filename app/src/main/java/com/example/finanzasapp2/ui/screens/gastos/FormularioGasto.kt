package com.example.finanzasapp2.ui.screens.gastos

import android.app.DatePickerDialog

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

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext

import com.example.finanzasapp2.domain.business.FinanzasRules
import com.example.finanzasapp2.data.model.Gasto
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

// Formulario utilizado para registrar o editar un gasto.
// Formulario utilizado para registrar o editar un gasto.
@Composable
fun FormularioGasto(
    gastoEditar: Gasto? = null,

    onGuardar: (
        nombre: String,
        monto: Double,
        categoria: String,
        fecha: String,
        descripcion: String
    ) -> Unit,

    onCancelar: () -> Unit
) {

    // Datos que se muestran en el formulario.
    // Si se está editando un gasto, se cargan sus datos existentes.
    var nombre by remember(gastoEditar?.id) {
        mutableStateOf(gastoEditar?.nombre ?: "")
    }

    var monto by remember(gastoEditar?.id) {
        mutableStateOf(
            gastoEditar?.monto?.toString() ?: ""
        )
    }

    var categoria by remember(gastoEditar?.id) {
        mutableStateOf(gastoEditar?.categoria ?: "")
    }

    var fecha by remember(gastoEditar?.id) {
        mutableStateOf(gastoEditar?.fecha ?: "")
    }

    var descripcion by remember(gastoEditar?.id) {
        mutableStateOf(gastoEditar?.descripcion ?: "")
    }

    // Guarda el mensaje de error del monto.
    var errorMonto by remember {
        mutableStateOf("")
    }

// Guarda el mensaje de error de la categoría.
    var errorCategoria by remember {
        mutableStateOf("")
    }

// Guarda el mensaje de error de la descripción.
    var errorDescripcion by remember {
        mutableStateOf("")
    }

    // Controla si se debe mostrar un mensaje de error.
    var mensajeError by remember {
        mutableStateOf("")
    }

    // Controla si se muestra la lista de categorías.
    var mostrarCategorias by remember {
        mutableStateOf(false)
    }

    // Controla el calendario para seleccionar la fecha.
    val contexto = LocalContext.current
    val calendario = Calendar.getInstance()

    var mostrarCalendario by remember {
        mutableStateOf(false)
    }

    // Categorías disponibles para los gastos.
    val categorias = listOf(
        "Alimentación",
        "Transporte",
        "Vivienda",
        "Servicios",
        "Salud",
        "Educación",
        "Entretenimiento",
        "Compras",
        "Otros"
    )

    AlertDialog(
        onDismissRequest = onCancelar,

        // Cambia el título dependiendo de si estamos registrando o editando.
        title = {
            Text(
                if (gastoEditar == null) {
                    "Registrar gasto"
                } else {
                    "Editar gasto"
                }
            )
        },

        text = {

            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                // Campo para indicar el nombre del gasto.
                OutlinedTextField(
                    value = nombre,
                    onValueChange = {
                        nombre = it
                    },
                    label = {
                        Text("Nombre")
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                // Campo para indicar el monto.
                OutlinedTextField(
                    value = monto,
                    onValueChange = {
                        monto = it

                        // Limpia el mensaje cuando el usuario modifica el valor.
                        errorMonto = ""
                    },
                    label = {
                        Text("Monto")
                    },
                    isError = errorMonto.isNotBlank(),
                    supportingText = {
                        if (errorMonto.isNotBlank()) {
                            Text(errorMonto)
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                // Campo de categoría.
                // Al pulsar sobre el campo se muestra la lista de categorías.
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
                        isError = errorCategoria.isNotBlank(),
                        supportingText = {
                            if (errorCategoria.isNotBlank()) {
                                Text(errorCategoria)
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    )

                    // Zona invisible que permite pulsar sobre todo el campo.
                    Box(
                        modifier = Modifier
                            .matchParentSize()
                            .clickable {
                                mostrarCategorias = true
                            }
                    )

                    // Lista desplegable de categorías.
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

                    // Permite pulsar cualquier parte del campo de fecha.
                    Box(
                        modifier = Modifier
                            .matchParentSize()
                            .clickable {
                                mostrarCalendario = true
                            }
                    )
                }

                // Campo para una descripción opcional.
                OutlinedTextField(
                    value = descripcion,
                    onValueChange = {
                        descripcion = it

                        errorDescripcion =
                            if (FinanzasRules.descripcionValida(it)) {
                                ""
                            } else {
                                "La descripción no puede superar los 200 caracteres."
                            }
                    },
                    label = {
                        Text("Descripción")
                    },
                    isError = errorDescripcion.isNotBlank(),
                    supportingText = {
                        if (errorDescripcion.isNotBlank()) {
                            Text(errorDescripcion)
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },

        // Botón para guardar un nuevo gasto o actualizar uno existente.
        confirmButton = {

            TextButton(
                onClick = {

                    // Intenta convertir el monto escrito a número.
                    val valor = monto.toDoubleOrNull()

// Reinicia los mensajes anteriores.
                    errorMonto = ""
                    errorCategoria = ""
                    errorDescripcion = ""

// Valida el monto.
                    if (valor == null || !FinanzasRules.montoValido(valor)) {
                        errorMonto = "El monto debe ser mayor a 0."
                    }

// Valida la categoría.
                    if (!FinanzasRules.categoriaValida(categoria)) {
                        errorCategoria = "Debe seleccionar una categoría."
                    }

// Valida la descripción.
                    if (!FinanzasRules.descripcionValida(descripcion)) {
                        errorDescripcion = "La descripción no puede superar 200 caracteres."
                    }

// Solo guarda si todas las validaciones son correctas.
                    if (
                        errorMonto.isBlank() &&
                        errorCategoria.isBlank() &&
                        errorDescripcion.isBlank() &&
                        nombre.isNotBlank() &&
                        fecha.isNotBlank()
                    ) {

                        onGuardar(
                            nombre,
                            valor!!,
                            categoria,
                            fecha,
                            descripcion
                        )
                    }
                }
            ) {

                // El texto cambia dependiendo de la operación.
                Text(
                    if (gastoEditar == null) {
                        "Guardar"
                    } else {
                        "Actualizar"
                    }
                )
            }
        },

        // Botón para cancelar el registro o edición.
        dismissButton = {

            TextButton(
                onClick = onCancelar
            ) {
                Text("Cancelar")
            }
        }
    )

    // Calendario para seleccionar o cambiar la fecha.
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

                // Actualiza la fecha mostrada en el formulario.
                fecha = formato.format(
                    fechaSeleccionada.time
                )

                // Cierra el calendario después de seleccionar la fecha.
                mostrarCalendario = false
            },
            calendario.get(Calendar.YEAR),
            calendario.get(Calendar.MONTH),
            calendario.get(Calendar.DAY_OF_MONTH)
        ).show()
    }
}