package com.bohrapankaj.mpm.ui.auth.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.bohrapankaj.mpm.base.BaseRepository
import com.bohrapankaj.mpm.base.BaseViewModel
import com.bohrapankaj.mpm.network.AuthApi
import com.bohrapankaj.mpm.network.Resource
import com.bohrapankaj.mpm.response.common.CommonModel
import kotlinx.coroutines.launch

class LoginViewModel(
    private val repository: LoginRepository
) : BaseViewModel(repository) {

    private val _loginResponse: MutableLiveData<Resource<CommonModel>> = MutableLiveData()

    val loginResponse: LiveData<Resource<CommonModel>>
        get() = _loginResponse

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _loginResponse.value = Resource.Loading
            _loginResponse.value = repository.login(email = email, password = password)
        }
    }
}