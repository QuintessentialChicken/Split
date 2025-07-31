package com.example.split.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import dagger.Provides
import javax.inject.Singleton

@Database(entities = [Expense::class, Group::class, User::class, ExpenseParticipant::class], version = 1)
abstract class SplitDatabase : RoomDatabase() {
    abstract fun expenseDao(): ExpenseDao
    abstract fun userDao(): UserDao
    abstract fun participantDao(): ParticipantDao
}
