package com.example.split.services.modules

import com.example.split.data.ExpensesRepository
import com.example.split.data.UsersRepository
import com.example.split.services.AccountService
import com.example.split.services.StorageService
import com.example.split.services.impl.FirebaseAccountServiceImpl
import com.example.split.services.impl.FirestoreStorageServiceImpl
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

//    @Provides
//    fun provideLocalStorageService(expenseDao: ExpenseDao, userDao: UserDao, participantDao: ParticipantDao): StorageService = RoomStorageServiceImpl(expenseDao, userDao, participantDao)

    @Provides
    fun provideAccountService(authRef: FirebaseAuth): AccountService = FirebaseAccountServiceImpl(authRef)

    @Provides
    fun provideFirestoreService(auth: AccountService, firestoreRef: FirebaseFirestore): StorageService = FirestoreStorageServiceImpl(auth, firestoreRef)

    @Provides
    @Singleton
    fun provideExpenseRepository(
        firestore: StorageService
    ): ExpensesRepository = ExpensesRepository(firestore)

    @Provides
    @Singleton
    fun provideUserRepository(
        auth: AccountService
    ): UsersRepository = UsersRepository(auth)
}