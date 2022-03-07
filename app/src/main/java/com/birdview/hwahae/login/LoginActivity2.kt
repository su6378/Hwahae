package com.birdview.hwahae.login

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.birdview.hwahae.R
import com.birdview.hwahae.databinding.LoginPage2Binding
import com.birdview.hwahae.main.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.rengwuxian.materialedittext.MaterialEditText

class LoginActivity2 : AppCompatActivity() {

    private var mBinding: LoginPage2Binding? = null
    private val binding get() = mBinding!!

    lateinit var auth: FirebaseAuth

    //아이디 비밀번호
    private lateinit var email : String
    private lateinit var password : String

    private var isLogin = false


    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = LoginPage2Binding.inflate(layoutInflater)
        auth = FirebaseAuth.getInstance()

        setContentView(binding.root)

        //뒤로가기
        binding.backBtn.setOnClickListener {
            super.onBackPressed()
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        }

        //아이디
        binding.loginEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                changeColor()
                email = binding.loginEmail.text.toString()

            }

        })

        //비밀번호
        binding.loginPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                changeColor()
                password = binding.loginPassword.text.toString()
            }

        })

        //비밀번호 재설정
        binding.findPassword.setOnClickListener{
            var intent = Intent(this,FindActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left)
        }

        //로그인
        binding.loginBtn.setOnClickListener {
            if(isLogin){
                login(email, password)
            }
            else{

            }
        }
    }

    //로그인 기능
    private fun login(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password) // 로그인
            .addOnCompleteListener { result ->
                if (result.isSuccessful) {
                    var intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
                }
            }
    }

    //로그인버튼 색깔 변경 메소드
    private fun changeColor() {
        var email = binding.loginEmail.text.toString().trim()
        var password = binding.loginPassword.text.toString().trim()

        if (email.isNotEmpty() && password.isNotEmpty()) {
            binding.loginBtn.setBackgroundColor(ContextCompat.getColor(this,R.color.splash_background))
            isLogin = true
        } else {
            binding.loginBtn.setBackgroundColor(Color.parseColor("#CCCCCC"))
            isLogin = false
        }
    }
}