package com.birdview.hwahae.main.search

import android.graphics.Color
import android.graphics.Typeface
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import androidx.annotation.RequiresApi
import com.birdview.hwahae.R
import com.birdview.hwahae.databinding.MySettingPageBinding
import com.birdview.hwahae.databinding.SearchPageBinding
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class SearchActivity : AppCompatActivity() {

    //뷰바인딩
    private var mBinding: SearchPageBinding? = null
    private val binding get() = mBinding!!

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = SearchPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //뒤로가기
        binding.backBtn.setOnClickListener {
            super.onBackPressed()
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        }

        //실시간 판매 베스트 text bold
        val data = binding.realtimeText.text.toString()

        val builder = SpannableStringBuilder(data)

        val typeSpan = StyleSpan(Typeface.BOLD)
        builder.setSpan(typeSpan, 7, data.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        binding.realtimeText.text = builder

        //n 시 기준
        val current =  Date().time
        val formatter = SimpleDateFormat("yyyy.MM.dd HH시 기준", Locale("ko","KR"))

        formatter.timeZone = TimeZone.getTimeZone("Asia/Seoul")

        val formatted = formatter.format(current)

        binding.baseTime.text = formatted


        //홈 버튼
        binding.homeBtn.setOnClickListener{
            super.onBackPressed()
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        }
    }
}