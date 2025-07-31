package com.example.split.utils

import com.example.split.data.Expense
import java.text.NumberFormat
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Currency
import java.util.Locale
import kotlin.math.pow

fun millisToDateString(timestamp: Long, format: String): String {
    val dateTime = Instant.ofEpochMilli(timestamp).atZone(ZoneId.systemDefault())
    val formatter = DateTimeFormatter.ofPattern(format, Locale.getDefault())
    return dateTime.format(formatter)
}

fun formatCurrency(amount: Int, currencyCode: String = "EUR"): String {
    val currency = Currency.getInstance(currencyCode)
    val fractionDigits = currency.defaultFractionDigits

    val majorAmount = amount / 10.0.pow(fractionDigits).toDouble()

    val formatter = NumberFormat.getCurrencyInstance().apply {
        this.currency = currency
        maximumFractionDigits = fractionDigits
        minimumFractionDigits = fractionDigits
    }

    return formatter.format(majorAmount)
}