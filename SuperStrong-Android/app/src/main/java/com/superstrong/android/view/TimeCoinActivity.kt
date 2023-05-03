package com.superstrong.android.view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.superstrong.android.R
import com.superstrong.android.databinding.ActivitySwapBinding
import com.superstrong.android.databinding.ActivityTimecoinBinding

class TimeCoinActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTimecoinBinding

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timecoin)

        binding = ActivityTimecoinBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 툴바 뒤로가기 버튼
        binding.btnBack.setOnClickListener {
            val intent = Intent(this, WalletActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.nextButton.setOnClickListener {
            val intent = Intent(this, WalletActivity::class.java)
            startActivity(intent)
            finish()
        }


    }

}