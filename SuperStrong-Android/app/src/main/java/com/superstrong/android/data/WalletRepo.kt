package com.superstrong.android.data

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.superstrong.android.viewmodel.AES256Util2
import org.json.JSONObject

data class Balance(
    @SerializedName("res")
    val valid:Int,
    @SerializedName("eth")
    val eth:Double,
    @SerializedName("doge")
    val doge:Double,
    @SerializedName("btc")
    val btc:Double
)

data class Id(
    @SerializedName("id")
    val id :String
)

data class History(
    @SerializedName("member_id")
    val id:String="aaaaa",
    @SerializedName("amount")
    val amount:Double = -1.0,
    @SerializedName("coin_name")
    val coin:String = "none",
    @SerializedName("quote")
    val quote:Double = -1.0,
    @SerializedName("gas")
    val gas:Double = -1.0,
    @SerializedName("history_id")
    val hid:Int = 3,
    @SerializedName("interaction_id")
    val interID:String = "SSS",
    @SerializedName("status")
    val status:String = "NONE",
    @SerializedName("date")
    val date:String = "NONE",
    @SerializedName("interaction_address")
    val address:String = "NONE"
)



class WalletRepo :BaseRepo() {
    val retrofitService = RetrofitInstance.backendApiService
    suspend fun getBalance(body: Id, token:String, key:String) :Balance?{
        AES256Util2.init2(key)
        //AES256Util2.init2()
        val req = E2eReq2(AES256Util2.aesEncode(Gson().toJson(body)), token)
        var job = safeApiCall { retrofitService.getBalance(req) }
        val res = job.data

        if(res == null)
            return null
        else
            return Gson().fromJson(AES256Util2.aesDecode(res.encData), Balance::class.java)
    }
    suspend fun getHistory(body: Id, token:String, key:String):JSONObject?{
        AES256Util2.init2(key)
        //AES256Util2.init2()
        val req = E2eReq2(AES256Util2.aesEncode(Gson().toJson(body)), token)
        var job = safeApiCall { retrofitService.getHistory(req) }
        val res = job.data

        if(res == null)
            return null
        else
            return JSONObject(AES256Util2.aesDecode(res.encData))
    }

}