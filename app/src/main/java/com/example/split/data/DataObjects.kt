package com.example.split.data

import androidx.room.Entity
import java.util.UUID

@Entity(tableName = "expenses")
data class Expense(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val amount: Double,
    val paidBy: String, // userId
    val splitBetween: List<String>,
    val timestamp: Long = System.currentTimeMillis()
)