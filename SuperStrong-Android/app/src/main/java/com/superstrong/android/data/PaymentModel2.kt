package com.superstrong.android.data

import com.google.gson.annotations.SerializedName

class PaymentModel2 {
}

data class PayInfo2(  //POST
    @SerializedName("id")
    val id: String,
    @SerializedName("token")
    val token: String,
    @SerializedName("pub_address")
    val pub_address: String,
    @SerializedName("gas")
    val gas : Double,
    @SerializedName("coin_name")
    val coin_name : String,
    @SerializedName("amount")
    var amount: Double
)
