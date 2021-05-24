package com.bohrapankaj.mpm.ui.auth.forgot_password.change_password

import com.bohrapankaj.mpm.base.BaseRepository
import com.bohrapankaj.mpm.network.AuthApi

class ChangePasswordRepository(
    private val authApi: AuthApi
) : BaseRepository() {

    suspend fun updatePassword(email: String, password: String, otp: String) = safeApiCall {
        authApi.updatePassword(email = email, password = password, otp = otp)
    }
}