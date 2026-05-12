package com.example.financialcalculator.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.financialcalculator.model.CompoundingPeriod
import com.example.financialcalculator.model.YearData
import com.example.financialcalculator.viewmodel.CalculatorUiState
import androidx.compose.foundation.text.KeyboardOptions

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalculatorScreen(
    uiState: CalculatorUiState,
    onInitialAmountChange: (String) -> Unit,
    onAnnualRateChange: (String) -> Unit,
    onYearsChange: (String) -> Unit,
    onCompoundingChange: (CompoundingPeriod) -> Unit,
    onCalculate: () -> Unit,
    onClearError: () -> Unit,
    onLanguageChange: (String) -> Unit
) {
    val labels = if (uiState.language == "ru") {
        mapOf(
            "title" to "Финансовый калькулятор",
            "initial" to "Начальная сумма",
            "rate" to "Годовая ставка (%)",
            "years" to "Срок (лет)",
            "compounding" to "Капитализация",
            "calculate" to "Рассчитать",
            "final" to "Итоговая сумма",
            "profit" to "Прибыль",
            "growth" to "Рост капитала"
        )
    } else {
        mapOf(
            "title" to "Financial Calculator",
            "initial" to "Initial Amount",
            "rate" to "Annual Rate (%)",
            "years" to "Term (years)",
            "compounding" to "Compounding",
            "calculate" to "Calculate",
            "final" to "Final Amount",
            "profit" to "Profit",
            "growth" to "Capital Growth"
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(labels["title"]!!, fontWeight = FontWeight.Bold) },
                actions = {
                    TextButton(onClick = {
                        onLanguageChange(if (uiState.language == "ru") "en" else "ru")
                    }) {
                        Text(if (uiState.language == "ru") "EN" else "RU")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = uiState.initialAmount,
                onValueChange = onInitialAmountChange,
                label = { Text(labels["initial"]!!) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )

            OutlinedTextField(
                value = uiState.annualRate,
                onValueChange = onAnnualRateChange,
                label = { Text(labels["rate"]!!) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )

            OutlinedTextField(
                value = uiState.years,
                onValueChange = onYearsChange,
                label = { Text(labels["years"]!!) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )

            Text(labels["compounding"]!!, style = MaterialTheme.typography.titleSmall)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                CompoundingPeriod.entries.forEach { period ->
                    FilterChip(
                        selected = uiState.compounding == period,
                        onClick = { onCompoundingChange(period) },
                        label = {
                            Text(
                                if (uiState.language == "ru") period.labelRu else period.labelEn,
                                fontSize = 12.sp
                            )
                        },
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            Button(
                onClick = onCalculate,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(labels["calculate"]!!, fontSize = 18.sp)
            }

            uiState.error?.let { error ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer
                    )
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(error, color = MaterialTheme.colorScheme.onErrorContainer)
                        TextButton(onClick = onClearError) { Text("OK") }
                    }
                }
            }

            uiState.result?.let { result ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(labels["final"]!!, style = MaterialTheme.typography.titleMedium)
                        Text(
                            text = "${(result.finalAmount * 100).toInt() / 100.0} ₽",
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            "${labels["profit"]}: ${(result.totalProfit * 100).toInt() / 100.0} ₽",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.secondary
                        )
                    }
                }

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            labels["growth"]!!,
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        GrowthChart(
                            data = result.yearlyData,
                            modifier = Modifier.fillMaxWidth().height(200.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
fun GrowthChart(data: List<YearData>, modifier: Modifier = Modifier) {
    if (data.isEmpty()) return

    val maxAmount = data.maxOf { it.amount }
    val minAmount = data.minOf { it.amount }
    val range = if (maxAmount == minAmount) 1.0 else maxAmount - minAmount

    Canvas(modifier = modifier) {
        val width = size.width
        val height = size.height
        val padding = 40f
        val chartWidth = width - padding * 2
        val chartHeight = height - padding * 2

        drawLine(
            color = Color.Gray,
            start = Offset(padding, height - padding),
            end = Offset(width - padding, height - padding),
            strokeWidth = 1f
        )
        drawLine(
            color = Color.Gray,
            start = Offset(padding, padding),
            end = Offset(padding, height - padding),
            strokeWidth = 1f
        )

        if (data.size > 1) {
            val path = Path()
            data.forEachIndexed { index, yearData ->
                val x = padding + (index.toFloat() / (data.size - 1)) * chartWidth
                val y = height - padding - ((yearData.amount - minAmount) / range * chartHeight).toFloat()
                if (index == 0) path.moveTo(x, y) else path.lineTo(x, y)
            }
            drawPath(path, Color(0xFF1565C0), style = Stroke(width = 3f, cap = StrokeCap.Round))
        }

        data.forEachIndexed { index, yearData ->
            val x = padding + if (data.size > 1) (index.toFloat() / (data.size - 1)) * chartWidth else chartWidth / 2
            val y = height - padding - ((yearData.amount - minAmount) / range * chartHeight).toFloat()
            drawCircle(Color(0xFF1565C0), 5f, Offset(x, y))
        }
    }
}