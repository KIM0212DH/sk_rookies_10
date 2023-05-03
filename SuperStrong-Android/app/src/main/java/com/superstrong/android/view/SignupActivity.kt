package com.superstrong.android.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.superstrong.android.R
import com.superstrong.android.databinding.ActivitySignupBinding
import com.superstrong.android.viewmodel.SignupVModel

class SignupActivity : AppCompatActivity() {
    lateinit var binding : ActivitySignupBinding
    lateinit var viewModel : SignupVModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        val dialog = LoadingDialog(this)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(SignupVModel::class.java)
        binding.viewModel = viewModel

        viewModel.stage.observe(this, Observer {//회원가입 단계
            var transaction = supportFragmentManager.beginTransaction()
            if (it == 1) {
                transaction.replace(R.id.frameLayout, Signup1Fragment())
                    .commit()
            } else if (it == 2) {
                transaction.replace(R.id.frameLayout, Signup2Fragment())
                    .commit()
            } else if (it == 3)  {
                transaction.replace(R.id.frameLayout, Signup3Fragment())
                    .commit()
            }
            else if (it == 4)  {
                transaction.replace(R.id.frameLayout, Signup4Fragment())
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
        viewModel.loading.observe(this, Observer {
            if(it)
                dialog.show()
            else
                dialog.dismiss()
        })
        binding.btnBack.setOnClickListener{
            viewModel.back()
        }
    }

}