package com.superstrong.android.viewmodel
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.superstrong.android.data.User
import com.superstrong.android.data.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.content.Intent
import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.superstrong.android.data.EncryptedData
import com.superstrong.android.view.WalletActivity
import org.json.JSONObject

class LoginViewModel : ViewModel() {
    val username = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    fun PostLogin(username: String, password: String,context: Context) {
        val sharedPref = context.getSharedPreferences("strong", Context.MODE_PRIVATE)
        val user = User(username, password) // 전송할 데이터 모델 객체 생성
        val encryptedUser = EncryptedData(AES256Util1.aesEncode(Gson().toJson(user)))
        val call = RetrofitInstance.backendApiService.login(encryptedUser) // POST 요청 보내기
        call.enqueue(object : Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()?.toString()
                    Log.i("rww","response"+responseBody)
                    val jsonObject = Gson().fromJson(responseBody, JsonObject::class.java)
                    Log.i("rww","json0bject:"+jsonObject)
                    val data= jsonObject.get("e2e_res").asString
                    Log.i("rww","data:"+data)
                    val decoded_data=AES256Util1.aesDecode(data)
                    Log.i("rww","decoded_data:"+decoded_data)
                    val jsonObject2 = Gson().fromJson(decoded_data, JsonObject::class.java)
                    Log.i("rww","jsonObject2:"+jsonObject2)
                    if (jsonObject2.get("token").asString == "login failed") {
                        // ---------------------로그인 실패 시 처리할 코드---------------------
                        Toast.makeText(context, "로그인이 실패했습니다.", Toast.LENGTH_SHORT).show()
                    } else {
                        // ---------------------로그인 성공 시 처리할 코드---------------------
                        with(sharedPref.edit()) {
                            putString("jwt_token", JSONObject(decoded_data).getString("token"))
                            putString("ID", username)
                            apply()
                        }
                        val keyname = sharedPref.getString("key_id","")
                        if(username != keyname)
                            Log.i("kkkkkkkkkkkkkkkkkkkkkkkkkkkkeykeyekyeykeykeye", "require key")

                        val intent = Intent(context, WalletActivity::class.java)
                        context.startActivity(intent)
                    }
                } else {
                    // ---------------------응답값 받기 실패---------------------
                    val errorBody = response.errorBody()?.toString()
                    Toast.makeText(context, "응답이 없습니다", Toast.LENGTH_SHORT).show()
                }
            }
            // ---------------------통신 실패 코드---------------------
            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                Toast.makeText(context, "통신 실패", Toast.LENGTH_SHORT).show()
            }
        })
    }}