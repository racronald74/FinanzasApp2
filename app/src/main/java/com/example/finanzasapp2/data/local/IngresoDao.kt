package com.example.finanzasapp2.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.finanzasapp2.data.model.Ingreso
import kotlinx.coroutines.flow.Flow

// DAO encargado de acceder a la tabla de ingresos.
@Dao
interface IngresoDao {

    // Obtiene todos los ingresos ordenados del más reciente al más antiguo.
    // Flow permite que la interfaz se actualice automáticamente cuando cambien los datos.
    @Query("SELECT * FROM ingresos ORDER BY id DESC")
    fun obtenerTodos(): Flow<List<Ingreso>>

    // Guarda un nuevo ingreso en la base de datos.
    @Insert
    suspend fun insertar(ingreso: Ingreso)

    // Elimina un ingreso existente.
    @Delete
    suspend fun eliminar(ingreso: Ingreso)

    @Update
    suspend fun actualizar(ingreso: Ingreso)
}