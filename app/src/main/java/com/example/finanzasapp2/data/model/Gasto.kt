package com.example.finanzasapp2.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "gastos")
data class Gasto(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val nombre: String,

    val monto: Double,

    val categoria: String,

    val descripcion: String = "",

    val fecha: String
)