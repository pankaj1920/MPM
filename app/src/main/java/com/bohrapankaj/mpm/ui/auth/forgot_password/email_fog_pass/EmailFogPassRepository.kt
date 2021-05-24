package com.bohrapankaj.mpm.ui.auth.forgot_password.email_fog_pass

import com.bohrapankaj.mpm.base.BaseRepository
import com.bohrapankaj.mpm.network.AuthApi

class EmailFogPassRepository(
    private val authApi: AuthApi
) : BaseRepository() {

    suspend fun forgetPassOtpGenerate(email: String) = safeApiCall {
        authApi.forgetPassOtpGenerate(email = email)
    }
}