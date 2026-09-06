package com.example.finanzasapp2.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finanzasapp2.data.model.Gasto
import com.example.finanzasapp2.data.model.Ingreso
import com.example.finanzasapp2.data.repository.FinanzasRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

// ViewModel principal de la aplicación.
// Se encarga de conectar la interfaz con el repositorio
// y mantener los datos de ingresos y gastos.
class FinanzasViewModel(
    private val repository: FinanzasRepository
) : ViewModel() {

    // Lista de ingresos almacenados en la base de datos.
    val ingresos: StateFlow<List<Ingreso>> =
        repository.obtenerIngresos()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList()
            )

    // Lista de gastos almacenados en la base de datos.
    val gastos: StateFlow<List<Gasto>> =
        repository.obtenerGastos()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList()
            )

    // Registra un nuevo ingreso.
    fun insertarIngreso(ingreso: Ingreso) {
        viewModelScope.launch {
            repository.insertarIngreso(ingreso)
        }
    }

    // Registra un nuevo gasto.
    fun insertarGasto(gasto: Gasto) {
        viewModelScope.launch {
            repository.insertarGasto(gasto)
        }
    }

    // Actualiza un ingreso existente.
    fun actualizarIngreso(ingreso: Ingreso) {
        viewModelScope.launch {
            repository.actualizarIngreso(ingreso)
        }
    }

    // Actualiza un gasto existente.
    fun actualizarGasto(gasto: Gasto) {
        viewModelScope.launch {
            repository.actualizarGasto(gasto)
        }
    }

    // Elimina un ingreso existente.
    fun eliminarIngreso(ingreso: Ingreso) {
        viewModelScope.launch {
            repository.eliminarIngreso(ingreso)
        }
    }

    // Elimina un gasto existente.
    fun eliminarGasto(gasto: Gasto) {
        viewModelScope.launch {
            repository.eliminarGasto(gasto)
        }
    }
}