package com.bohrapankaj.mpm.base

import androidx.lifecycle.ViewModel

// we are making it abstract bcz we will extend this ViewModel to all other Viewmodel classs
abstract class BaseViewModel(
    val baseRepository: BaseRepository
):ViewModel() {
    //here we can call any global api which will be in all viewmodel
}