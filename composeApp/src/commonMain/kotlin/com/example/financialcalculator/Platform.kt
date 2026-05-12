package com.example.financialcalculator

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform