package com.superstrong.android.view

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.superstrong.android.R

class FindPassActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_findpass)

        val NextButton : Button = findViewById(R.id.confirm_button)

        NextButton.setOnClickListener {
            val intent = Intent(this, FindPassActivity2::class.java)
            startActivity(intent)
        }
    }

}