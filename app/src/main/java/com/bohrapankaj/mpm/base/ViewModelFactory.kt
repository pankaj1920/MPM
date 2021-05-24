package com.bohrapankaj.mpm.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bohrapankaj.mpm.ui.auth.forgot_password.change_password.ChangPasswordViewModel
import com.bohrapankaj.mpm.ui.auth.forgot_password.change_password.ChangePasswordRepository
import com.bohrapankaj.mpm.ui.auth.forgot_password.email_fog_pass.EmailFogPassRepository
import com.bohrapankaj.mpm.ui.auth.forgot_password.email_fog_pass.EmailForgPassViewModel
import com.bohrapankaj.mpm.ui.auth.login.LoginRepository
import com.bohrapankaj.mpm.ui.auth.login.LoginViewModel
import com.bohrapankaj.mpm.ui.auth.signup.SignUpRepository
import com.bohrapankaj.mpm.ui.auth.signup.SignUpViewModel

class ViewModelFactory(
    /* here we are using BaseRepository bcz we are creating all our repository with the help of BaseRepository
          * that mean whenever we need to create a repository we will extend the base repository
          * that's y we are passing base repository as constructor parameter to our viewModel factory bcz it is responsible for giving all the view model that are require in our project
          * */
    private val repository: BaseRepository
) : ViewModelProvider.NewInstanceFactory() {
    /*here we will override create function and this function will create our viewmodel*/
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            /*here we will check this modelClass is assignable from our specific ViewModel or not
                * if it is assignable from AuthViewModel then we will Create AuthViewModel */

            modelClass.isAssignableFrom(LoginViewModel::class.java) -> LoginViewModel(repository as LoginRepository) as T

            modelClass.isAssignableFrom(SignUpViewModel::class.java) -> SignUpViewModel(repository as SignUpRepository) as T

            modelClass.isAssignableFrom(EmailForgPassViewModel::class.java) -> EmailForgPassViewModel(
                repository as EmailFogPassRepository
            ) as T

            modelClass.isAssignableFrom(ChangPasswordViewModel::class.java)->ChangPasswordViewModel(repository as ChangePasswordRepository) as T


            /*for every other case we will throw exception*/
            else -> throw IllegalArgumentException("ViewModel not found")
        }
    }
}