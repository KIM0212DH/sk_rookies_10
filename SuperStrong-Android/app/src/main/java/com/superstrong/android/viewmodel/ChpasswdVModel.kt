package com.superstrong.android.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.superstrong.android.data.ChpasswdModel
import kotlinx.coroutines.launch


class ChpasswdVModel() : ViewModel(){
    var jwt = "fgdfgdg"
    var wrongPasswd = MutableLiveData<Int>(0)
    var model = ChpasswdModel(jwt)
    var stage = MutableLiveData<Int>(1)
    var samePass = MutableLiveData<Boolean>(false)
    var done = MutableLiveData<Boolean>(false)

    fun back(){
        stage.value = stage.value!! - 1
    }
    fun checkCurrentPassword(pass:String){
        /*val ret = model.checkPassword(pass)
        wrongPasswd.value = !ret
        if(ret)
            stage.value=2*/
        viewModelScope.launch {
            val ret = model.checkPassword(pass).data
            wrongPasswd.value = ret?.result?: 1
            if(wrongPasswd.value != 1)
                stage.value = 2
        }
    }
    fun newpass(pass1:String, pass2:String){
        samePass.value = (pass1 == pass2)
        if(pass1 == pass2){
            viewModelScope.launch {
                val ret = model.newPassword(pass1).data
                val rr = ret?.result ?: 0
                if (rr != 0)
                    stage.value = 3
            }
        }
    }
    fun relogin(){
        done.value=true
    }
}