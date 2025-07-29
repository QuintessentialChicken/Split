package com.example.split.utils

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

fun millisToDateString(timestamp: Long, format: String): String {
    val dateTime = Instant.ofEpochMilli(timestamp).atZone(ZoneId.systemDefault())
    val formatter = DateTimeFormatter.ofPattern(format, Locale.getDefault())
    return dateTime.format(formatter)
}