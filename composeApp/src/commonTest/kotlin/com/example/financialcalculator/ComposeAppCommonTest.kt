package com.example.financialcalculator.viewmodel

import com.example.financialcalculator.model.CompoundingPeriod
import kotlin.test.*

class CalculatorViewModelTest {

    @Test
    fun testInitialState() {
        val state = CalculatorUiState()
        assertEquals("100000", state.initialAmount)
        assertEquals("10", state.annualRate)
        assertEquals(CompoundingPeriod.MONTHLY, state.compounding)
        assertNull(state.result)
    }

    @Test
    fun testUpdateInitialAmount() {
        val state = CalculatorUiState(initialAmount = "50000")
        assertEquals("50000", state.initialAmount)
    }

    @Test
    fun testUpdateCompounding() {
        val state = CalculatorUiState(compounding = CompoundingPeriod.QUARTERLY)
        assertEquals(CompoundingPeriod.QUARTERLY, state.compounding)
    }

    @Test
    fun testLanguageSwitch() {
        val state = CalculatorUiState(language = "en")
        assertEquals("en", state.language)
    }

    @Test
    fun testErrorState() {
        val state = CalculatorUiState(error = "Test error")
        assertEquals("Test error", state.error)
    }

    @Test
    fun testClearError() {
        val state = CalculatorUiState(error = "Error").copy(error = null)
        assertNull(state.error)
    }

    @Test
    fun testResultNullByDefault() {
        val state = CalculatorUiState()
        assertNull(state.result)
    }
}