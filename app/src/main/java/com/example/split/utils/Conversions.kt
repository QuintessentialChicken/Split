package com.example.split.utils

import com.example.split.data.Expense
import java.text.NumberFormat
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Currency
import java.util.Locale

fun millisToDateString(timestamp: Long, format: String): String {
    val dateTime = Instant.ofEpochMilli(timestamp).atZone(ZoneId.systemDefault())
    val formatter = DateTimeFormatter.ofPattern(format, Locale.getDefault())
    return dateTime.format(formatter)
}

fun formatCurrency(amount: Double, currencyCode: String = "EUR"): String {
    val formatter = NumberFormat.getCurrencyInstance()
    formatter.maximumFractionDigits = 2
    formatter.currency = Currency.getInstance(currencyCode)
    return formatter.format(amount)
}