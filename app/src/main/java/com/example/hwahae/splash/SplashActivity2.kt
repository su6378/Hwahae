package com.example.hwahae.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.core.os.postDelayed
import com.example.hwahae.R
import com.example.hwahae.login.LoginActivity
import com.example.hwahae.main.MainActivity

class SplashActivity2 : AppCompatActivity() {

    private val splashDuration = 1500L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_page2)
        Handler().postDelayed(splashDuration) {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}