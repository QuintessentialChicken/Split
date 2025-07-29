package com.example.split.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true) val userId: Long = 0,
    val name: String
)

@Entity(tableName = "groups")
data class Group(
    @PrimaryKey(autoGenerate = true) val groupId: Long = 0,
    val groupName: String
)

@Entity(
    tableName = "group_memberships",
    primaryKeys = ["groupId", "userId"],
    foreignKeys = [
        ForeignKey(entity = Group::class, parentColumns = ["groupId"], childColumns = ["groupId"]),
        ForeignKey(entity = User::class, parentColumns = ["userId"], childColumns = ["userId"])
    ],
    indices = [Index("groupId"), Index("userId")]
)
data class GroupMembershipEntity(
    val groupId: Long,
    val userId: Long
)

@Entity(
    tableName = "expenses",
    foreignKeys = [
        ForeignKey(entity = Group::class, parentColumns = ["groupId"], childColumns = ["groupId"], onDelete = ForeignKey.SET_NULL),
        ForeignKey(entity = User::class, parentColumns = ["userId"], childColumns = ["paidByUserId"])
    ],
    indices = [Index("groupId"), Index("paidByUserId")]
)
data class Expense(
    @PrimaryKey(autoGenerate = true) val expenseId: Long = 0,
    val title: String,
    val amount: Double,
    val currencyCode: String,
    val date: Long,
    val paidByUserId: Long,
    val groupId: Long? = null  // null if not part of a group
)

@Entity(
    tableName = "expense_participants",
    primaryKeys = ["expenseId", "userId"],
    foreignKeys = [
        ForeignKey(entity = Expense::class, parentColumns = ["expenseId"], childColumns = ["expenseId"]),
        ForeignKey(entity = User::class, parentColumns = ["userId"], childColumns = ["userId"])
    ],
    indices = [Index("expenseId"), Index("userId")]
)
data class ExpenseParticipantEntity(
    val expenseId: Long,
    val userId: Long,
    val share: Double  // how much this user owes
)
