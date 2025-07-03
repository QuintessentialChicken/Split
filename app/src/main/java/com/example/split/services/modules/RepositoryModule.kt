package com.example.split.services.modules

import com.example.split.data.ExpenseDao
import com.example.split.data.ExpensesRepository
import com.example.split.services.StorageService
import com.example.split.services.impl.RoomStorageServiceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideLocalStorageService(dao: ExpenseDao): StorageService = RoomStorageServiceImpl(dao)

    @Provides
    fun provideRepository(
        local: StorageService
    ): ExpensesRepository = ExpensesRepository(local)
}