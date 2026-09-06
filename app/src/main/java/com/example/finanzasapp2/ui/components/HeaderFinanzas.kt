package com.example.finanzasapp2.ui.components

import androidx.compose.foundation.background

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.draw.shadow

@Composable
fun HeaderFinanzas(
    titulo: String
) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(142.dp)
            .shadow(
                elevation = 8.dp,
                clip = false
            )
            .background(
                Color(
                    red = 70,
                    green = 118,
                    blue = 184
                )
            )
    ) {

        // Icono a la izquierda dentro de un círculo
        Box(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(start = 24.dp)
                .size(56.dp)
                .background(
                    color = Color(0xFFD9E6FF),
                    shape = androidx.compose.foundation.shape.CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {

            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Perfil",
                tint = Color(0xFF294B7A),
                modifier = Modifier.size(32.dp)
            )
        }

        // Título exactamente centrado en el Header
        Text(
            text = titulo,
            color = Color.White,
            fontSize = 28.sp,
            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}