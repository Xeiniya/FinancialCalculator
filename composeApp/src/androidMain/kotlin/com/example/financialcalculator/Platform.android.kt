package com.example.financialcalculator

class AndroidPlatform : Platform { override val name: String = "Android" }
actual fun getPlatform(): Platform = AndroidPlatform()