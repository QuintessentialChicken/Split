package com.example.split.data

data class User(
    val name: String = "",
    val friendCode: String = "",
)

data class Expense(
    val title: String,
    val amount: Int,
    val currencyCode: String,
    val date: Long,
    val paidByUserId: Long,
    val groupId: Long? = null  // null if not part of a group
)

data class Group(
    var name: String = "",
    var members: List<String> = emptyList()
)