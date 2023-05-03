package com.superstrong.android.view

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayoutMediator
import com.superstrong.android.R
import com.superstrong.android.databinding.ActivityWalletBinding
import com.superstrong.android.viewmodel.LogVModel
import com.superstrong.android.viewmodel.TabFragmentAdapter

class WalletActivity : FragmentActivity()  {

//    private lateinit var binding: ActivityWalletBinding
    private val tabTitles:Array<String> = arrayOf("잔금", "거래내용")
    lateinit var binding : ActivityWalletBinding
    lateinit var viewModel : LogVModel

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWalletBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //setContentView(R.layout.activity_wallet)
        viewModel = ViewModelProvider(this).get(LogVModel::class.java)
        binding.viewModel = viewModel
        val dialog = LoadingDialog(this)
        val bottomNavigationView: BottomNavigationView = findViewById(R.id.navigationView)
        bottomNavigationView.setOnNavigationItemSelectedListener(MyNavigationListener(this))
        val pagerAdapter = TabFragmentAdapter(this)

        val pager = binding.viewPager
        pager.isNestedScrollingEnabled = true
        pager.adapter = pagerAdapter

        val sharedPref = getSharedPreferences("strong", Context.MODE_PRIVATE)
        val id = sharedPref.getString("ID","")
        val pub_ad = sharedPref.getString("pub_ad","")
        val token = sharedPref.getString("jwt_token","")
        val key = sharedPref.getString("key","")
        Log.d("kkkkkkkkkkkkkkkkkkkk",id!!)
        Log.d("kkkkkkkkkkkkkkkkkkkk",token!!)
        Log.d("kkkkkkkkkkkkkkkkkkkk",key!!)

        binding.toolbarTitle.setText(id)
        binding.pubAddress.setText(pub_ad)

        viewModel.getData(id!!, token!!, key!!)

        val tab = binding.tabLayout1
        TabLayoutMediator(tab, pager, {tab, position -> tab.text = tabTitles[position]}).attach()


        val PaymentBtn : ImageView = binding.imgPayment
        val TimeCoinBtn : ImageView = binding.imgTimecoin
        val SwapBtn : ImageView = binding.imgSwap

        PaymentBtn.setOnClickListener {
            val intent = Intent(this, PaymentActivity::class.java)
            startActivity(intent)
        }

        TimeCoinBtn.setOnClickListener {
            val intent = Intent(this, TimeCoinActivity::class.java)
            startActivity(intent)
        }

        SwapBtn.setOnClickListener {
            val intent = Intent(this, SwapActivity::class.java)
            startActivity(intent)
        }
        viewModel.loading.observe(this, Observer {
            if(it)
                dialog.show()
            else
                dialog.dismiss()
        })
        viewModel.valid.observe(this, Observer {
            var errorMesage = arrayOf("서버와의 통신 과정에서 오류가 발생했습니다.","","토큰이 만료되었거나 부적절합니다.")
            val builder = AlertDialog.Builder(this)
            if(it == 2) {
                builder.setTitle("지갑 조회 실패")
                    .setMessage(errorMesage[it])
                    .setPositiveButton("돌아가기",
                        DialogInterface.OnClickListener { dialog, id ->
                            val intentLogin = Intent(this,LoginActivity::class.java)
                            startActivity(intentLogin)
                            finish()
                        })
                builder.show()
            }
            else if(it == -1)
                Toast.makeText(this, "서버와의 통신이 원활하지 않습니다", Toast.LENGTH_SHORT).show()
            else if(it == 0)
                Toast.makeText(this, "현제 거래내역이 없습니다", Toast.LENGTH_SHORT).show()
        })


//
//        binding.imgPayment.setOnClickListener {
//            Log.d("태그", "송금 클릭했다")
//            val intent = Intent(this, PaymentActivity::class.java)
//            startActivity(intent)
//        }
//
//        binding.imgTimecoin.setOnClickListener {
//            val intent = Intent(this, TimeCoinActivity::class.java)
//            startActivity(intent)
//        }
//
//        binding.imgSwap.setOnClickListener {
//            val intent = Intent(this, SwapActivity::class.java)
//            startActivity(intent)
//        }

    }

}