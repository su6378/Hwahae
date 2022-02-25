package com.example.hwahae.register

import android.content.ContentValues.TAG
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.hwahae.R
import com.kennyc.bottomsheet.BottomSheetListener
import com.kennyc.bottomsheet.BottomSheetMenuDialogFragment

class RegisterActivity2 : AppCompatActivity(), BottomSheetListener {
    //여성
    private var isFemale = false
    //남성
    private var isMale = false

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = com.example.hwahae.databinding.RegisterPage2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        //뒤로가기
        binding.backBtn.setOnClickListener{
            Log.d("테스트", intent.getStringExtra("email").toString())
        }

        //닉네임
        binding.register2Nickname.text = intent.getStringExtra("nickname")+"님"

        //여성
        binding.femaleBtn.setOnClickListener{
                if(!isFemale){
                    binding.femaleBtn.setBackgroundColor(resources.getColor(R.color.splash_background))
                    binding.femaleImg.imageTintList = ColorStateList.valueOf(Color.parseColor("#FFFFFFFF"))
                    isFemale = true
                    binding.maleBtn.setBackgroundColor(resources.getColor(R.color.white))
                    binding.maleImg.imageTintList = ColorStateList.valueOf(Color.parseColor("#696969"))
                    isMale = false
                }
        }

        //남성
        binding.maleBtn.setOnClickListener{
                if(!isMale){
                    binding.maleBtn.setBackgroundColor(resources.getColor(R.color.splash_background))
                    binding.maleImg.imageTintList = ColorStateList.valueOf(Color.parseColor("#FFFFFFFF"))
                    isMale = true
                    binding.femaleBtn.setBackgroundColor(resources.getColor(R.color.white))
                    binding.femaleImg.imageTintList = ColorStateList.valueOf(Color.parseColor("#696969"))
                    isFemale = false
                }
        }

        //가입경로
        binding.routeLayout.setOnClickListener{

        }

        //다음
        binding.nextbtn.setOnClickListener{
            Log.d("isFemale",isFemale.toString())
            Log.d("ismale",isMale.toString())

            /*val items = mutableListOf<MenuItem>()

            for (i in 1..20) {
                val menuItem = BottomSheetMenu.MenuItemBuilder(
                    applicationContext,
                    i,
                    "Item $i",
                ) .build()
                items.add(menuItem)
            }

            BottomSheetMenuDialogFragment.Builder(

                context = this,
                listener = this,
                `object` = "some object",
                menuItems = items,
                autoExpand = true,
                closeTitle = "가입경로",

            ).show(supportFragmentManager)*/

        }
    }

    override fun onSheetShown(bottomSheet: BottomSheetMenuDialogFragment, `object`: Any?) {
        Log.v(TAG, "onSheetShown with Object " + `object`!!)
    }

    override fun onSheetItemSelected(
        bottomSheet: BottomSheetMenuDialogFragment,
        item: MenuItem,
        `object`: Any?
    ) {
        Toast.makeText(applicationContext, item.title.toString() + " Clicked", Toast.LENGTH_SHORT)
            .show()
    }

    override fun onSheetDismissed(
        bottomSheet: BottomSheetMenuDialogFragment,
        `object`: Any?,
        dismissEvent: Int
    ) {
        Log.v(TAG, "onSheetDismissed $dismissEvent")
    }

}