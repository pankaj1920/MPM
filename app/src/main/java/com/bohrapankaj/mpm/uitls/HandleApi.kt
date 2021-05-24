package com.bohrapankaj.mpm.uitls

import android.view.View
import androidx.fragment.app.Fragment
import com.bohrapankaj.mpm.network.Resource
import com.google.android.material.snackbar.Snackbar


fun View.snackbar(message:String, action:(()->Unit)? = null){
    val snackbar = Snackbar.make(this,message, Snackbar.LENGTH_LONG)
    action?.let {
        snackbar.setAction("Retry"){
            it()
        }
    }
    snackbar.show()
}

/*in this function paramter we will pass failure and we will also pass the function to retry the operation*/
fun Fragment.handleApiError(
    failure: Resource.Failure,
    /*this function will not return anything and we are making it null
        so it is not necessary to pass this retry function every time when we handle this hadleApiError function*/
    retry:(()->Unit)? = null
){
    when{
        failure.isNetworkError-> requireView().snackbar("Please check your internet connection",retry)

        //for any other error we will display error body
        else->{
            val error = failure.errorBody?.string().toString()
            requireView().snackbar(error)
        }
    }
}