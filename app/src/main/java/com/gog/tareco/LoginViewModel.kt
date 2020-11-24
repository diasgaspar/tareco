package com.gog.tareco

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel(){

    private val _loginResult = MutableLiveData<Boolean>()
    val loginResultLiveData = _loginResult
    fun areCredentialValid(username: String, password: String) {

        loginResultLiveData.postValue(username == password)

    }




}