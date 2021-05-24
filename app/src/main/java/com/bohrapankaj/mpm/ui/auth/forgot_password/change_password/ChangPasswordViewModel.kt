package com.bohrapankaj.mpm.ui.auth.forgot_password.change_password

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.bohrapankaj.mpm.base.BaseViewModel
import com.bohrapankaj.mpm.network.Resource
import com.bohrapankaj.mpm.response.common.CommonModel
import kotlinx.coroutines.launch

class ChangPasswordViewModel(
    val repository: ChangePasswordRepository
) : BaseViewModel(repository) {

    private val _changePasswordResponse: MutableLiveData<Resource<CommonModel>> = MutableLiveData()

    val changePasswordResponse: LiveData<Resource<CommonModel>>
        get() = _changePasswordResponse

    fun updatePassword(email: String, password: String, otp: String) {
        viewModelScope.launch {
            _changePasswordResponse.value = Resource.Loading
            _changePasswordResponse.value = repository.updatePassword(email, password, otp)
        }
    }
}