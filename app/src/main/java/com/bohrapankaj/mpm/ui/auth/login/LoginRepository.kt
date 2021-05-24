package com.bohrapankaj.mpm.ui.auth.login

import com.bohrapankaj.mpm.base.BaseRepository
import com.bohrapankaj.mpm.network.AuthApi

class LoginRepository(
    private val authApi: AuthApi
) : BaseRepository() {

    suspend fun login(
        email:String,
        password:String
    ) = safeApiCall {
            authApi.login(email = email,password = password)
    }
}