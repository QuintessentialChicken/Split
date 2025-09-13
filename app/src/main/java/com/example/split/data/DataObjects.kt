package com.example.split.data

data class User(
    val name: String = "",
    val friendCode: String = "",
)

data class Expense(
    val title: String = "",
    val amount: Int = 0,
    val currencyCode: String = "",
    val date: Long = 0,
    val paidByUserId: String = "",
    val groupId: String? = null,  //null if not part of a group
    val participants: Map<String, Float> = emptyMap()
)

data class Group(
    var name: String = "",
    var members: List<String> = emptyList(),
    var memberCount: Int = -1,
)