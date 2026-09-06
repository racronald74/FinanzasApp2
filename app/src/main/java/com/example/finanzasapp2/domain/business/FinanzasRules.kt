package com.example.finanzasapp2.domain.business

object FinanzasRules {

    fun montoValido(monto: Double): Boolean {
        return monto > 0
    }

    fun descripcionValida(descripcion: String): Boolean {
        return descripcion.length <= 200
    }

    fun categoriaValida(categoria: String): Boolean {
        return categoria.isNotBlank()
    }

    fun saldoDisponible(
        totalIngresos: Double,
        totalGastos: Double
    ): Double {
        return totalIngresos - totalGastos
    }
}