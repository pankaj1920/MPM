package com.bohrapankaj.mpm.network

import com.bohrapankaj.mpm.response.common.CommonModel
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AuthApi {

    @FormUrlEncoded
    @POST("auth/login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): CommonModel

    @FormUrlEncoded
    @POST("auth/register")
    suspend fun signUp(
        @Field("email") email: String,
        @Field("password") password: String
    ): CommonModel

    @FormUrlEncoded
    @POST("auth/forgetPassOtpGenerate")
    suspend fun forgetPassOtpGenerate(
        @Field("email") email: String
    ): CommonModel

    @FormUrlEncoded
    @POST("auth/updatePassword")
    suspend fun updatePassword(
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("otp") otp: String
    ): CommonModel
}