package com.example.financialcalculator.ui

import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.financialcalculator.ui.theme.CalculatorTheme
import com.example.financialcalculator.viewmodel.CalculatorViewModel

@Composable
fun App() {
    val viewModel = viewModel { CalculatorViewModel() }
    val uiState by viewModel.uiState.collectAsState()

    CalculatorTheme {
        CalculatorScreen(
            uiState = uiState,
            onInitialAmountChange = { viewModel.updateInitialAmount(it) },
            onAnnualRateChange = { viewModel.updateAnnualRate(it) },
            onYearsChange = { viewModel.updateYears(it) },
            onCompoundingChange = { viewModel.updateCompounding(it) },
            onCalculate = { viewModel.calculate() },
            onClearError = { viewModel.clearError() },
            onLanguageChange = { viewModel.switchLanguage(it) }
        )
    }
}