package com.bohrapankaj.mpm.ui.auth.forgot_password.email_fog_pass

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.bohrapankaj.mpm.base.BaseViewModel
import com.bohrapankaj.mpm.network.Resource
import com.bohrapankaj.mpm.response.common.CommonModel
import kotlinx.coroutines.launch

class EmailForgPassViewModel(
    private val repository: EmailFogPassRepository
) : BaseViewModel(repository) {

    private val _forgeotPassOtpGenResponse: MutableLiveData<Resource<CommonModel>> = MutableLiveData()
    val forgeotPassOtpGenResponse: LiveData<Resource<CommonModel>>
        get() = _forgeotPassOtpGenResponse


    fun forgeotPassOtpGenResponse(email:String){
        viewModelScope.launch {
            _forgeotPassOtpGenResponse.value = Resource.Loading
            _forgeotPassOtpGenResponse.value = repository.forgetPassOtpGenerate(email)
        }
    }
}