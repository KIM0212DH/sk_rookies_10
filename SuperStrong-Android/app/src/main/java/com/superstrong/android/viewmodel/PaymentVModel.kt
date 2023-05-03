package com.superstrong.android.viewmodel

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.superstrong.android.data.*
import com.superstrong.android.view.LoginActivity
import com.superstrong.android.view.PaymentActivity2
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PaymentVModel : ViewModel() {

    val toaddress = MutableLiveData<String>()
    val sendamount = MutableLiveData<Double>()
    val coinname = MutableLiveData<String>()
    val token = MutableLiveData<String>()

    fun PostPayment(to_address: String, send_amount: Double, coin_name: String, token: String, id: String, context: Context) {
        val payInfo1 = PayInfo1(to_address, send_amount, coin_name, "", id) // 전송할 데이터 모델 객체 생성

        // AES256Util 클래스 초기화
        AES256Util2.init(context)

        val encryptedPayment = EncryptedPayment(AES256Util2.aesEncode(Gson().toJson(payInfo1)), token)
        val call = RetrofitInstance.backendApiService.payment1(encryptedPayment) // POST 요청 보내기

        call.enqueue(object : Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()?.toString()
                    val jsonObject = Gson().fromJson(responseBody, JsonObject::class.java)
                    Log.i("리스폰스 (Payment1)","reponse:"+responseBody)

                    val data= jsonObject.get("e2e_res").asString

                    // 복호화
                    val decoded_data=AES256Util2.aesDecode(data)
                    Log.i("rww","decoded_data:"+decoded_data)
                    val jsonObject2 = Gson().fromJson(decoded_data, JsonObject::class.java)
                    Log.i("rww","jsonObject2:"+jsonObject2)

                    if (jsonObject2.get("res").asString == "0" ) {
                        Toast.makeText(context, "송금에 실패했습니다.", Toast.LENGTH_SHORT).show()

                    } else if(jsonObject2.get("res").asString == "2" ){
                        Toast.makeText(context, "토큰이 유효하지 않습니다.", Toast.LENGTH_SHORT).show()
                        val intentLogin = Intent(context, LoginActivity::class.java)
                        context.startActivity(intentLogin)
                    }
                    else {
                        val intent = Intent(context, PaymentActivity2::class.java)
                        intent.putExtra("calculated_gas",jsonObject2.get("calculated_gas").asDouble)
                        intent.putExtra("send_amount",jsonObject2.get("send_amount").asDouble)
                        intent.putExtra("coin_name",jsonObject2.get("coin_name").asString)
                        intent.putExtra("to_address",jsonObject2.get("to_address").asString)
                        intent.putExtra("remain_amount",jsonObject2.get("remain_amount").asDouble)
                        context.startActivity(intent)
                    }
                } else {
                    val errorBody = response.errorBody()?.toString()
                    Toast.makeText(context, "response 실패!!", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                Toast.makeText(context, "통신 실패: PaymentVModel", Toast.LENGTH_SHORT).show()
            }
        })
    }


}
