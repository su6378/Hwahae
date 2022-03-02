package com.birdview.hwahae.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.core.os.postDelayed
import com.birdview.hwahae.R
import com.birdview.hwahae.login.LoginActivity

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