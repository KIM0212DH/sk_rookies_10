package com.superstrong.android.view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.superstrong.android.R
import com.superstrong.android.databinding.ActivityPaymentBinding
import com.superstrong.android.databinding.ActivitySwapBinding

class SwapActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySwapBinding

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_swap)

        binding = ActivitySwapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 툴바 뒤로가기 버튼
        binding.btnBack.setOnClickListener {
            val intent = Intent(this, WalletActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.confirmButton.setOnClickListener {
            val intent = Intent(this, SwapActivity2::class.java)
            startActivity(intent)
            finish()
        }
    }

}