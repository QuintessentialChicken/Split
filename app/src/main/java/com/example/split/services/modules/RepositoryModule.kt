package com.example.split.services.modules

import com.example.split.data.ExpenseDao
import com.example.split.data.ExpensesRepository
import com.example.split.data.ParticipantDao
import com.example.split.data.UserDao
import com.example.split.data.UsersRepository
import com.example.split.services.StorageService
import com.example.split.services.impl.RoomStorageServiceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideLocalStorageService(expenseDao: ExpenseDao, userDao: UserDao, participantDao: ParticipantDao): StorageService = RoomStorageServiceImpl(expenseDao, userDao, participantDao)

    @Provides
    @Singleton
    fun provideExpenseRepository(
        local: StorageService
    ): ExpensesRepository = ExpensesRepository(local)

    @Provides
    @Singleton
    fun provideUserRepository(
        local: StorageService
    ): UsersRepository = UsersRepository(local)
}