package com.bohrapankaj.mpm.uitls

import android.content.Context
import android.util.Log
import android.widget.EditText


fun showToast(context: Context, message: String, visibile: Boolean) {
    if (visibile) {
        android.widget.Toast.makeText(context, message, android.widget.Toast.LENGTH_SHORT).show()
    }

}

fun showLog(tag: String, message: String) {
    Log.d(tag, message)
}

fun validation(value: String): Boolean {
    return !value.isEmpty()
}

fun checkPassword(password:String,cPassword:String):Boolean{
    return password.equals(cPassword)
}