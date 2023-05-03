package com.superstrong.android.data

import com.google.gson.annotations.SerializedName

class PaymentModel3 {
}

data class PayInfoRsp1( // GET
    @SerializedName("to_address")
    val to_address: String,
    @SerializedName("send_amount")
    val send_amount: Double,
    @SerializedName("coin_name")
    val coin_name: String,
    @SerializedName("circulated_gas")
    val circulated_gas : Double,
    @SerializedName("remain_amount")
    val remain_amount : Double
)
