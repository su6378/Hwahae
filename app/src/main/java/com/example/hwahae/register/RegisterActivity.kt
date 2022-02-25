package com.example.hwahae.register

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.hwahae.R
import com.google.firebase.auth.FirebaseAuth
import com.rengwuxian.materialedittext.MaterialEditText
import me.codego.view.RoundLayout
import java.util.regex.Pattern

class RegisterActivity : AppCompatActivity() {

    // 이메일 정규식
    val emailValidation =
        "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"

    //비밀번호
    private val PASSWORD_PATTERN: Pattern? = Pattern.compile(
        "^" +
                "(?=.*[A-Za-z])" +
                "(?=.*[0-9])" +
                "(?=.*[@#$%^&+=])" +  // at least 1 special character
                "(?=\\S+$)" +  // no white spaces
                ".{8,}" +  // at least 4 characters
                "$"
    )

    //파이어베이스
    private var firebaseAuth: FirebaseAuth? = null

    //이메일
    lateinit var register_email: MaterialEditText
    private var email_check = false

    //비밀번호
    lateinit var register_password: MaterialEditText
    private var password_check = false

    //닉네임
    lateinit var register_nickname: MaterialEditText
    private var nickname_check = false

    //체크박스
    private var all_check = false
    lateinit var all_agree: RoundLayout
    lateinit var all_agree_checkbox: CheckBox
    lateinit var service_use_agree: RoundLayout
    lateinit var service_use_agree_checkbox: CheckBox
    lateinit var personal_info_agree: RoundLayout
    lateinit var personal_info_agree_checkbox: CheckBox
    lateinit var under_14_agree: RoundLayout
    lateinit var under_14_agree_checkbox: CheckBox

