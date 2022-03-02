package com.birdview.hwahae.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.birdview.hwahae.R
import com.birdview.hwahae.register.RegisterActivity
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

        //회원가입 버튼
        val registerBtn = findViewById<TextView>(R.id.registerBtn)
        registerBtn.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left)
        })

    }

    private fun getLoginList(): ArrayList<Int> {
        return arrayListOf<Int>(R.drawable.login_image1, R.drawable.login_image2)
    }
}