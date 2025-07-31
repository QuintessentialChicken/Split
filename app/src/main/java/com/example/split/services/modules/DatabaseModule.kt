package com.example.split.services.modules

import android.content.Context
import androidx.room.Room
import com.example.split.data.ExpenseDao
import com.example.split.data.ParticipantDao
import com.example.split.data.SplitDatabase
import com.example.split.data.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): SplitDatabase =
        Room.databaseBuilder(
            context,
            SplitDatabase::class.java,
            "expense_database"
        ).build()

    @Provides
    fun provideExpenseDao(db: SplitDatabase): ExpenseDao = db.expenseDao()

    @Provides
    fun provideUserDao(db: SplitDatabase): UserDao = db.userDao()

    @Provides
    fun provideParticipantDao(db: SplitDatabase): ParticipantDao = db.participantDao()
}