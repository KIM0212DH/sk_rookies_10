package com.superstrong.android.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.superstrong.android.data.Balance
import com.superstrong.android.data.History
import com.superstrong.android.data.Id
import com.superstrong.android.data.WalletRepo
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject

class LogVModel : ViewModel(){
    var balance = MutableLiveData<Balance>(Balance(0,0.0,0.0,0.0))
    var historyData = JSONArray(arrayOf(JSONObject(Gson().toJson(History()))))
    var repo = WalletRepo()
    var loading = MutableLiveData<Boolean>(false)
    var valid = MutableLiveData<Int>(1)

    fun getData(id:String, token:String, key:String){
        loading.value = true
        viewModelScope.launch {
            var job1 = async { repo.getBalance(Id(id), token, key)}
            var job2 = async { repo.getHistory(Id(id), token, key)}
            val bal = job1.await()
            val history = job2.await()
            balance.value=bal?:Balance(-1,-1.0,-1.0,-1.0)

            valid.value = history?.getString("res")?.toInt() ?: -1


            if(valid.value == 1)
                historyData = history!!.getJSONArray("history")
            else
                historyData = JSONArray(arrayOf(JSONObject(Gson().toJson(History()))))

            loading.value = false

        }
    }

}