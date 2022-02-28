package com.example.hwahae.register

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.afollestad.materialdialogs.DialogBehavior
import com.afollestad.materialdialogs.LayoutMode
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.ModalDialog
import com.afollestad.materialdialogs.bottomsheets.BottomSheet
import com.afollestad.materialdialogs.bottomsheets.setPeekHeight
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.customview.getCustomView
import com.example.hwahae.R
import com.marcoscg.dialogsheet.DialogSheet

class RegisterActivity2 : AppCompatActivity() {
    //여성
    private var isFemale = false

    //남성
    private var isMale = false

    private var gender = ""

    //가입경로
    private var isFriend = false
    private var isYoutube = false
    private var isOfficial = false
    private var isEtc = false
    private var isAppstore = false
    private var isFacebookAD = false
    private var isCafeBlog = false
    private var isElevator = false
    private var isNews = false
    private var isOnlineBanner = false
    private lateinit var route_text : TextView
    private lateinit var route_baseLine : View
    private var isRoute = false

    //생년월일
    private lateinit var age_text : TextView
    lateinit var birthAdapter: BirthAdapter
    private var isBirth = false

    //다음버튼
    private lateinit var nextBtn : TextView

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = com.example.hwahae.databinding.RegisterPage2Binding.inflate(layoutInflater)
        setContentView(binding.root)



        //뒤로가기
        binding.backBtn.setOnClickListener {
            super.onBackPressed()
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        }

        //닉네임
        binding.register2Nickname.text = intent.getStringExtra("nickname") + "님"

        //여성
        binding.femaleBtn.setOnClickListener {
            if (!isFemale) {
                binding.femaleBtn.setBackgroundColor(resources.getColor(R.color.splash_background))
                binding.femaleImg.imageTintList =
                    ColorStateList.valueOf(Color.parseColor("#FFFFFFFF"))
                isFemale = true
                binding.maleBtn.setBackgroundColor(resources.getColor(R.color.white))
                binding.maleImg.imageTintList = ColorStateList.valueOf(Color.parseColor("#696969"))
                isMale = false
            }
        }

        //남성
        binding.maleBtn.setOnClickListener {
            if (!isMale) {
                binding.maleBtn.setBackgroundColor(resources.getColor(R.color.splash_background))
                binding.maleImg.imageTintList =
                    ColorStateList.valueOf(Color.parseColor("#FFFFFFFF"))
                isMale = true
                binding.femaleBtn.setBackgroundColor(resources.getColor(R.color.white))
                binding.femaleImg.imageTintList =
                    ColorStateList.valueOf(Color.parseColor("#696969"))
                isFemale = false
            }
        }

        //가입경로
        route_text = findViewById(R.id.route_text)
        route_baseLine = findViewById(R.id.route_baseLine)
        binding.routeLayout.setOnClickListener {
            initRoute()
        }

        //생년월일
        age_text = findViewById(R.id.age_text)
        binding.ageLayout.setOnClickListener{
            initBirth(BottomSheet(LayoutMode.MATCH_PARENT))
        }

