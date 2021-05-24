package com.bohrapankaj.mpm.base

import com.bohrapankaj.mpm.network.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException



abstract class BaseRepository {

    /* here we will define the function to safely call the api
        this fun will be generic fun and return Resource<T> of type T where T is actual response we should get
        */

    /* now we have this function which will execute the api call and to this function we will pass a parameter that is
    our api call so it is another suspending function */

    suspend fun <T> safeApiCall(
        // this apiCall is a suspending function so we have to define suspend keyword here and this function will return us T
        apiCall: suspend () -> T
    ): Resource<T> {
        // now here we will execute the api call
        return withContext(Dispatchers.IO) {
            try {
                // if this call was sucesful we will get the result directly and we will put response to Resource sealed class
                // so we will wrap it with Resource.Sucess
                Resource.Success(apiCall.invoke())
            } catch (throwable: Throwable) {
                // here we have The failure response

                when (throwable) {
                    is HttpException -> {
                        Resource.Failure(
                            false,
                            throwable.code(),
                            throwable.response()!!.errorBody(),
                            null
                        )
                    }
                    else -> {
                        // any oother case we will consider as network error
                        Resource.Failure(true, null, null ,throwable.message.toString())
                    }
                }
            }
        }
    }
}