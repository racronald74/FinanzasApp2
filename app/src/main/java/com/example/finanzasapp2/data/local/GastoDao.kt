package com.example.finanzasapp2.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.finanzasapp2.data.model.Gasto
import kotlinx.coroutines.flow.Flow

@Dao
interface GastoDao {

    @Query("SELECT * FROM gastos ORDER BY id DESC")
    fun obtenerGastos(): Flow<List<Gasto>>

    @Insert
    suspend fun insertarGasto(gasto: Gasto)

    @Update
    suspend fun actualizarGasto(gasto: Gasto)

    @Delete
    suspend fun eliminarGasto(gasto: Gasto)
}