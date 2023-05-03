package com.superstrong.android.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.superstrong.android.R
import android.content.Intent
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomnavigation.BottomNavigationView


class AccountManageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_manage)
        val chpass = findViewById<TextView>(R.id.manage_chpass)
        val intentChpass = Intent(this,ChpasswdActivity::class.java)

        val withdrawal = findViewById<TextView>(R.id.manage_withdrawal)
        val intentWithdrawal = Intent(this,ChpasswdActivity::class.java)

        val qna = findViewById<TextView>(R.id.manage_qna)
        val intentQna = Intent(this,ChpasswdActivity::class.java)
        val bottomNavigationView: BottomNavigationView = findViewById(R.id.navigationView)
        bottomNavigationView.setOnNavigationItemSelectedListener(MyNavigationListener(this))
        chpass.setOnClickListener {
            startActivity(intentChpass)
        }
        withdrawal.setOnClickListener {
            startActivity(intentWithdrawal)
        }
        qna.setOnClickListener {
            startActivity(intentQna)
        }
    }
}