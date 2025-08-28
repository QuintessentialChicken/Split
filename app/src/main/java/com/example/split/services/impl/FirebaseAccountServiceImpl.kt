package com.example.split.services.impl

import com.example.split.data.User
import com.example.split.services.AccountService
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FirebaseAccountServiceImpl @Inject constructor(
    auth: FirebaseAuth
): AccountService {
    override val currentUserId: String
        get() = TODO("Not yet implemented")
    override val hasUser: Boolean
        get() = TODO("Not yet implemented")
    override val currentUserGetter: FirebaseUser?
        get() = TODO("Not yet implemented")
    override val currentUser: Flow<User>
        get() = TODO("Not yet implemented")

    override suspend fun authenticate(email: String, password: String) {
        TODO("Not yet implemented")
    }

    override suspend fun sendRecoveryEmail(email: String) {
        TODO("Not yet implemented")
    }

    override suspend fun createAnonymousAccount() {
        TODO("Not yet implemented")
    }

    override suspend fun linkAccount(email: String, password: String) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAccount() {
        TODO("Not yet implemented")
    }

    override suspend fun signOut() {
        TODO("Not yet implemented")
    }

}