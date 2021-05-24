package com.bohrapankaj.mpm.network

import okhttp3.ResponseBody

sealed class Resource<out T> {

    // here we will define two data class 1-> Success 2-> Failure
    data class Success<out T>(val value: T) : Resource<T>()

    // it wont return T bcz it is a failure situation. here we will return the values which are require to handle the failure
    data class Failure(
        // if it is Network Error
        val isNetworkError: Boolean,

        //Error Code to get Error Code from api Call
        val errorCode: Int?,

        //error response body
        val errorBody: ResponseBody?,
        val message: String?
    ) : Resource<Nothing>() //for resource it will return nothing


    //for loading we will create a object
    object Loading : Resource<Nothing>()
}