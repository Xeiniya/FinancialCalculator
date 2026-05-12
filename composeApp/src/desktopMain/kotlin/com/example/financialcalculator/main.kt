package com.example.financialcalculator

import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.example.financialcalculator.ui.App

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Financial Calculator",
        state = rememberWindowState(width = 480.dp, height = 800.dp)
    ) { App() }
}