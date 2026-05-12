package com.example.financialcalculator.viewmodel

import androidx.lifecycle.ViewModel
import com.example.financialcalculator.model.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class CalculatorUiState(
    val initialAmount: String = "100000",
    val annualRate: String = "10",
    val years: String = "5",
    val compounding: CompoundingPeriod = CompoundingPeriod.MONTHLY,
    val result: CalculationResult? = null,
    val error: String? = null,
    val language: String = "ru"
)

class CalculatorViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(CalculatorUiState())
    val uiState: StateFlow<CalculatorUiState> = _uiState.asStateFlow()

    fun updateInitialAmount(value: String) {
        _uiState.value = _uiState.value.copy(initialAmount = value)
    }

    fun updateAnnualRate(value: String) {
        _uiState.value = _uiState.value.copy(annualRate = value)
    }

    fun updateYears(value: String) {
        _uiState.value = _uiState.value.copy(years = value)
    }

    fun updateCompounding(period: CompoundingPeriod) {
        _uiState.value = _uiState.value.copy(compounding = period)
    }

    fun calculate() {
        val state = _uiState.value
        val initialAmount = state.initialAmount.toDoubleOrNull()
        val annualRate = state.annualRate.toDoubleOrNull()
        val years = state.years.toIntOrNull()

        if (initialAmount == null || annualRate == null || years == null) {
            _uiState.value = state.copy(error = "Введите корректные числовые значения")
            return
        }

        val input = CalculationInput(
            initialAmount = initialAmount,
            annualRate = annualRate,
            years = years,
            compounding = state.compounding
        )

        val validationError = FinancialCalculator.validateInput(input)
        if (validationError != null) {
            _uiState.value = state.copy(error = validationError)
            return
        }

        val result = FinancialCalculator.calculate(input)
        _uiState.value = _uiState.value.copy(result = result, error = null)
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }

    fun switchLanguage(lang: String) {
        _uiState.value = _uiState.value.copy(language = lang)
    }
}