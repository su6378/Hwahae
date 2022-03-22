package com.birdview.hwahae.main

import android.os.Bundle
import android.util.Log
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.birdview.hwahae.R
import com.birdview.hwahae.main.home.HomeFragment
import com.birdview.hwahae.main.my.MyFragment
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {

    //프레임
    private val fl: FrameLayout by lazy {
        findViewById(R.id.frameLayout)
    }

    private lateinit var bottomNavi : BottomNavigationView

    var backKeyPressedTime: Long = 0

    override fun onBackPressed() {
        if(bottomNavi.selectedItemId == R.id.fragHome){
            if (System.currentTimeMillis() - backKeyPressedTime < 2000) {
                finish()
                return
            }
            Toast.makeText(this, "뒤로가기 버튼을 한번 더 누르면 종료합니다.", Toast.LENGTH_SHORT).show()
            backKeyPressedTime = System.currentTimeMillis()
        }else{
            changeFragment(HomeFragment())
            bottomNavi.selectedItemId = R.id.fragHome
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_page)


        //바텀 네비게이션
        bottomNavi = findViewById(R.id.bottomNavi)

        bottomNavi.setOnItemSelectedListener { item ->
            changeFragment(
                when (item.itemId) {
                    R.id.fragHome -> {
                        HomeFragment()
                    }
                    R.id.fragObserve -> {
                        ObserveFragment()
                    }
                    R.id.fragShopping -> {
                        ShopFragment()
                    }
                    else -> {
                        MyFragment()
                    }
                }
            )
            true
        }

        //시작 프래그먼트
        bottomNavi.selectedItemId = R.id.fragHome


    }


    private fun changeFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.frameLayout, fragment)
            .commit()
    }

}