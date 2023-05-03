package com.superstrong.android.data

import com.google.gson.annotations.SerializedName

data class Password(
    @SerializedName("pass")
    var pass: String
        )

data class ChpassReponse(
    @SerializedName("res")
    var result: Int
)
class ChpasswdModel(_jwt:String) :BaseRepo() {
    val jwt = _jwt
    val retrofitService = RetrofitInstance.backendApiService
    suspend fun checkPassword(pass:String) : Resource<ChpassReponse> = safeApiCall { retrofitService.chPass(body = Password(pass)) }

    suspend fun newPassword(pass:String) : Resource<ChpassReponse> = safeApiCall { retrofitService.newPass(body = Password(pass)) }
}