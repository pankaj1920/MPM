package com.bohrapankaj.mpm.ui.auth.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.bohrapankaj.mpm.base.BaseViewModel
import com.bohrapankaj.mpm.network.Resource
import com.bohrapankaj.mpm.response.common.CommonModel
import kotlinx.coroutines.launch

class SignUpViewModel(
    private val repository: SignUpRepository
) : BaseViewModel(repository) {

    private val _signUpResponse: MutableLiveData<Resource<CommonModel>> = MutableLiveData()
    val signUpResponse: LiveData<Resource<CommonModel>>
        get() = _signUpResponse

    fun signup(email: String, password: String) {
        viewModelScope.launch {
            _signUpResponse.value = Resource.Loading
            _signUpResponse.value = repository.register(email = email,password = password)
        }
    }
}