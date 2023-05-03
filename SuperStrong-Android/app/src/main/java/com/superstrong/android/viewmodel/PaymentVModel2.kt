package com.superstrong.android.viewmodel

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.superstrong.android.data.*
import com.superstrong.android.view.LoginActivity
import com.superstrong.android.view.PaymentActivity3
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PaymentVModel2 : ViewModel() {
    val id = MutableLiveData<String>()
    val token = MutableLiveData<String>()
    val pub_address = MutableLiveData<String>()
    val gas = MutableLiveData<Double>()
    val coin_name = MutableLiveData<String>()
    val amount = MutableLiveData<Double>()

    fun PostPayment(id: String, token: String, pub_address: String, gas: Double, coin_name: String, amount:Double, context: Context) {
        val payInfo2 = PayInfo2(id, "", pub_address, gas, coin_name, amount) // 전송할 데이터 모델 객체 생성
        Log.d("액티비티 2에서 3으로 넘어갈때!!! 넘기는 값들 !!!!!!!!", "df" + payInfo2)
        val encryptedPayment = EncryptedPayment(AES256Util2.aesEncode(Gson().toJson(payInfo2)), token)
        val call = RetrofitInstance.backendApiService.payment2(encryptedPayment) // POST 요청 보내기

        // AES256Util 클래스 초기화
        AES256Util2.init(context)

        call.enqueue(object : Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                if (response.isSuccessful) {

                    val responseBody = response.body()?.toString()
                    val jsonObject = Gson().fromJson(responseBody, JsonObject::class.java)

                    val data= jsonObject.get("e2e_res").asString
                    Log.i("rww","data:"+data)
                    Log.i("리스폰스 (Payment2)","reponse:"+responseBody)

                    val decoded_data=AES256Util2.aesDecode(data)
                    Log.i("rww","decoded_data:"+decoded_data)

                    val jsonObject2 = Gson().fromJson(decoded_data, JsonObject::class.java)
                    Log.i("rww","jsonObject2:"+jsonObject2)

                    Log.i("리스폰스","reponse:"+responseBody)

                    if (jsonObject2.get("res").asString == "0" || jsonObject2.get("res").asString == null ) {
                        Toast.makeText(context, "송금에 실패했습니다.", Toast.LENGTH_SHORT).show()
                    } else if(jsonObject2.get("res").asString == "2"){
                        Toast.makeText(context, "유효하지 않은 토큰입니다.", Toast.LENGTH_SHORT).show()
                        val intentLogin = Intent(context, LoginActivity::class.java)
                        context.startActivity(intentLogin)
                    }
                    else {
                        val intent = Intent(context, PaymentActivity3::class.java)
                        context.startActivity(intent)
                    }

                    Log.i("리스폰스","reponse:"+responseBody)
                } else {
                    val errorBody = response.errorBody()?.toString()
                    Toast.makeText(context, "송금에 실패했습니다.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                Toast.makeText(context, "통신 실패: PaymentVModel2", Toast.LENGTH_SHORT).show()
            }
        })
    }}