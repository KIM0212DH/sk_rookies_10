package com.superstrong.android.view

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.superstrong.android.R
import com.superstrong.android.databinding.ActivityPayment2Binding
import com.superstrong.android.viewmodel.PaymentGetVModel
import com.superstrong.android.viewmodel.PaymentVModel
import com.superstrong.android.viewmodel.PaymentVModel2
import org.json.JSONObject

class PaymentActivity2 : AppCompatActivity() {

    private var coin_name: String? = ""
    private lateinit var binding: ActivityPayment2Binding
    private lateinit var paymentVModel: PaymentVModel
    private lateinit var paymentVModel2: PaymentVModel2
    private lateinit var paymentGetVModel: PaymentGetVModel



    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment2)
        paymentVModel2 = ViewModelProvider(this).get(PaymentVModel2::class.java)
        paymentGetVModel = ViewModelProvider(this).get(PaymentGetVModel::class.java)

        // 네비게이션
        val bottomNavigationView: BottomNavigationView = findViewById(R.id.navigationView)
        bottomNavigationView.setOnNavigationItemSelectedListener(MyNavigationListener(this))

        // 바인딩
        binding = ActivityPayment2Binding.inflate(layoutInflater)
        setContentView(binding.root)

// --------------- 서버에서 정보 GET -------------------------------------------------------------------------------------------


        // 지갑의 주소 받기
        val to_address: String? = intent.getStringExtra("to_address")

        // 코인의 수량 받기
        //val send_amount = intent.getDoubleExtra("ToCoinQuan")
        val send_amount: Double? = intent.getDoubleExtra("send_amount", 0.0)
        //val send_amount: Double? = paymentGetVModel.sendamount.value
        //Log.d("Send Amount","전송 수량 !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! - " + send_amount)

        // 코인의 이름 받기
        //val coin_name: String? = paymentGetVModel.coinname.value
        val coin_name: String? = intent.getStringExtra("coin_name")

        // 코인의 가스 비용 받기
        // val calculated_gas: Double? = paymentGetVModel.circulated_gas.value
        val calculated_gas: Double? = intent.getDoubleExtra("calculated_gas", 0.0)

        // 코인의 잔금 받기
        //val remain_amount: Double? = paymentGetVModel.remain_amount.value
        val remain_amount: Double? = intent.getDoubleExtra("remain_amount", 0.0)

        Log.d("**************************************************** To Address","to address: "+to_address)
        Log.d("***************************************************** To Address* Send Amount","send amount: "+send_amount)
        Log.d("***************************************************** To Address* Coin name","coin name: "+coin_name)
        Log.d("***************************************************** To Address* Calculated Gas","calculated_gas: "+calculated_gas)
        Log.d("***************************************************** To Address* Remain Amount","remain_amount: "+remain_amount)

        binding.payAddress.setText(to_address)

        binding.coinPay.setText(send_amount.toString())

        binding.coin1.setText(coin_name)
        binding.coin2.setText(coin_name)
        binding.coin3.setText(coin_name)

        binding.coinGas.setText(calculated_gas.toString())

        binding.coinBalance.setText(remain_amount.toString())


//        if (to_address != null && send_amount != null && coin_name != null && circulated_gas != null && remain_amount != null) {
//            paymentGetVModel.GetPayment(to_address, send_amount, coin_name, circulated_gas, remain_amount, this)
//            startActivity(intent)
//
//        } else {
//            Log.d("*서버에서가져옴* To Address","to address: "+to_address)
//            Log.d("*서버에서가져옴* Send Amount","send amount: "+send_amount)
//            Log.d("*서버에서가져옴* Coin name","coin name: "+coin_name)
//            Log.d("*서버에서가져옴* Circulated Gas","circulated_gas: "+circulated_gas)
//            Log.d("*서버에서가져옴* Remain Amount","remain_amount: "+remain_amount)
//            Toast.makeText(this, "오류!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!", Toast.LENGTH_SHORT).show()
//        }


//-----------------------------------------------------------------------------------------------------------------

        // 툴바 뒤로가기 버튼
        binding.btnBack.setOnClickListener {
            val intent = Intent(this, PaymentActivity::class.java)
            startActivity(intent)
            finish()
        }

// ----------------- 서버에 정보 POST ------------------------------------------------------------------------------

        binding.nextButton.setOnClickListener {
            val intent = Intent(this, PaymentActivity3::class.java)

            val sharedPref = getSharedPreferences("strong", Context.MODE_PRIVATE)
            //var token = sharedPref.getString("jwt_token","")

            var tokendjdjdjdj = sharedPref.getString("jwt_token","")
            val jsonObject = JSONObject()
            jsonObject.put("token",tokendjdjdjdj)
            var token = jsonObject.getString("token")


            var id = sharedPref.getString("ID", "")
            if(token==null){
                token = "nonetoken"
            } else if(id==null){
                id = "noneid"
            }

            Log.d("토크ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㄴ", ": "+ token)
            Log.d("아이디ㅣㅣㅣㅣㅣㅣㅣㅣㅣㅣㅣㅣㅣㅣㅣㅣㅣㅣㅣㅣㅣㅣㅣㅣㅣㅣㅣㅣㅣㅣㅣ", ": "+ id)

            Log.d("To Address","to address: "+to_address)
            Log.d("Send Amount", "send amount: "+ send_amount)
            Log.d("Coin name","coin name: "+coin_name)
            Log.d("Calculated Gas","calculated_gas: "+calculated_gas)
            Log.d("Remain Amount","remain_amount: "+remain_amount)
            Log.d("Token","token: "+token)

            if (id != null && token != null && to_address != null && calculated_gas != null && coin_name != null && send_amount != null) {
                paymentVModel2.PostPayment(id, token, to_address, calculated_gas, coin_name, send_amount, this)

            } else {
                Toast.makeText(this, "Payment 2에서 3으로 서버에 정보 POST 하는 과정에서 null값이 포함되어있습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

}
