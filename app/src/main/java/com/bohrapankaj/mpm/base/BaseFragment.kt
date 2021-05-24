package com.bohrapankaj.mpm.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.bohrapankaj.mpm.network.RetrofitClient


/* as this a fragment we will extend th fragment calss to this class
* and for all our main fragment we will extend this base fragment
* for every fragment we have something common eg ViewModel DataBinding and Repository for every fragment
* here we define BaseRepository bcz we will extend this base reposity in all our repository */
abstract class BaseFragment<VM : BaseViewModel, B : ViewBinding, R : BaseRepository> : Fragment() {

    lateinit var binding:B
    lateinit var viewModel:VM
    protected val retrofitClient = RetrofitClient()
    protected lateinit var mContext: Context

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = getFragmentBinding(inflater,container)
        mContext = requireContext()

        /*here we will create the viewModel but first thing we need is ViewModelFactory Instance to create viewModel
      * in ViewModelFactory we need to pass the repository for that we have fun getFragmentRepository() to get actual repository for our fragment
      * with the help of this factory we can create our viewModel instance*/
        val factory = ViewModelFactory(getFragmentRepository())
        viewModel = ViewModelProvider(this,factory).get(getViewModel())

        return binding.root
    }

    //this function will return us the class of type viewmodel
    abstract fun getViewModel(): Class<VM>

    // to this fun we will pass inflater and container bcz these are required to inflate the viewBinding
    // this fun will return us B i.e binding
    abstract fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?): B

    //it will return R i.e Repository
    abstract fun getFragmentRepository(): R
}