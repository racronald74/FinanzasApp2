package com.example.finanzasapp2.data.repository

import com.example.finanzasapp2.data.local.GastoDao
import com.example.finanzasapp2.data.local.IngresoDao
import com.example.finanzasapp2.data.model.Gasto
import com.example.finanzasapp2.data.model.Ingreso
import kotlinx.coroutines.flow.Flow

// Repositorio encargado de conectar el ViewModel
// con los DAO de ingresos y gastos.
class FinanzasRepository(
    private val ingresoDao: IngresoDao,
    private val gastoDao: GastoDao
) {

    // Obtiene todos los ingresos de la base de datos.
    fun obtenerIngresos(): Flow<List<Ingreso>> {
        return ingresoDao.obtenerTodos()
    }

    // Obtiene todos los gastos de la base de datos.
    fun obtenerGastos(): Flow<List<Gasto>> {
        return gastoDao.obtenerGastos()
    }

    // Guarda un nuevo ingreso.
    suspend fun insertarIngreso(ingreso: Ingreso) {
        ingresoDao.insertar(ingreso)
    }

    // Guarda un nuevo gasto.
    suspend fun insertarGasto(gasto: Gasto) {
        gastoDao.insertarGasto(gasto)
    }

    // Actualiza un ingreso existente.
    suspend fun actualizarIngreso(ingreso: Ingreso) {
        ingresoDao.actualizar(ingreso)
    }

    // Actualiza un gasto existente.
    suspend fun actualizarGasto(gasto: Gasto) {
        gastoDao.actualizarGasto(gasto)
    }

    // Elimina un ingreso.
    suspend fun eliminarIngreso(ingreso: Ingreso) {
        ingresoDao.eliminar(ingreso)
    }

    // Elimina un gasto.
    suspend fun eliminarGasto(gasto: Gasto) {
        gastoDao.eliminarGasto(gasto)
    }
}