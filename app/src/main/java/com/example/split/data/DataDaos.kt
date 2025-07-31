package com.example.split.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpenseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(expense: Expense): Long

    @Query("SELECT * FROM expenses")
    fun getAll(): Flow<List<Expense>>

    @Query("SELECT * FROM expenses ORDER BY date DESC")
    fun getExpensesSortedByDateDesc(): Flow<List<Expense>>

    @Query("SELECT * FROM expense_participants")
    fun getAllParticipants(): Flow<List<ExpenseParticipant>>

    @Delete
    suspend fun delete(expense: Expense)

    @Update
    suspend fun update(expense: Expense)

    @Query("DELETE FROM expenses WHERE expenseId = :id")
    suspend fun delete(id: String)
}

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: User)

    @Query("SELECT * FROM users")
    fun getAll(): Flow<List<User>>
}

@Dao
interface ParticipantDao {
    @Insert
    suspend fun insert(participants: List<ExpenseParticipant>)
}