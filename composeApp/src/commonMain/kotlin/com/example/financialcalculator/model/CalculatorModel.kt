package com.example.financialcalculator.model

import kotlin.math.pow

enum class CompoundingPeriod(val labelRu: String, val labelEn: String, val periodsPerYear: Int) {
    MONTHLY("Ежемесячно", "Monthly", 12),
    QUARTERLY("Ежеквартально", "Quarterly", 4),
    ANNUALLY("Ежегодно", "Annually", 1)
}

data class CalculationInput(
    val initialAmount: Double = 100000.0,
    val annualRate: Double = 10.0,
    val years: Int = 5,
    val compounding: CompoundingPeriod = CompoundingPeriod.MONTHLY
)

data class CalculationResult(
    val finalAmount: Double = 0.0,
    val totalProfit: Double = 0.0,
    val yearlyData: List<YearData> = emptyList()
)

data class YearData(
    val year: Int,
    val amount: Double,
    val profit: Double
)

object FinancialCalculator {

    fun calculate(input: CalculationInput): CalculationResult {
        val rate = input.annualRate / 100.0
        val periods = input.compounding.periodsPerYear
        val n = periods * input.years
        val r = rate / periods

        val finalAmount = input.initialAmount * (1 + r).pow(n)
        val totalProfit = finalAmount - input.initialAmount

        val yearlyData = (1..input.years).map { year ->
            val nYears = periods * year
            val amount = input.initialAmount * (1 + r).pow(nYears)
            val profit = amount - input.initialAmount
            YearData(year = year, amount = amount, profit = profit)
        }

        return CalculationResult(
            finalAmount = finalAmount,
            totalProfit = totalProfit,
            yearlyData = yearlyData
        )
    }

    fun validateInput(input: CalculationInput): String? {
        if (input.initialAmount <= 0) return "Начальная сумма должна быть положительной"
        if (input.initialAmount > 1_000_000_000) return "Сумма слишком большая"
        if (input.annualRate <= 0) return "Процентная ставка должна быть положительной"
        if (input.annualRate > 100) return "Ставка не может превышать 100%"
        if (input.years <= 0) return "Срок должен быть положительным"
        if (input.years > 100) return "Срок слишком большой"
        return null
    }
}