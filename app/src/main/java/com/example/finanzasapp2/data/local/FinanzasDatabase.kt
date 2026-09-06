package com.example.finanzasapp2.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.finanzasapp2.data.model.Gasto
import com.example.finanzasapp2.data.model.Ingreso

@Database(
    entities = [Ingreso::class, Gasto::class],
    version = 1,
    exportSchema = false
)
abstract class FinanzasDatabase : RoomDatabase() {

    abstract fun ingresoDao(): IngresoDao

    abstract fun gastoDao(): GastoDao
}