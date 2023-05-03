package com.superstrong.android.view


import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.superstrong.android.R
import com.bumptech.glide.Glide
class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val imageView = findViewById<ImageView>(R.id.imageView)

        Glide.with(this).load(R.drawable.movelog).into(imageView)
        // 5초 후 LoginActivity로 이동하고 현재 Activity를 종료
        Handler().postDelayed({
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }, 9000)
    }
}