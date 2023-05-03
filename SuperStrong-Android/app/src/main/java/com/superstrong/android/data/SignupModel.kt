package com.superstrong.android.data



import android.util.Log
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.superstrong.android.viewmodel.AES256Util1
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope


data class SignUpRequestBody(
    @SerializedName("id")
    val id:String,
    @SerializedName("pw")
    val pw:String,
    @SerializedName("email")
    val email:String,
    @SerializedName("phone_num")
    val phone_num: String
)

data class SignUpResponseBody(
    @SerializedName("res")
    val result: String,
)

data class AuthCode(
    @SerializedName("code")
    var code:String
)

data class UserData(
    @SerializedName("res") // 맞으면 1 틀리면 0
    var result: String,
    @SerializedName("private_key") // 거래시 사용할 키
    var key:String,
    @SerializedName("pub_address") // 지갑 주소
    var pubAddress:String,
    @SerializedName("id") // 사용자 id
    var id:String
)



class Repository :BaseRepo() {
    val retrofitService = RetrofitInstance.backendApiService
    //suspend fun signupRequest(body: SignUpRequestBody) : Resource<SignUpResponseBody> = safeApiCall{retrofitService.signUp(body)}
    suspend fun signupRequest(body: SignUpRequestBody) : SignUpResponseBody?{
        var gson = Gson()
        val jsonString = gson.toJson(body)
        val edata = E2eReq(AES256Util1.aesEncode(jsonString))
        val job = safeApiCall { retrofitService.signUp(edata) }
        val res = job.data

        if(res == null)
            return null
        else
           return gson.fromJson(AES256Util1.aesDecode(res.encData), SignUpResponseBody::class.java)
    }

    suspend fun sendCode(body: AuthCode) : UserData? {
        var gson = Gson()
        val jsonString = gson.toJson(body)
        val edata = E2eReq(AES256Util1.aesEncode(jsonString))
        val job = safeApiCall { retrofitService.emailAuth(edata) }
        val res = job.data

        if(res == null)
            return null
        else
            return gson.fromJson(AES256Util1.aesDecode(res.encData), UserData::class.java)
    }
}

