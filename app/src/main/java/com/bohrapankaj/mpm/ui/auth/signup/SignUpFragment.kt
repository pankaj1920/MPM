package com.bohrapankaj.mpm.ui.auth.signup

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.bohrapankaj.mpm.R
import com.bohrapankaj.mpm.base.BaseFragment
import com.bohrapankaj.mpm.databinding.FragmentSignUpBinding
import com.bohrapankaj.mpm.network.AuthApi
import com.bohrapankaj.mpm.network.Resource
import com.bohrapankaj.mpm.uitls.checkPassword
import com.bohrapankaj.mpm.uitls.handleApiError
import com.bohrapankaj.mpm.uitls.showToast
import com.bohrapankaj.mpm.uitls.validation


class SignUpFragment : BaseFragment<SignUpViewModel, FragmentSignUpBinding, SignUpRepository>() {

    lateinit var signUpBtn: Button

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        signUpBtn = binding.signUpBtn



        binding.loginTxt.setOnClickListener {
            val action = SignUpFragmentDirections.actionSignUpFragmentToLoginFragment()
            findNavController().navigate(action)
        }

        signUpBtn.setOnClickListener {
            val email = binding.signUpEmailEt.text.toString().trim()
            val password = binding.signUpPasswordEt.text.toString().trim()
            val cPassword = binding.signUpCPasswordEt.text.toString().trim()

            if (validation(email) && validation(password) && validation(cPassword)) {
                if (checkPassword(password = password, cPassword = cPassword)) {
                    signUp(email, password)

                    observerSignupResponse()
                } else {
                    showToast(mContext, "Password not matched", true)
                }
            } else {
                showToast(mContext, "Enter the data", true)
            }
        }

    }

    private fun observerSignupResponse() {
        viewModel.signUpResponse.observe(viewLifecycleOwner,{
                when(it){
                    is Resource.Success->{
                        if (it.value.status.equals("1")){
                            showToast(mContext,it.value.message,true)
                            val action = SignUpFragmentDirections.actionSignUpFragmentToLoginFragment()
                            findNavController().navigate(action)
                        }else{
                            showToast(mContext,it.value.message,true)
                        }
                    }

                    is Resource.Failure ->handleApiError(it)
                }
        })
    }

    private fun signUp(email: String, password: String) {

        viewModel.signup(email = email, password = password)
    }

    override fun getViewModel(): Class<SignUpViewModel> {
        return SignUpViewModel::class.java
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSignUpBinding {
        return FragmentSignUpBinding.inflate(inflater, container, false)
    }

    override fun getFragmentRepository(): SignUpRepository {
        return SignUpRepository(retrofitClient.buildApi(AuthApi::class.java))
    }
}