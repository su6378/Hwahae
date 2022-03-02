package com.example.hwahae.register

import android.os.Bundle
import android.util.Log
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatActivity
import com.example.hwahae.R

class RegisterActivity3 : AppCompatActivity() {

    //피부타입
    private var skin = ""

    //피부고민
    private var problem = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = com.example.hwahae.databinding.RegisterPage3Binding.inflate(layoutInflater)
        setContentView(binding.root)

        val email = intent.getStringExtra("email")
        val password = intent.getStringExtra("password")
        val nickname = intent.getStringExtra("nickname")
        val gender = intent.getStringExtra("gender")
        val birth = intent.getStringExtra("birth")

        //닉네임
        binding.register3Nickname.text = nickname + "님에 대한\n힌트를 주세요!"

        //피부타입
        binding.skinGroup.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.skin_dry -> {
                    skin = "지성"
                    binding.skinDry.setTextColor(resources.getColor(R.color.white))
                    binding.skinNeutral.setTextColor(resources.getColor(R.color.dimgray))
                    binding.skinOil.setTextColor(resources.getColor(R.color.dimgray))
                    binding.skinCombination.setTextColor(resources.getColor(R.color.dimgray))
                }
                R.id.skin_neutral -> {
                    skin = "중성"
                    binding.skinDry.setTextColor(resources.getColor(R.color.dimgray))
                    binding.skinNeutral.setTextColor(resources.getColor(R.color.white))
                    binding.skinOil.setTextColor(resources.getColor(R.color.dimgray))
                    binding.skinCombination.setTextColor(resources.getColor(R.color.dimgray))
                }


                R.id.skin_oil -> {
                    skin = "지성"
                    binding.skinDry.setTextColor(resources.getColor(R.color.dimgray))
                    binding.skinNeutral.setTextColor(resources.getColor(R.color.dimgray))
                    binding.skinOil.setTextColor(resources.getColor(R.color.white))
                    binding.skinCombination.setTextColor(resources.getColor(R.color.dimgray))
                }

                R.id.skin_combination -> {
                    skin = "복합성"
                    binding.skinDry.setTextColor(resources.getColor(R.color.dimgray))
                    binding.skinNeutral.setTextColor(resources.getColor(R.color.dimgray))
                    binding.skinOil.setTextColor(resources.getColor(R.color.dimgray))
                    binding.skinCombination.setTextColor(resources.getColor(R.color.white))
                }
            }
        }
    }

    private var listener = CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
        if (isChecked) {
            when(buttonView.id) {
                R.id.skin_noProblem -> { problem = "해당없음" }
                R.id.skin_atopy -> { problem = "아토피" }
                R.id.skin_acne -> { problem = "여드름" }
                R.id.skin_sensibility -> { problem = "민감성") }
            }
        }
        else {
            R.id.skin_noProblem -> {  }
            R.id.skin_atopy -> { checkedToast(cb2) }
            R.id.skin_acne -> { checkedToast(cb3) }
            R.id.skin_sensibility -> { checkedToast(cb4) }
        }
    }
}