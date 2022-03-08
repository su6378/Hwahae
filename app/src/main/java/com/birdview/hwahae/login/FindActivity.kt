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
import com.afollestad.materialdialogs.MaterialDialog
import com.birdview.hwahae.R
import com.birdview.hwahae.databinding.LoginFindPageBinding
import com.birdview.hwahae.databinding.LoginPage2Binding
import com.birdview.hwahae.register.RegisterActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import java.util.regex.Pattern

class FindActivity : AppCompatActivity() {

    private var mBinding: LoginFindPageBinding? = null
    private val binding get() = mBinding!!
    private val db = FirebaseFirestore.getInstance()

    private lateinit var auth: FirebaseAuth

    //이메일
    private lateinit var email : String
    private var isFind = false

    // 이메일 정규식
    val emailValidation =
        "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = FirebaseAuth.getInstance()
        mBinding = LoginFindPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //뒤로가기
        binding.cancelBtn.setOnClickListener {
            super.onBackPressed()
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        }

        //이메일
        binding.findEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                email = binding.findEmail.text.toString()
                changeColor()
            }

        })

        //비밀번호 재설정 이메일 보내기
        binding.findBtn.setOnClickListener{
            if(isFind){
                auth.sendPasswordResetEmail(email)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this, "해당 이메일 주소로 메일을 보냈습니다.", Toast.LENGTH_SHORT).show()
                            super.onBackPressed()
                            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
                        }else{
                            MaterialDialog(this).show {
                                titleFont
                                message(text = "가입되어 있지 않습니다. 가입을\n진행하시겠습니까?")
                                positiveButton(text = "예") { dialog ->
                                    val intent = Intent(this@FindActivity,RegisterActivity::class.java)
                                    startActivity(intent)
                                    overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left)
                                }
                                negativeButton(text = "아니요") { dialog ->
                                    dialog.dismiss()
                                }
                            }
                        }
                    }
            }else{

            }
        }
    }

    //이메일 유효성 체크 메소드
    private fun changeColor(){
        val e = Pattern.matches(emailValidation, email)

        if (email.isEmpty()) {
            isFind = false
            binding.findBtn.setBackgroundColor(Color.parseColor("#CCCCCC"))
        } else {
            if (e) {
                //이메일 형태가 정상일 경우
                isFind = true
                binding.findBtn.setBackgroundColor(ContextCompat.getColor(this,R.color.splash_background))

            } else {
                isFind = false
                binding.findBtn.setBackgroundColor(Color.parseColor("#CCCCCC"))
            }
        }

    }

}