# FinancialCalculator — Финансовый калькулятор

Кроссплатформенное приложение на Compose Multiplatform — расчёт сложных процентов с визуализацией роста капитала. Поддержка Android, iOS, Linux и Web.

## Основные возможности

- Ввод начальной суммы, процентной ставки, срока
- Выбор периодичности капитализации (ежемесячно/ежеквартально/ежегодно)
- Расчёт итоговой суммы и общей прибыли
- Визуализация роста капитала в виде графика (Canvas API)
- Валидация ввода данных с сообщениями об ошибках
- Локализация: русский, английский
- Анимация элементов интерфейса
- Обработка исключительных ситуаций

## Технологии

| Компонент          | Технология                              |
|--------------------|-----------------------------------------|
| Фреймворк          | Compose Multiplatform 1.10.3            |
| Язык               | Kotlin 2.3.21                           |
| UI                 | Material 3 (Compose)                    |
| Графика            | Canvas API (собственная реализация)      |
| Архитектура        | MVVM (ViewModel + StateFlow)            |
| Математика         | kotlin.math (pow)                       |
| Сериализация       | kotlinx.serialization 1.7.3             |
| Тесты              | kotlin.test + kotlinx.coroutines.test   |
| CI/CD              | GitHub Actions                          |

## Установка и запуск

```bash
git clone https://github.com/Xeiniya/FinancialCalculator.git
cd FinancialCalculator

# Android
./gradlew composeApp:assembleDebug

# Desktop
./gradlew composeApp:run

# Web
./gradlew composeApp:wasmJsBrowserRun
```

## Тестирование

```bash
# Все тесты
./gradlew composeApp:allTests

# Только модульные тесты
./gradlew composeApp:desktopTest
```

## Сборка под платформы

```bash
# Android APK
./gradlew composeApp:assembleDebug

# Desktop дистрибутив
./gradlew composeApp:createDistributable

# WebAssembly
./gradlew composeApp:wasmJsBrowserDistribution
```

## Структура проекта

```
composeApp/src/
├── commonMain/kotlin/com/example/financialcalculator/
│   ├── model/              # CalculatorModel.kt — логика расчётов
│   ├── viewmodel/          # CalculatorViewModel.kt — MVVM
│   └── ui/                 # App.kt, CalculatorScreen.kt — Compose UI
│       └── theme/          # Theme.kt — темы
├── androidMain/            # Android-специфичный код
├── iosMain/                # iOS-специфичный код
├── desktopMain/            # Desktop (JVM) код
├── wasmJsMain/             # Web (WebAssembly) код
└── commonTest/             # Общие тесты
```

## Формула расчёта

```
FV = PV × (1 + r/n)^(n×t)

где:
FV — будущая стоимость
PV — начальная сумма
r  — годовая ставка (в десятичных)
n  — периодов капитализации в год
t  — срок в годах
```

## Author

Ксения Николаева
