package com.birdview.hwahae.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.birdview.hwahae.R
import com.birdview.hwahae.main.home.HomeFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {

    //프레임
    private val fl: FrameLayout by lazy {
        findViewById(R.id.frameLayout)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_page)


        //바텀 네비게이션
        val bottomNavi = findViewById<BottomNavigationView>(R.id.bottomNavi)

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