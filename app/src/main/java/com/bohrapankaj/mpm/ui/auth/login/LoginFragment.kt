package com.bohrapankaj.mpm.ui.auth.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.navigation.fragment.findNavController
import com.bohrapankaj.mpm.base.BaseFragment
import com.bohrapankaj.mpm.databinding.FragmentLoginBinding
import com.bohrapankaj.mpm.network.AuthApi
import com.bohrapankaj.mpm.network.Resource
import com.bohrapankaj.mpm.ui.home.DashboardActivity
import com.bohrapankaj.mpm.uitls.handleApiError
import com.bohrapankaj.mpm.uitls.showToast
import com.bohrapankaj.mpm.uitls.validation


class LoginFragment : BaseFragment<LoginViewModel, FragmentLoginBinding, LoginRepository>() {

    lateinit var emailEt:EditText
    lateinit var passwordEt:EditText

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        emailEt = binding.emailEt
        passwordEt = binding.passwordEt

        observerLoginResponse()


        binding.signInBtn.setOnClickListener {
            login()
        }

        binding.signUpTxt.setOnClickListener {
            val action = LoginFragmentDirections.actionLoginFragmentToSignUpFragment()
            findNavController().navigate(action)
        }

        binding.forgotPasswordTxt.setOnClickListener {
            val action = LoginFragmentDirections.actionLoginFragmentToEmailForgPassFragment()
            findNavController().navigate(action)
        }
    }

    private fun observerLoginResponse() {
            viewModel.loginResponse.observe(viewLifecycleOwner,{
                when(it){
                    is Resource.Success ->{
                        if (it.value.status.equals("1")){
                            showToast(mContext,it.value.message,true)
                            startActivity(Intent(mContext, DashboardActivity::class.java))
                            requireActivity().finish()

                        }else{
                            showToast(mContext,it.value.message,true)
                        }
                    }

                    is Resource.Failure ->handleApiError(it) { login() }
                }
            })
    }



    private fun login() {
        val email = emailEt.text.toString().trim()
        val password = passwordEt.text.toString().trim()

        val emailValidate = validation(email)
        val passwordValidate = validation(password)

        if (emailValidate and passwordValidate){
            viewModel.login(email = email,password = password)
        }else{
            showToast(mContext,"Enter The Credentials",true)
        }
    }

    override fun getViewModel(): Class<LoginViewModel> {
        return LoginViewModel::class.java
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentLoginBinding {
        return FragmentLoginBinding.inflate(inflater,container,false)
    }

    override fun getFragmentRepository(): LoginRepository {
        return LoginRepository(retrofitClient.buildApi(AuthApi::class.java))
    }

}