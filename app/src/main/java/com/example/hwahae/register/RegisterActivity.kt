package com.example.hwahae.register

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.hwahae.R
import com.google.firebase.auth.FirebaseAuth
import com.rengwuxian.materialedittext.MaterialEditText
import java.util.regex.Pattern

class RegisterActivity : AppCompatActivity() {

    // 검사 정규식
    val emailValidation = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"

    //파이어베이스
    private var firebaseAuth : FirebaseAuth? = null

    //이메일
    lateinit var register_email : MaterialEditText

    //비밀번호
    lateinit var register_password : MaterialEditText

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register_page)

        //뒤로가기
        val backBtn = findViewById<ImageView>(R.id.backBtn)
        backBtn.setOnClickListener(View.OnClickListener {
            finish()
            overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right)
        })

        //이메일
        firebaseAuth = FirebaseAuth.getInstance()
        register_email = findViewById(R.id.register_email)
        register_email.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(p0: Editable?) {
                checkEmail()
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {


            }
        })

        //비밀번호
        register_password = findViewById(R.id.register_password)




    }
    fun checkEmail():Boolean{
        var email = register_email.text.toString().trim() //공백제거
        val p = Pattern.matches(emailValidation, email) // 서로 패턴이 맞닝?

        if(email.isEmpty()){
            register_email.setError("이메일을 입력하세요.")
            return false
        }else{
            if (p) {
                //이메일 형태가 정상일 경우
                return true
            } else {
                register_email.setError("이메일 형식이 올바르지 않습니다.")
                return false
            }
        }

    }

    private fun createEmail(){
        firebaseAuth!!.createUserWithEmailAndPassword(register_email.toString().trim(),register_password.toString().trim())
            .addOnCompleteListener(this){
                if (it.isSuccessful){
                    val user = firebaseAuth?.currentUser
                }
            }
    }

}

