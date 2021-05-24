package com.bohrapankaj.mpm.ui.auth.forgot_password.verify_otp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavArgs
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bohrapankaj.mpm.R
import com.bohrapankaj.mpm.base.BaseFragment
import com.bohrapankaj.mpm.databinding.FragmentVerifyOtpFogPassBinding
import com.bohrapankaj.mpm.network.AuthApi
import com.bohrapankaj.mpm.network.Resource
import com.bohrapankaj.mpm.ui.auth.forgot_password.email_fog_pass.EmailFogPassRepository
import com.bohrapankaj.mpm.ui.auth.forgot_password.email_fog_pass.EmailForgPassViewModel
import com.bohrapankaj.mpm.uitls.handleApiError
import com.bohrapankaj.mpm.uitls.showToast
import com.bohrapankaj.mpm.uitls.validation


class VerifyOtpFogPassFragment :
    BaseFragment<EmailForgPassViewModel, FragmentVerifyOtpFogPassBinding, EmailFogPassRepository>() {

    val navArgs: VerifyOtpFogPassFragmentArgs by navArgs()
    lateinit var email:String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        email = navArgs.email

        binding.submitOtp.setOnClickListener {
            val otp = binding.otpEt.text.toString().trim()

            if (validation(email) && validation(otp)){
                val action =
                    VerifyOtpFogPassFragmentDirections.actionVerifyOtpFogPassFragmentToChangePasswordFragment(email=email,otp=otp)
                findNavController().navigate(action)
            }else{
                showToast(mContext,"Enter Otp",true)
            }
        }

        binding.resendEmail.setOnClickListener {
            resendOtp()

            resendOtpObserver()
        }
    }

    private fun resendOtpObserver() {
        viewModel.forgeotPassOtpGenResponse.observe(viewLifecycleOwner,{
            when(it){
                is Resource.Success->{
                    if (it.value.status.equals("1")){
                        showToast(mContext,it.value.message,true)
                    }else{
                        showToast(mContext,it.value.toString(),true)
                    }
                }
                is Resource.Failure -> handleApiError(it)
            }
        })
    }

    private fun resendOtp() {
        viewModel.forgeotPassOtpGenResponse(email)
    }

    override fun getViewModel(): Class<EmailForgPassViewModel> {
        return EmailForgPassViewModel::class.java
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentVerifyOtpFogPassBinding {
        return FragmentVerifyOtpFogPassBinding.inflate(inflater, container, false)
    }

    override fun getFragmentRepository(): EmailFogPassRepository =
        EmailFogPassRepository(retrofitClient.buildApi(AuthApi::class.java))


}