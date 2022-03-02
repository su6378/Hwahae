package com.birdview.hwahae.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.core.os.postDelayed
import com.birdview.hwahae.R

class SplashActivity : AppCompatActivity() {

    private val splashDuration = 1500L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_page)
        Handler().postDelayed(splashDuration) {
            //스플래시 화면 처리
            val intent = Intent(this, SplashActivity2::class.java)
            startActivity(intent)
            overridePendingTransition(0,0)
            finish()
        }
    }
}