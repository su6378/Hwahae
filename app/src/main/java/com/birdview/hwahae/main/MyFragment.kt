package com.birdview.hwahae.main

import android.content.ContentValues
import android.graphics.Color
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.birdview.hwahae.R
import com.birdview.hwahae.databinding.MainMyPageBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class MyFragment : Fragment() {

    //뷰바인딩
    private var mBinding: MainMyPageBinding? = null
    private val binding get() = mBinding!!

    //Firebase
    val db = FirebaseFirestore.getInstance()
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        mBinding = MainMyPageBinding.inflate(inflater,container,false)

        //닉네임
        auth = FirebaseAuth.getInstance()
        initNickname()

        //회원 정보
        initInfo()


        //나를 소식 받기한 사용자 부분 글자색 변경
        val peopleData = binding.userPeople.text.toString()

        val builder = SpannableStringBuilder(peopleData)

        val colorSpan = ForegroundColorSpan(ContextCompat.getColor(requireContext(),R.color.splash_background))
        builder.setSpan(colorSpan, 13,14, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        binding.userPeople.text = builder

        return binding.root
    }

    //닉네임
    private fun initNickname() {
        val user = auth.currentUser
        val email = user?.email
        val docRef = db.collection("user").document(email!!)
        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    val nickname = document.getString("nickname")
                    binding.nickname.text = nickname
                } else {
                }
            }
            .addOnFailureListener { exception ->
                Log.d(ContentValues.TAG, "유저 정보 불러오기 실패")
            }
    }

    //회원정보
    private fun initInfo(){
        val user = auth.currentUser
        val email = user?.email
        val docRef = db.collection("user").document(email!!)
        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    val birth = document.getString("birth")
                    val birthInt = birth?.toInt()
                    val age = (2023- birthInt!!).toString()

                    val skin = document.getString("skin")
                    val problem = document.get("problem").toString().trim().replace(",","/").replace("[","").replace("]","").replace(" ","")
                    binding.userInfo.text = age+"/"+skin+"/"+problem
                } else {
                }
            }
            .addOnFailureListener { exception ->
                Log.d(ContentValues.TAG, "유저 정보 불러오기 실패")
            }
    }

}