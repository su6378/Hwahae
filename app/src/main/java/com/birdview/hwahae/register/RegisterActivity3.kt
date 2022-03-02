package com.birdview.hwahae.register

import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.birdview.hwahae.R
import com.birdview.hwahae.databinding.RegisterPage3Binding
import com.birdview.hwahae.main.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class RegisterActivity3 : AppCompatActivity() {

    //피부타입
    private var skin = ""
    private var isSkin = false

    //피부고민
    private var problem = ""
    private var isProblem = false
    private lateinit var skinNoProblem: CheckBox
    private lateinit var skinAtopy: CheckBox
    private lateinit var skinAcne: CheckBox
    private lateinit var skinSensibility: CheckBox

    //파이어베이스
    private lateinit var auth: FirebaseAuth
    private val db = Firebase.firestore

    //다음버튼
    private lateinit var nextBtn: TextView

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = RegisterPage3Binding.inflate(layoutInflater)
        setContentView(binding.root)



        val email = intent.getStringExtra("email")
        val password = intent.getStringExtra("password")
        val nickname = intent.getStringExtra("nickname")
        val gender = intent.getStringExtra("gender")
        val birth = intent.getStringExtra("birth")

        //뒤로가기
        binding.backBtn.setOnClickListener {
            super.onBackPressed()
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        }

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
                    isSkin = true
                    changeColor()

                }
                R.id.skin_neutral -> {
                    skin = "중성"
                    binding.skinDry.setTextColor(resources.getColor(R.color.dimgray))
                    binding.skinNeutral.setTextColor(resources.getColor(R.color.white))
                    binding.skinOil.setTextColor(resources.getColor(R.color.dimgray))
                    binding.skinCombination.setTextColor(resources.getColor(R.color.dimgray))
                    isSkin = true
                    changeColor()
                }


                R.id.skin_oil -> {
                    skin = "지성"
                    binding.skinDry.setTextColor(resources.getColor(R.color.dimgray))
                    binding.skinNeutral.setTextColor(resources.getColor(R.color.dimgray))
                    binding.skinOil.setTextColor(resources.getColor(R.color.white))
                    binding.skinCombination.setTextColor(resources.getColor(R.color.dimgray))
                    isSkin = true
                    changeColor()
                }

                R.id.skin_combination -> {
                    skin = "복합성"
                    binding.skinDry.setTextColor(resources.getColor(R.color.dimgray))
                    binding.skinNeutral.setTextColor(resources.getColor(R.color.dimgray))
                    binding.skinOil.setTextColor(resources.getColor(R.color.dimgray))
                    binding.skinCombination.setTextColor(resources.getColor(R.color.white))
                    isSkin = true
                    changeColor()
                }
            }
        }

        //피부고민
        skinNoProblem = findViewById(R.id.skin_noProblem)
        skinAtopy = findViewById(R.id.skin_atopy)
        skinAcne = findViewById(R.id.skin_acne)
        skinSensibility = findViewById(R.id.skin_sensibility)

        binding.skinNoProblem.setOnCheckedChangeListener(listener)
        binding.skinAtopy.setOnCheckedChangeListener(listener)
        binding.skinAcne.setOnCheckedChangeListener(listener)
        binding.skinSensibility.setOnCheckedChangeListener(listener)

        //화해 시작하기
        nextBtn = findViewById(R.id.nextbtn)
        auth = FirebaseAuth.getInstance()
        binding.nextbtn.setOnClickListener {
            auth.createUserWithEmailAndPassword(email!!, password!!)
                .addOnCompleteListener { result ->
                    if (result.isSuccessful) {
                        Toast.makeText(this, "회원가입이 완료되었습니다.", Toast.LENGTH_SHORT).show()
                        if (auth.currentUser != null) {
                            createAccout()
                            var intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                        }
                    } else if (result.exception?.message.isNullOrEmpty()) {
                        Toast.makeText(this, "오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
                    } else {
                        //login(email, password)
                    }
                }
        }

    }

    private var listener = CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
        if (isChecked) {
            when (buttonView.id) {
                R.id.skin_noProblem -> {
                    problem = "해당없음"
                    skinNoProblem.setTextColor(ContextCompat.getColor(this, R.color.white))
                    skinAtopy.setTextColor(ContextCompat.getColor(this, R.color.dimgray))
                    skinAcne.setTextColor(ContextCompat.getColor(this, R.color.dimgray))
                    skinSensibility.setTextColor(ContextCompat.getColor(this, R.color.dimgray))
                    skinAtopy.isChecked = false
                    skinAcne.isChecked = false
                    skinSensibility.isChecked = false
                    isProblem = true
                    changeColor()

                }
                R.id.skin_atopy -> {
                    problem = "아토피"
                    skinAtopy.setTextColor(ContextCompat.getColor(this, R.color.white))
                    skinNoProblem.setTextColor(ContextCompat.getColor(this, R.color.dimgray))
                    skinNoProblem.isChecked = false
                    isProblem = true
                    changeColor()
                }
                R.id.skin_acne -> {
                    problem = "여드름"
                    skinAcne.setTextColor(ContextCompat.getColor(this, R.color.white))
                    skinNoProblem.setTextColor(ContextCompat.getColor(this, R.color.dimgray))
                    skinNoProblem.isChecked = false
                    isProblem = true
                    changeColor()
                }
                R.id.skin_sensibility -> {
                    problem = "민감성"
                    skinSensibility.setTextColor(ContextCompat.getColor(this, R.color.white))
                    skinNoProblem.setTextColor(ContextCompat.getColor(this, R.color.dimgray))
                    skinNoProblem.isChecked = false
                    isProblem = true
                    changeColor()
                }
            }
        } else {
            when(buttonView.id){
                R.id.skin_noProblem ->{
                    isProblem = false
                    skinNoProblem.setTextColor(ContextCompat.getColor(this, R.color.dimgray))
                    changeColor()
                }
                R.id.skin_atopy -> {
                    skinAtopy.setTextColor(ContextCompat.getColor(this, R.color.dimgray))
                    checkSkin()
                    changeColor()
                }
                R.id.skin_acne -> {
                    skinAcne.setTextColor(ContextCompat.getColor(this, R.color.dimgray))
                    checkSkin()
                    changeColor()
                }
                R.id.skin_sensibility -> {
                    skinSensibility.setTextColor(ContextCompat.getColor(this, R.color.dimgray))
                    checkSkin()
                    changeColor()
                }
            }

        }
    }

    //중복선택가능한 피부고민 isProblem값 판별 메소드
    private fun checkSkin(){
        if(skinAtopy.isChecked || skinAcne.isChecked || skinSensibility.isChecked){
            isProblem = true
        }else{
            isProblem = false
        }
    }

    //계정 생성
    private fun createAccout(){
        val email = intent.getStringExtra("email")
        val password = intent.getStringExtra("password")
        val nickname = intent.getStringExtra("nickname")
        val gender = intent.getStringExtra("gender")
        val birth = intent.getStringExtra("birth")

        val user = hashMapOf(
            "email" to email,
            "password" to password,
            "nickname" to nickname,
            "gender" to gender,
            "birth" to birth,
        )

        db.collection("user").document(email!!)
            .set(user)
            .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully written!") }
            .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }
    }

    //다음버튼 색깔 변경 메소드
    private fun changeColor() {
        if (isSkin && isProblem) {
            nextBtn.setBackgroundColor(
                ContextCompat.getColor(
                    this@RegisterActivity3,
                    R.color.splash_background
                )
            )
        } else {
            nextBtn.setBackgroundColor(Color.parseColor("#CCCCCC"))
        }
    }
}