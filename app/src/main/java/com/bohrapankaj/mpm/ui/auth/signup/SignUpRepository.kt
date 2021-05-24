package com.bohrapankaj.mpm.ui.auth.signup

import com.bohrapankaj.mpm.base.BaseRepository
import com.bohrapankaj.mpm.network.AuthApi

class SignUpRepository(
    private val authApi: AuthApi
) : BaseRepository() {

    suspend fun register(
        email: String,
        password: String
    ) = safeApiCall {
        authApi.signUp(email = email, password = password)
    }
}