package com.example.split.ui.screens.splash

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.split.navigation.Groups
import com.example.split.services.AccountService
import com.example.split.services.StorageService
import com.google.firebase.auth.FirebaseAuthException
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "SplashViewModel"
//TODO DataStore is not read/written properly

class SplashViewModel @Inject constructor(
    private val accountService: AccountService,
    private val storageService: StorageService,
) : ViewModel() {
    private val showError = mutableStateOf(false)
    var isLoading = mutableStateOf(true)
    var alarm = false


    init {
        if (!alarm) onAppStart()
    }

    private fun onAppStart() {
        showError.value = false

        viewModelScope.launch {
            if (!accountService.hasUser) {
                try {
                    accountService.createAnonymousAccount()
                } catch (ex: FirebaseAuthException) {
                    showError.value = true
                    throw ex
                }
            }
        }
        isLoading.value = false
    }
}