    //다음버튼
    private lateinit var nextBtn: TextView

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register_page)

        //뒤로가기
        val backBtn = findViewById<ImageView>(R.id.backBtn)
        backBtn.setOnClickListener(View.OnClickListener {
            finish()
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        })

        //이메일
        firebaseAuth = FirebaseAuth.getInstance()
        register_email = findViewById(R.id.register_email)
        register_email.addTextChangedListener(object : TextWatcher {
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
        register_password.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                checkPassword()
            }

        })

        //닉네임
        register_nickname = findViewById(R.id.register_nickname)
        register_nickname.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                checkNickname()
            }

        })

        //체크박스
        all_agree = findViewById(R.id.all_agree)
        all_agree_checkbox = findViewById(R.id.all_agree_checkbox)

        service_use_agree = findViewById(R.id.service_use_agree)
        service_use_agree_checkbox = findViewById(R.id.service_use_agree_checkbox)

        personal_info_agree = findViewById(R.id.personal_info_agree)
        personal_info_agree_checkbox = findViewById(R.id.personal_info_agree_checkbox)

        under_14_agree = findViewById(R.id.under_14_agree)
        under_14_agree_checkbox = findViewById(R.id.under_14_agree_checkbox)

        //전체 동의
        all_agree.setOnClickListener {
            if (all_agree_checkbox.isChecked) {
                all_agree_checkbox.isChecked = false
                service_use_agree_checkbox.isChecked = false
                personal_info_agree_checkbox.isChecked = false
                under_14_agree_checkbox.isChecked = false
                all_check = false
                nextBtn.setBackgroundColor(Color.parseColor("#CCCCCC"))
            } else {
                all_agree_checkbox.isChecked = true
                service_use_agree_checkbox.isChecked = true
                personal_info_agree_checkbox.isChecked = true
                under_14_agree_checkbox.isChecked = true
                all_check = true
                if (email_check && password_check && all_check && nickname_check) {
                    nextBtn.setBackgroundColor(resources.getColor(R.color.splash_background))
                }
            }

        }

        //이용약관
        service_use_agree.setOnClickListener {
            if (service_use_agree_checkbox.isChecked) {
                service_use_agree_checkbox.isChecked = false
                all_agree_checkbox.isChecked = false
                all_check = false
                nextBtn.setBackgroundColor(Color.parseColor("#CCCCCC"))
            } else {
                service_use_agree_checkbox.isChecked = true
                if (personal_info_agree_checkbox.isChecked && under_14_agree_checkbox.isChecked) {
                    all_agree_checkbox.isChecked = true
                    all_check = true
                    if (email_check && password_check && all_check && nickname_check) {
                        nextBtn.setBackgroundColor(resources.getColor(R.color.splash_background))
                    }
                }
            }
        }

        //개인정보 수집
        personal_info_agree.setOnClickListener {
            if (personal_info_agree_checkbox.isChecked) {
                personal_info_agree_checkbox.isChecked = false
                all_agree_checkbox.isChecked = false
                all_check = false
                nextBtn.setBackgroundColor(Color.parseColor("#CCCCCC"))
            } else {
                personal_info_agree_checkbox.isChecked = true
                if (service_use_agree_checkbox.isChecked && under_14_agree_checkbox.isChecked) {
                    all_agree_checkbox.isChecked = true
                    all_check = true
                    if (email_check && password_check && all_check && nickname_check) {
                        nextBtn.setBackgroundColor(resources.getColor(R.color.splash_background))
                    }
                }
            }
        }

        //만 14세 미만
        under_14_agree.setOnClickListener {
            if (under_14_agree_checkbox.isChecked) {
                under_14_agree_checkbox.isChecked = false
                all_agree_checkbox.isChecked = false
                all_check = false
                nextBtn.setBackgroundColor(Color.parseColor("#CCCCCC"))
            } else {
                under_14_agree_checkbox.isChecked = true
                if (service_use_agree_checkbox.isChecked && personal_info_agree_checkbox.isChecked) {
                    all_agree_checkbox.isChecked = true
                    all_check = true
                    if (email_check && password_check && all_check && nickname_check) {
                        nextBtn.setBackgroundColor(resources.getColor(R.color.splash_background))
                    }
                }
            }
        }


        //다음
        nextBtn = findViewById(R.id.nextbtn)
        nextBtn.setOnClickListener {
            if (buttonClick()) {
                val intent = Intent(this, RegisterActivity2::class.java)
                intent.putExtra("email", register_email.text.toString().trim())
                intent.putExtra("password", register_password.text.toString().trim())
                intent.putExtra("nickname", register_nickname.text.toString().trim())
                startActivity(intent)
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left)
            } else {
            }
        }

    }

    //이메일 유효성 체크 메소드
    fun checkEmail(): Boolean {
        var email = register_email.text.toString().trim() //공백제거
        val e = Pattern.matches(emailValidation, email)

        if (email.isEmpty()) {
            register_email.setError("이메일을 입력하세요.")
            email_check = false
            nextBtn.setBackgroundColor(Color.parseColor("#CCCCCC"))
            return false
        } else {
            if (e) {
                //이메일 형태가 정상일 경우
                email_check = true
                if (email_check && password_check && all_check && nickname_check) {
                    nextBtn.setBackgroundColor(resources.getColor(R.color.splash_background))
                }
                return true
            } else {
                register_email.setError("이메일 형식이 올바르지 않습니다.")
                email_check = false
                nextBtn.setBackgroundColor(Color.parseColor("#CCCCCC"))
                return false
            }
        }

    }

    //비밀번호 유효성 체크 메소드
    private fun checkPassword(): Boolean {
        var password = register_password.text.toString().trim()
        if (password.isEmpty()) {
            register_password.setError("비밀번호는 대문자, 소문자, 숫자, 특수문자 중 2종류 이상을 조합하여 8자리 이상 입력해주세요.")
            password_check = false
            nextBtn.setBackgroundColor(Color.parseColor("#CCCCCC"))
            return false
        } else {
            if (!Pattern.matches(
                    "^(?=.*\\d)(?=.*[~`!@#$%\\^&*()-])(?=.*[a-zA-Z]).{8,20}$", password
                )
            ) {
                //비밀번호 형태가 정상일 경우
                register_password.setError("비밀번호는 대문자, 소문자, 숫자, 특수문자 중 2종류 이상을 조합하여 8자리 이상 입력해주세요.")
                password_check = false
                nextBtn.setBackgroundColor(Color.parseColor("#CCCCCC"))
                return false
            } else {
                password_check = true
                if (email_check && password_check && all_check && nickname_check) {
                    nextBtn.setBackgroundColor(resources.getColor(R.color.splash_background))
                }
                return true
            }
        }
    }

    //닉네임 유효성 메소드
    private fun checkNickname(): Boolean {
        var nickname = register_nickname.text.toString().trim()
        if (nickname.isEmpty()) {
            register_nickname.setError("닉네임을 입력해주세요.")
            nickname_check = false
            nextBtn.setBackgroundColor(Color.parseColor("#CCCCCC"))
            return false
        } else {
            nickname_check = true
            if (email_check && password_check && all_check && nickname_check) {
                nextBtn.setBackgroundColor(resources.getColor(R.color.splash_background))
            }
            return true
        }
    }

    //버튼 클릭 유효성 메소드
    private fun buttonClick(): Boolean {
        return email_check && password_check && all_check && nickname_check
    }

    private fun createEmail() {
        firebaseAuth!!.createUserWithEmailAndPassword(
            register_email.toString().trim(),
            register_password.toString().trim()
        )
            .addOnCompleteListener(this) {
                if (it.isSuccessful) {
                    val user = firebaseAuth?.currentUser
                }
            }
    }

}

