package com.birdview.hwahae.main.my

import android.content.DialogInterface
import android.content.Intent
import android.net.wifi.hotspot2.pps.Credential
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import com.afollestad.materialdialogs.internal.main.DialogLayout
import com.birdview.hwahae.R
import com.birdview.hwahae.databinding.LoginFindPageBinding
import com.birdview.hwahae.databinding.MySettingPageBinding
import com.birdview.hwahae.login.LoginActivity
import com.forms.sti.progresslitieigb.ProgressLoadingIGB
import com.forms.sti.progresslitieigb.finishLoadingIGB
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.marcoscg.dialogsheet.DialogSheet

class SettingActivity : AppCompatActivity() {

    private var mBinding: MySettingPageBinding? = null
    private val binding get() = mBinding!!

    //파이어베이스
    private var auth: FirebaseAuth? = null
    private val db = FirebaseFirestore.getInstance()

    //탈퇴하기
    private lateinit var password: String

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = MySettingPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //파이어베이스
        auth = FirebaseAuth.getInstance()

        //뒤로가기
        binding.backBtn.setOnClickListener {
            super.onBackPressed()
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        }

        //탈퇴하기
        binding.deleteLayout.setOnClickListener {
            val builder = AlertDialog.Builder(this, R.style.AlertDialogTheme)
            builder.setMessage("계정을 삭제하시겠습니까?")
                .setPositiveButton("예", DialogInterface.OnClickListener { dialog, which ->
                     ProgressLoadingIGB.startLoadingIGB(this){
                         message = "삭제중입니다. 잠시만 기다려주세요... "
                         srcLottieJson = R.raw.skin_care
                         hight = 500 // Optional
                         width = 500 // Optional
                     }
                    delete()
                })
                .setNegativeButton("아니요", DialogInterface.OnClickListener { dialog, which ->

                })
            builder.show()
        }

        //로그아웃
        binding.logoutLayout.setOnClickListener {
            val builder = AlertDialog.Builder(this, R.style.AlertDialogTheme)
            builder.setMessage("로그아웃 하시겠습니까?")
                .setPositiveButton("예", DialogInterface.OnClickListener { dialog, which ->
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    auth?.signOut()
                })
                .setNegativeButton("아니요", DialogInterface.OnClickListener { dialog, which ->

                })
            builder.show()
        }

    }

    //계정삭제
    private fun delete() {
        val user = auth!!.currentUser!!
        val email = user.email!!

        val docRef = db.collection("user").document(email!!)
        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    password = document.getString("password").toString()

                    //사용자 재인증
                    val credential = EmailAuthProvider.getCredential(email, password)
                    user.reauthenticate(credential)
                        .addOnCompleteListener {
                            //사용자 삭제
                            user.delete().addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    val intent = Intent(this, LoginActivity::class.java)
                                    startActivity(intent)
                                    auth!!.signOut()
                                    finishLoadingIGB()

                                    //FireStore 데이터 삭제
                                    val docRef = db.collection("user").document(email!!)
                                    docRef.delete()
                                        .addOnSuccessListener {
                                            Log.d(
                                                "성공",
                                                "DocumentSnapshot successfully deleted!"
                                            )
                                        }
                                        .addOnFailureListener { e ->
                                            Log.w(
                                                "실패",
                                                "Error deleting document",
                                                e
                                            )
                                        }
                                } else {
                                    Log.d("삭제실패", "")
                                }
                            }
                        }

                } else {
                }
            }
            .addOnFailureListener { exception ->
            }
    }
}