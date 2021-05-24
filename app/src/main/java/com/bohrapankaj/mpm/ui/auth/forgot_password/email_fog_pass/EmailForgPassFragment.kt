package com.bohrapankaj.mpm.ui.auth.forgot_password.email_fog_pass

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.navigation.fragment.findNavController
import com.bohrapankaj.mpm.R
import com.bohrapankaj.mpm.base.BaseFragment
import com.bohrapankaj.mpm.databinding.FragmentEmailForgPassBinding
import com.bohrapankaj.mpm.network.AuthApi
import com.bohrapankaj.mpm.network.Resource
import com.bohrapankaj.mpm.uitls.handleApiError
import com.bohrapankaj.mpm.uitls.showToast
import com.bohrapankaj.mpm.uitls.validation


class EmailForgPassFragment : BaseFragment<EmailForgPassViewModel,FragmentEmailForgPassBinding,EmailFogPassRepository>() {

    lateinit var fogEmailEt:EditText
    lateinit var registerBtn:Button
    lateinit var email:String;

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fogEmailEt = binding.fogEmailEt
        registerBtn = binding.registerBtn



        registerBtn.setOnClickListener {
             email = fogEmailEt.text.toString().trim()

            if (validation(email)){
                generateOTP(email)
                generateOTPObserver()

            }else{
                showToast(mContext,"Enter the mail",true)
            }
        }
    }

    private fun generateOTPObserver() {
        viewModel.forgeotPassOtpGenResponse.observe(viewLifecycleOwner,{
            when(it){
                is Resource.Success->{
                    if(it.value.status.equals("1")){
                        showToast(mContext,it.value.message,true)
                        val action = EmailForgPassFragmentDirections.actionEmailForgPassFragmentToVerifyOtpFogPassFragment(email)
                        findNavController().navigate(action)
                    }else{
                        showToast(mContext,it.value.message,true)
                    }
                }
                is Resource.Failure->handleApiError(it)

            }


        })
    }

    private fun generateOTP(email: String) {
        viewModel.forgeotPassOtpGenResponse(email)
    }


    override fun getViewModel(): Class<EmailForgPassViewModel> {
       return EmailForgPassViewModel::class.java
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentEmailForgPassBinding {
        return FragmentEmailForgPassBinding.inflate(inflater,container,false)
    }

    override fun getFragmentRepository(): EmailFogPassRepository {
        return EmailFogPassRepository(retrofitClient.buildApi(AuthApi::class.java))
    }

}