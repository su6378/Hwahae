package com.example.hwahae.login

import android.gesture.GestureOverlayView.ORIENTATION_HORIZONTAL
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.hwahae.R
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_page)

        val login_vp : ViewPager2 = findViewById(R.id.login_vp)

        login_vp.adapter = LoginVP(this,getLoginList()) // 어댑터 생성
        login_vp.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        //indicator 부분 연결
        val login_indicator = findViewById<WormDotsIndicator>(R.id.login_indicator)
        login_indicator.setViewPager2(login_vp)

    }

    private fun getLoginList(): ArrayList<Int> {
        return arrayListOf<Int>(R.drawable.login_image1, R.drawable.login_image2)
    }
}