        //다음
        nextBtn = findViewById(R.id.nextbtn)
        binding.nextbtn.setOnClickListener {
            if (isNext()) {
                if(isFemale){
                    gender = "여성"
                }else if(isMale){
                    gender = "남성"
                }
                val intent = Intent(this, RegisterActivity3::class.java)
                intent.putExtra("email", intent.putExtra("email",intent.getStringExtra("email")))
                intent.putExtra("password", intent.putExtra("password",intent.getStringExtra("password")))
                intent.putExtra("nickname", intent.putExtra("nickname",intent.getStringExtra("nickname")))
                intent.putExtra("nickname", intent.putExtra("nickname",intent.getStringExtra("nickname")))
                intent.putExtra("gender", intent.putExtra("gender",gender))
                intent.putExtra("birth", intent.putExtra("birth",age_text.text.toString()))
                startActivity(intent)
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left)
            } else {
            }
        }
    }
    //가입경로 바텀시트 생성 메소드
    private fun initRoute() {

        val dialogSheet = DialogSheet(this@RegisterActivity2)

        dialogSheet.setView(R.layout.register_route)

        val inflatedView = dialogSheet.inflatedView


        val cancel = inflatedView?.findViewById<ImageView>(R.id.route_cancel)
        cancel?.setOnClickListener{
            dialogSheet.dismiss()
        }


        val friends = inflatedView?.findViewById<ConstraintLayout>(R.id.friendsLayout)
        val friends_text = inflatedView?.findViewById<TextView>(R.id.friends_text)
        val friends_check = inflatedView?.findViewById<ImageView>(R.id.friends_check)
        friends?.setOnClickListener {
            friends_check?.visibility = View.VISIBLE
            isFriend = true
            isYoutube = false
            isOfficial = false
            isEtc = false
            isAppstore = false
            isFacebookAD = false
            isCafeBlog = false
            isElevator = false
            isNews = false
            isOnlineBanner = false
            route_text.text = friends_text?.text.toString()
            route_text.setTextColor(ContextCompat.getColor(this, R.color.black))
            route_baseLine.setBackgroundColor(ContextCompat.getColor(this,R.color.splash_background))
            changeColor()
            dialogSheet.dismiss()

        }

        val youtube = inflatedView?.findViewById<ConstraintLayout>(R.id.youtubeLayout)
        val youtube_text = inflatedView?.findViewById<TextView>(R.id.youtube_text)
        val youtube_check = inflatedView?.findViewById<ImageView>(R.id.youtube_check)
        youtube?.setOnClickListener {
            youtube_check?.visibility = View.VISIBLE
            isFriend = false
            isYoutube = true
            isOfficial = false
            isEtc = false
            isAppstore = false
            isFacebookAD = false
            isCafeBlog = false
            isElevator = false
            isNews = false
            isOnlineBanner = false
            route_text.text = youtube_text?.text.toString()
            route_text.setTextColor(ContextCompat.getColor(this, R.color.black))
            route_baseLine.setBackgroundColor(ContextCompat.getColor(this,R.color.splash_background))
            changeColor()
            dialogSheet.dismiss()

        }

        val official = inflatedView?.findViewById<ConstraintLayout>(R.id.officialSNSLayout)
        val official_text = inflatedView?.findViewById<TextView>(R.id.officialSNS_text)
        val official_check = inflatedView?.findViewById<ImageView>(R.id.officialSNS_check)
        official?.setOnClickListener {
            official_check?.visibility = View.VISIBLE
            isFriend = false
            isYoutube = false
            isOfficial = true
            isEtc = false
            isAppstore = false
            isFacebookAD = false
            isCafeBlog = false
            isElevator = false
            isNews = false
            isOnlineBanner = false
            route_text.text = official_text?.text.toString()
            route_text.setTextColor(ContextCompat.getColor(this, R.color.black))
            route_baseLine.setBackgroundColor(ContextCompat.getColor(this,R.color.splash_background))
            changeColor()
            dialogSheet.dismiss()

        }
        val etc = inflatedView?.findViewById<ConstraintLayout>(R.id.etcLayout)
        val etc_text = inflatedView?.findViewById<TextView>(R.id.etc_text)
        val etc_check = inflatedView?.findViewById<ImageView>(R.id.etc_check)
        etc?.setOnClickListener {
            etc_check?.visibility = View.VISIBLE
            isFriend = false
            isYoutube = false
            isOfficial = false
            isEtc = true
            isAppstore = false
            isFacebookAD = false
            isCafeBlog = false
            isElevator = false
            isNews = false
            isOnlineBanner = false
            route_text.text = etc_text?.text.toString()
            route_text.setTextColor(ContextCompat.getColor(this, R.color.black))
            route_baseLine.setBackgroundColor(ContextCompat.getColor(this,R.color.splash_background))
            changeColor()
            dialogSheet.dismiss()

        }
        val appStore = inflatedView?.findViewById<ConstraintLayout>(R.id.appStoreLayout)
        val appStore_text = inflatedView?.findViewById<TextView>(R.id.appStore_text)
        val appStore_check = inflatedView?.findViewById<ImageView>(R.id.appStore_check)
        appStore?.setOnClickListener {
            appStore_check?.visibility = View.VISIBLE
            isFriend = false
            isYoutube = false
            isOfficial = false
            isEtc = false
            isAppstore = true
            isFacebookAD = false
            isCafeBlog = false
            isElevator = false
            isNews = false
            isOnlineBanner = false
            route_text.text = appStore_text?.text.toString()
            route_text.setTextColor(ContextCompat.getColor(this, R.color.black))
            route_baseLine.setBackgroundColor(ContextCompat.getColor(this,R.color.splash_background))
            changeColor()
            dialogSheet.dismiss()

        }
        val facebook = inflatedView?.findViewById<ConstraintLayout>(R.id.facebookADLayout)
        val facebookAD_text = inflatedView?.findViewById<TextView>(R.id.facebookAD_text)
        val facebookAD_check = inflatedView?.findViewById<ImageView>(R.id.facebookAD_check)
        facebook?.setOnClickListener {
            facebookAD_check?.visibility = View.VISIBLE
            isFriend = false
            isYoutube = false
            isOfficial = false
            isEtc = false
            isAppstore = false
            isFacebookAD = true
            isCafeBlog = false
            isElevator = false
            isNews = false
            isOnlineBanner = false
            route_text.text = facebookAD_text?.text.toString()
            route_text.setTextColor(ContextCompat.getColor(this, R.color.black))
            route_baseLine.setBackgroundColor(ContextCompat.getColor(this,R.color.splash_background))
            changeColor()
            dialogSheet.dismiss()

        }
        val cafe = inflatedView?.findViewById<ConstraintLayout>(R.id.cafeBlogLayout)
        val cafeBlog_text = inflatedView?.findViewById<TextView>(R.id.cafeBlog_text)
        val cafeBlog_check = inflatedView?.findViewById<ImageView>(R.id.cafeBlog_check)
        cafe?.setOnClickListener {
            cafeBlog_check?.visibility = View.VISIBLE
            isFriend = false
            isYoutube = false
            isOfficial = false
            isEtc = false
            isAppstore = false
            isFacebookAD = false
            isCafeBlog = true
            isElevator = false
            isNews = false
            isOnlineBanner = false
            route_text.text = cafeBlog_text?.text.toString()
            route_text.setTextColor(ContextCompat.getColor(this, R.color.black))
            route_baseLine.setBackgroundColor(ContextCompat.getColor(this,R.color.splash_background))
            changeColor()
            dialogSheet.dismiss()

        }
        val elevator = inflatedView?.findViewById<ConstraintLayout>(R.id.elevatorLayout)
        val elevator_text = inflatedView?.findViewById<TextView>(R.id.elevator_text)
        val elevator_check = inflatedView?.findViewById<ImageView>(R.id.elevator_check)
        elevator?.setOnClickListener {
            elevator_check?.visibility = View.VISIBLE
            isFriend = false
            isYoutube = false
            isOfficial = false
            isEtc = false
            isAppstore = false
            isFacebookAD = false
            isCafeBlog = false
            isElevator = true
            isNews = false
            isOnlineBanner = false
            route_text.text = elevator_text?.text.toString()
            route_text.setTextColor(ContextCompat.getColor(this, R.color.black))
            route_baseLine.setBackgroundColor(ContextCompat.getColor(this,R.color.splash_background))
            changeColor()
            dialogSheet.dismiss()

        }
        val news = inflatedView?.findViewById<ConstraintLayout>(R.id.newsLayout)
        val news_text = inflatedView?.findViewById<TextView>(R.id.news_text)
        val news_check = inflatedView?.findViewById<ImageView>(R.id.news_check)
        news?.setOnClickListener {
            news_check?.visibility = View.VISIBLE
            isFriend = false
            isYoutube = false
            isOfficial = false
            isEtc = false
            isAppstore = false
            isFacebookAD = false
            isCafeBlog = false
            isElevator = false
            isNews = true
            isOnlineBanner = false
            route_text.text = news_text?.text.toString()
            route_text.setTextColor(ContextCompat.getColor(this, R.color.black))
            route_baseLine.setBackgroundColor(ContextCompat.getColor(this,R.color.splash_background))
            changeColor()
            dialogSheet.dismiss()

        }
        val onlineBanner = inflatedView?.findViewById<ConstraintLayout>(R.id.onlineBannerLayout)
        val onlineBanner_text = inflatedView?.findViewById<TextView>(R.id.onlineBanner_text)
        val onlineBanner_check = inflatedView?.findViewById<ImageView>(R.id.onlineBanner_check)
        onlineBanner?.setOnClickListener {
            onlineBanner_check?.visibility = View.VISIBLE
            isFriend = false
            isYoutube = false
            isOfficial = false
            isEtc = false
            isAppstore = false
            isFacebookAD = false
            isCafeBlog = false
            isElevator = false
            isNews = false
            isOnlineBanner = true
            route_text.text = onlineBanner_text?.text.toString()
            route_text.setTextColor(ContextCompat.getColor(this, R.color.black))
            route_baseLine.setBackgroundColor(ContextCompat.getColor(this,R.color.splash_background))
            changeColor()
            dialogSheet.dismiss()

        }
        //경로 레이아웃 다시 클릭시 선택한 이전 경로 다시 보여주기
        if (isFriend) {
            friends_check?.visibility = View.VISIBLE
        }
        if (isYoutube) {
            youtube_check?.visibility = View.VISIBLE
        }
        if (isOfficial) {
            official_check?.visibility = View.VISIBLE
        }
        if (isEtc) {
            etc_check?.visibility = View.VISIBLE
        }
        if (isAppstore) {
            appStore_check?.visibility = View.VISIBLE
        }
        if (isFacebookAD) {
            facebookAD_check?.visibility = View.VISIBLE
        }
        if (isCafeBlog) {
            cafeBlog_check?.visibility = View.VISIBLE
        }
        if (isElevator) {
            elevator_check?.visibility = View.VISIBLE
        }
        if (isNews) {
            news_check?.visibility = View.VISIBLE
        }
        if (isOnlineBanner) {
            onlineBanner_check?.visibility = View.VISIBLE
        }

        dialogSheet.show()
    }

    //생년월일 바텀시트 생성 메소드
    private fun initBirth(dialogBehavior: DialogBehavior = ModalDialog) {
        val dialog = MaterialDialog(this, dialogBehavior).show {
            customView(R.layout.register_birth, scrollable = true, noVerticalPadding = true)
            setPeekHeight(Int.MAX_VALUE)

        }

        val customView = dialog.getCustomView()
        val age_recyclerview: RecyclerView = customView.findViewById(R.id.age_recyclerview)



        val list = ArrayList<BirthData>()
        for(i in 1952..2008){
            list.add(BirthData(i))
        }


        val adapter = BirthAdapter(list)

        adapter.setItemClickListener(object: BirthAdapter.OnItemClickListener{
            override fun onClick(v: View, position: Int) {
                // 클릭 시 이벤트 작성
                age_text.text = list[position].birth.toString()
                age_text.setTextColor(ContextCompat.getColor(this@RegisterActivity2,R.color.black))
                changeColor()
                dialog.dismiss()

            }
        })

        age_recyclerview.adapter = adapter

    }

    //다음버튼 색깔 변경 메소드
    private fun changeColor(){
        Log.d("테스트",isMale.toString()+isFemale.toString()+isRoute.toString()+isBirth.toString())
        if(isMale || isFemale){
             if(isBirth && isRoute){
                 nextBtn.setBackgroundColor(ContextCompat.getColor(this@RegisterActivity2,R.color.splash_background))
             }else{
                 nextBtn.setBackgroundColor(Color.parseColor("#CCCCCC"))
             }
        }else{
            nextBtn.setBackgroundColor(Color.parseColor("#CCCCCC"))
        }
    }

    //다음 버튼 활성화 메소드
    private fun isNext() : Boolean{
        if(isMale || isFemale){
            return isBirth && isRoute
        }else{
            return false
        }
    }
}