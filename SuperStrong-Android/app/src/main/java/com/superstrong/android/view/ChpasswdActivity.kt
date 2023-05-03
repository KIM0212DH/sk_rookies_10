package com.superstrong.android.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.superstrong.android.R
import com.superstrong.android.databinding.ActivityChpasswdBinding
import com.superstrong.android.viewmodel.ChpasswdVModel


class ChpasswdActivity : AppCompatActivity() {
    lateinit var binding : ActivityChpasswdBinding
    lateinit var viewModel :ChpasswdVModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChpasswdBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this).get(ChpasswdVModel::class.java)
        binding.viewModel = viewModel

        binding.btnBack.setOnClickListener{
            viewModel.back()
        }


        viewModel.stage.observe(this, Observer {
            var transaction = supportFragmentManager.beginTransaction()
            if (it == 1) {
                transaction.replace(R.id.frameLayout, Chpasswd1Fragment())
                    .commit()
            } else if (it == 2) {
                transaction.replace(R.id.frameLayout, Chpasswd2Fragment())
                    .commit()
            } else if (it == 3)  {
                transaction.replace(R.id.frameLayout, Chpasswd3Fragment())
                    .commit()
            }
            else
                finish()
        })
        viewModel.done.observe(this, Observer {
            if(it) {
                val intentLogin = Intent(this,LoginActivity::class.java)
                startActivity(intentLogin)
                finish()
            }
        })
    }
}
