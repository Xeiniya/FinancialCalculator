package com.example.financialcalculator.model

import kotlin.test.*
import kotlin.math.abs
import kotlin.math.pow

class CalculatorModelTest {

    @Test
    fun testMonthlyCompounding() {
        val input = CalculationInput(
            initialAmount = 100000.0, annualRate = 10.0, years = 5, compounding = CompoundingPeriod.MONTHLY
        )
        val result = FinancialCalculator.calculate(input)
        assertTrue(result.finalAmount > input.initialAmount)
        assertTrue(result.totalProfit > 0)
        assertEquals(5, result.yearlyData.size)
    }

    @Test
    fun testQuarterlyCompounding() {
        val input = CalculationInput(
            initialAmount = 50000.0, annualRate = 8.0, years = 3, compounding = CompoundingPeriod.QUARTERLY
        )
        val result = FinancialCalculator.calculate(input)
        assertTrue(result.finalAmount > 50000.0)
        assertEquals(3, result.yearlyData.size)
    }

    @Test
    fun testAnnualCompounding() {
        val input = CalculationInput(
            initialAmount = 200000.0, annualRate = 5.0, years = 10, compounding = CompoundingPeriod.ANNUALLY
        )
        val result = FinancialCalculator.calculate(input)
        val expected = 200000.0 * (1.05).pow(10.0)
        assertTrue(abs(result.finalAmount - expected) < 0.01)
    }

    @Test
    fun testValidationNegativeAmount() {
        val input = CalculationInput(initialAmount = -100.0)
        assertNotNull(FinancialCalculator.validateInput(input))
    }

    @Test
    fun testValidationZeroRate() {
        val input = CalculationInput(annualRate = 0.0)
        assertNotNull(FinancialCalculator.validateInput(input))
    }

    @Test
    fun testValidationLargeYears() {
        val input = CalculationInput(years = 150)
        val error = FinancialCalculator.validateInput(input)
        assertNotNull(error)
        assertTrue(error!!.contains("большой"))
    }

    @Test
    fun testCompoundingPeriodValues() {
        assertEquals(12, CompoundingPeriod.MONTHLY.periodsPerYear)
        assertEquals(4, CompoundingPeriod.QUARTERLY.periodsPerYear)
        assertEquals(1, CompoundingPeriod.ANNUALLY.periodsPerYear)
    }
}