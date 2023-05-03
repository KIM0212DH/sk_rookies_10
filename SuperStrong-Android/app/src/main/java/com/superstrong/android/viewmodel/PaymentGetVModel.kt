package com.superstrong.android.viewmodel

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.superstrong.android.data.PayInfoRsp1
import com.superstrong.android.data.RetrofitInstance
import com.superstrong.android.view.PaymentActivity2
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PaymentGetVModel : ViewModel()  {

    val toaddress = MutableLiveData<String>()
    val sendamount = MutableLiveData<Double>()
    val coinname = MutableLiveData<String>()
    val circulated_gas = MutableLiveData<Double>()
    val remain_amount  = MutableLiveData<Double>()


    fun GetPayment(to_address: String, send_amount: Double, coin_name: String, circulated_gas: Double, remain_amount: Double, context: Context) {
        val payInfoRsp1 = PayInfoRsp1(to_address, send_amount, coin_name,circulated_gas,remain_amount) // 전송할 데이터 모델 객체 생성
        Log.d("서버에서 들고옴!!!!!리스폰ㄴ스으으으으으으으으으으으으으", "ㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇ" + payInfoRsp1)
        val call = RetrofitInstance.backendApiService.paymentRsp1(payInfoRsp1) // GET 요청 보내기

        call.enqueue(object : Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
//                if (response.isSuccessful) {
//                    val responseBody = response.body()?.toString()
//                    val jsonObject = Gson().fromJson(responseBody, JsonObject::class.java)
//                    Log.i("리스폰스","reponse:"+responseBody)
//                    if (jsonObject.get("").asString == "invalid") {
//                        Toast.makeText(context, "GET 송금에 실패했습니다.", Toast.LENGTH_SHORT).show()
//                    } else {
//                        val intent = Intent(context, PaymentActivity2::class.java)
//                        context.startActivity(intent)
//                    }
//
//
//                } else {
//                    val errorBody = response.errorBody()?.toString()
//                    Toast.makeText(context, "GET 송금에 실패했습니다.", Toast.LENGTH_SHORT).show()
//                }
                val intent = Intent(context, PaymentActivity2::class.java)
                context.startActivity(intent)
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                Toast.makeText(context, "통신 실패: PaymentGetVModel", Toast.LENGTH_SHORT).show()

            }
        })
    }
}