package com.superstrong.android.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.superstrong.android.databinding.ActivityLoginBinding
import com.superstrong.android.viewmodel.LoginViewModel
import android.text.TextWatcher
import android.text.Editable
class LoginActivity : AppCompatActivity() {
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

//-----------------------------------입력값 바인딩------------------------------------------------------------------------
        binding.usernameEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                loginViewModel.username.value = s.toString()
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        binding.passwordEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                loginViewModel.password.value = s.toString()
            }

            override fun afterTextChanged(s: Editable?) {}
        })

//-----------------------------------로그인 버튼 이벤트------------------------------------------------------------------------
        binding.loginBtn.setOnClickListener {
            val usernameValue: String? = loginViewModel.username.value
            val passwordValue: String? = loginViewModel.password.value
            if (usernameValue != null && passwordValue != null) {
                loginViewModel.PostLogin(usernameValue, passwordValue, this)
            } else {
                Toast.makeText(this, "id와 password를 입력해 주세요", Toast.LENGTH_SHORT).show()
            }

        }

//-----------------------------------회원가입 이벤트------------------------------------------------------------------------
        binding.signupBtn.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }

//-----------------------------------비밀번호 찾기 이벤트------------------------------------------------------------------------
        binding.findPassBtn.setOnClickListener {
            val intent = Intent(this, FindPassActivity::class.java)
            startActivity(intent)
        }

    }
}