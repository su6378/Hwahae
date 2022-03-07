package com.birdview.hwahae.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.birdview.hwahae.R
import com.birdview.hwahae.databinding.LoginFindPageBinding
import com.birdview.hwahae.databinding.LoginPage2Binding

class FindActivity : AppCompatActivity() {

    private var mBinding: LoginFindPageBinding? = null
    private val binding get() = mBinding!!

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = LoginFindPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //뒤로가기
        binding.cancelBtn.setOnClickListener {
            super.onBackPressed()
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        }
    }
}