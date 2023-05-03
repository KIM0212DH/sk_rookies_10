package com.superstrong.android.data
import android.content.Context
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.superstrong.android.databinding.ActivityLoginBinding
import com.superstrong.android.view.LoginActivity

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("id")
    val id: String,
    @SerializedName("pw")
    val pw: String
)

data class EncryptedData(
    @SerializedName("e2e_req")
    val e2e_req: String
    )