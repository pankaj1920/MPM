package com.bohrapankaj.mpm.ui.auth.forgot_password.change_password

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.navigation.NavArgs
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bohrapankaj.mpm.R
import com.bohrapankaj.mpm.base.BaseFragment
import com.bohrapankaj.mpm.databinding.FragmentChangePasswordBinding
import com.bohrapankaj.mpm.network.AuthApi
import com.bohrapankaj.mpm.network.Resource
import com.bohrapankaj.mpm.uitls.checkPassword
import com.bohrapankaj.mpm.uitls.handleApiError
import com.bohrapankaj.mpm.uitls.showToast
import com.bohrapankaj.mpm.uitls.validation


class ChangePasswordFragment :
    BaseFragment<ChangPasswordViewModel, FragmentChangePasswordBinding, ChangePasswordRepository>() {


    lateinit var password: String
    lateinit var cPassword: String
    var email = ""
    var otp = ""
    val navArgs: ChangePasswordFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        email = navArgs.email
        otp = navArgs.otp

        binding.resetPassbtn.setOnClickListener {
            password = binding.rPasswordEt.text.toString().trim()
            cPassword = binding.rConfirmPasswordEt.text.toString().trim()

            if (validation(password) && validation(cPassword)) {

                if (checkPassword(password, cPassword)) {
                    updatePassword(email, password, otp)
                    updatePasswordObserver()
                } else {
                    showToast(mContext, "Password did not matched", true)
                }
            } else {
                showToast(mContext, "Enter the Password", true)
            }
        }

    }

    private fun updatePasswordObserver() {

        viewModel.changePasswordResponse.observe(viewLifecycleOwner, {
            when(it) {
                is Resource.Success -> {
                    if (it.value.status.equals("1")) {
                        showToast(mContext, it.value.message, true)
                        val action =
                            ChangePasswordFragmentDirections.actionChangePasswordFragmentToLoginFragment2()
                        findNavController().navigate(action)
                    } else {
                        showToast(mContext, it.value.message, true)
                    }
                }

                is Resource.Failure -> handleApiError(it)

            }
        })
    }

    private fun updatePassword(email: String, password: String, otp: String) {
        viewModel.updatePassword(email, password, otp)
    }

    override fun getViewModel(): Class<ChangPasswordViewModel> {
        return ChangPasswordViewModel::class.java
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentChangePasswordBinding {
        return FragmentChangePasswordBinding.inflate(inflater, container, false)
    }

    override fun getFragmentRepository(): ChangePasswordRepository {
        return ChangePasswordRepository(retrofitClient.buildApi(AuthApi::class.java))
    }


}