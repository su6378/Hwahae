package com.birdview.hwahae.main.home.now

import android.content.ContentValues.TAG
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ScrollView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.birdview.hwahae.databinding.MainHomeNowPageBinding
import com.birdview.hwahae.main.home.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage


class NowFragment : Fragment() {

    //Firebase
    val db = FirebaseFirestore.getInstance()
    private lateinit var auth: FirebaseAuth

    //뷰바인딩
    private var mBinding: MainHomeNowPageBinding? = null
    private val binding get() = mBinding!!

    //이 제품 어때요?
    private lateinit var recommend_recyclerview: RecyclerView
    private lateinit var recommendAdapter: RecommendAdapter
    private var recommendData = mutableListOf<RecommendData>()

    //NEW인기신제품
    private lateinit var newnhot_recyclerview: RecyclerView
    private lateinit var newnhotAdapter: NewnHotAdapter
    private var newnhotData = mutableListOf<NewnHotData>()

    //광고 뷰페이저
    private lateinit var ad_vp: ViewPager2
    private lateinit var adVP: AdVP
    private var adData = mutableListOf<Uri>()

    //화해쇼핑
    private lateinit var shopping_recyclerview: RecyclerView
    private lateinit var shoppingAdapter: ShoppingAdapter
    private var shoppingData = mutableListOf<ShoppingData>()

    //신상 sale 특가
    private lateinit var newProduct_recyclerview: RecyclerView
    private lateinit var newProductAdapter: NewProductAdapter
    private var newProductData = mutableListOf<NewProductData>()

    //뷰티ON
    private lateinit var beautyOn_recyclerview: RecyclerView
    private lateinit var beautyOnAdapter: BeautyOnAdapter
    private var beautyOnData = mutableListOf<BeautyOnData>()

    override fun onResume() {
        super.onResume()
        // initShopping()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        mBinding = MainHomeNowPageBinding.inflate(inflater, container, false)


        //닉네임
        auth = FirebaseAuth.getInstance()
        initNickname()

        //이 제품 어때요?
        recommend_recyclerview = binding.recommendRecyclerview
        initRecommend()

        //NEW인기신제품
        newnhot_recyclerview = binding.newnhotRecyclerview
        initNewnHot()

        //광고 뷰페이저
        initAd()

        //화해쇼핑
        shopping_recyclerview = binding.shoppingRecyclerview
        initShopping()

        //신상 sale 기획전
        newProduct_recyclerview = binding.newProductRecyclerview
        initNewProduct()

        //뷰티ON
        beautyOn_recyclerview = binding.beautyONRecyclerview
        initBeautyON()

        //상단이동 버튼 visibility
        binding.nowScrollview.viewTreeObserver.addOnScrollChangedListener {
            if (binding.nowScrollview.scrollY == 0) {
                binding.scrollUpBtn.visibility = View.INVISIBLE
            } else if (binding.nowScrollview.scrollY > 0) {
                binding.scrollUpBtn.visibility = View.VISIBLE
            }
        }


        //최상단화면으로 이동
        binding.scrollUpBtn.setOnClickListener {
            binding.nowScrollview.fullScroll(ScrollView.FOCUS_UP)
            binding.scrollUpBtn.visibility = View.INVISIBLE
        }


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
                Log.d(TAG, "유저 정보 불러오기 실패")
            }
    }

    //이 제품 어때요? 리사이클러뷰 초기화
    private fun initRecommend() {
        recommendData = mutableListOf()
        recommendAdapter = RecommendAdapter(requireContext())
        recommend_recyclerview.adapter = recommendAdapter

        val productList = arrayListOf<String>("simple_toner", "teatree_toner", "greentea_toner")
        val companyList = arrayListOf<String>("싸이닉", "메디힐", "시드물")
        val nameList = arrayListOf<String>("더 심플 카밍 토너", "티트리바이옴 블레미쉬\n시카 토너", "녹차 스킨")

        for (i in 0..2) {
            Firebase.storage.reference.child("product/" + productList[i] + ".png").downloadUrl.addOnCompleteListener {
                if (it.isSuccessful) {

                    recommendData.apply {
                        add(
                            RecommendData(
                                image = it.result,
                                company = companyList[i],
                                name = nameList[i]
                            )
                        )

                        recommendAdapter.datas = recommendData
                        recommendAdapter.notifyDataSetChanged()

                    }
                }
            }
        }

        recommendAdapter.setOnItemClickListener(object : RecommendAdapter.OnItemClickListener {
            override fun onItemClick(v: View, data: RecommendData, pos: Int) {
                /*  Intent(this@MainActivity, ProfileDetailActivity::class.java).apply {
                        putExtra("data", data)
                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    }.run { startActivity(this) }*/
            }

        })


    }

    //NEW인기신제품 리사이클러뷰 초기화
    private fun initNewnHot() {
        newnhotData = mutableListOf()
        newnhotAdapter = NewnHotAdapter(requireContext())
        newnhot_recyclerview.adapter = newnhotAdapter

        val productList = arrayListOf<String>(
            "retinol_ample",
            "dokdo_suncream",
            "birch_sunstick",
            "aqua_eyecream",
            "divein_suncream",
            "chamomile_lipbalm"
        )
        val companyList = arrayListOf<String>("이니스프리", "라운드랩", "라운드랩", "에스네이처", "토리든", "비플레인")
        val nameList = arrayListOf<String>(
            "레티놀 시카 흔적 앰플",
            "1025 독도 선크림\n[SPF50+/PA++++]",
            "자작나무 수분 선스틱\n[SPF50+/PA++++]",
            "아쿠아 소이 요거\n아이크림",
            "다이브인 무기자차\n마일드 선크림...",
            "캐모마일 듀얼 보습\n립밤"
        )

        for (i in 0..5) {
            Firebase.storage.reference.child("product/" + productList[i] + ".png").downloadUrl.addOnCompleteListener {
                if (it.isSuccessful) {

                    newnhotData.apply {
                        add(
                            NewnHotData(
                                image = it.result,
                                company = companyList[i],
                                name = nameList[i]
                            )
                        )

                        newnhotAdapter.datas = newnhotData
                        newnhotAdapter.notifyDataSetChanged()


                    }
                }
            }
        }

        newnhotAdapter.setOnItemClickListener(object : NewnHotAdapter.OnItemClickListener {
            override fun onItemClick(v: View, data: NewnHotData, pos: Int) {
                Log.d("테스트", data.name)
            }

        })

    }

    //광고 뷰페이저
    private fun initAd() {

        ad_vp = binding.adVp
        adVP = AdVP(requireContext(), getAdList())
        ad_vp.adapter = adVP
        ad_vp.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        //indicator 부분 연결
        binding.adIndicator.setViewPager2(ad_vp)

    }

    //뷰페이저 이미지
    private fun getAdList(): MutableList<Uri> {
        adData = mutableListOf()
        val adList = arrayListOf<String>("lifill", "celderma")

        for (i in 0..1) {
            Firebase.storage.reference.child("ad/" + adList[i] + ".png").downloadUrl.addOnCompleteListener {
                if (it.isSuccessful) {


                    adData.apply {
                        add(
                            it.result
                        )

                        adVP.item = adData
                        adVP.notifyDataSetChanged()

                    }
                }

            }
        }
        return adData
    }

    //화해쇼핑
    private fun initShopping() {
        shoppingData = mutableListOf()
        shoppingAdapter = ShoppingAdapter(requireContext())
        shopping_recyclerview.adapter = shoppingAdapter

        val shoppingList = arrayListOf<String>(
            "baobab_soap",
            "centella_ample_remover",
            "snature_aqua_cream",
            "ampleN_ceramide_shot",
            "ahc_purerescue_eyecream"
        )

        for (i in 0 until shoppingList.size) {
            Firebase.storage.reference.child("shopping/" + shoppingList[i] + ".png").downloadUrl.addOnCompleteListener {
                if (it.isSuccessful) {

                    val docRef = db.collection("product").document(shoppingList[i])
                    docRef.get()
                        .addOnSuccessListener { document ->
                            if (document != null) {
                                val title = document.getString("title")!!.replace("\\n", "\n")
                                val tag = document.getString("tag")
                                val price = document.getString("price")
                                val sale = document.getString("sale")
                                val salePrice = document.getString("salePrice")

                                shoppingData.apply {
                                    add(
                                        ShoppingData(
                                            image = it.result,
                                            title = title,
                                            tag = tag!!,
                                            price = price!!,
                                            sale = sale!!,
                                            salePrice = salePrice!!
                                        )
                                    )

                                    shoppingAdapter.datas = shoppingData
                                    shoppingAdapter.notifyDataSetChanged()


                                }

                            } else {
                            }
                        }
                        .addOnFailureListener { exception ->
                            Log.d(TAG, "상품 정보 불러오기 실패")
                        }

                }
            }
        }


        shoppingAdapter.setOnItemClickListener(object : ShoppingAdapter.OnItemClickListener {
            override fun onItemClick(v: View, data: ShoppingData, pos: Int) {
                Log.d("테스트", data.title)
            }

        })
    }

    //신상 sale 기획전
    private fun initNewProduct() {
        newProductData = mutableListOf()
        newProductAdapter = NewProductAdapter(requireContext())
        newProduct_recyclerview.adapter = newProductAdapter

        val newProductList = arrayListOf<String>(
            "birch_toneup_suncream",
            "birch_soothing_gel",
            "dokdo_cleansing_milk",
            "dokdo_bubble_form",
            "aqua_gel_mask",
            "inteca_soothing_ample",
            "makprem_cleansing_milk"
        )

        for (i in 0 until newProductList.size) {
            Firebase.storage.reference.child("product/" + newProductList[i] + ".png").downloadUrl.addOnCompleteListener {
                if (it.isSuccessful) {

                    val docRef = db.collection("product").document(newProductList[i])
                    docRef.get()
                        .addOnSuccessListener { document ->
                            if (document != null) {
                                val company = document.getString("company")
                                val name = document.getString("name")!!.replace("\\n", "\n")
                                val price = document.getString("price")
                                val sale = document.getString("sale")
                                val salePrice = document.getString("salePrice")

                                newProductData.apply {
                                    add(
                                        NewProductData(
                                            image = it.result,
                                            company = company!!,
                                            name = name,
                                            price = price!!,
                                            sale = sale!!,
                                            salePrice = salePrice!!
                                        )
                                    )

                                    newProductAdapter.datas = newProductData
                                    newProductAdapter.notifyDataSetChanged()


                                }

                            } else {
                            }
                        }
                        .addOnFailureListener { exception ->
                            Log.d(TAG, "상품 정보 불러오기 실패")
                        }

                }
            }
        }


        newProductAdapter.setOnItemClickListener(object : NewProductAdapter.OnItemClickListener {
            override fun onItemClick(v: View, data: NewProductData, pos: Int) {
                Log.d("테스트", data.name)
            }

        })
    }

    //뷰티ON
    private fun initBeautyON() {
        beautyOnData = mutableListOf()
        beautyOnAdapter = BeautyOnAdapter(requireContext())
        beautyOn_recyclerview.adapter = beautyOnAdapter

        val beautyOnList = arrayListOf<String>(
            "hair_care",
            "hair_problem",
            "trouble",
            "pery_pera",
            "honey_sleep",
            "spring_lip"
        )

        for (i in 0 until beautyOnList.size) {
            Firebase.storage.reference.child("beauty/" + beautyOnList[i] + ".png").downloadUrl.addOnCompleteListener {
                if (it.isSuccessful) {

                    val docRef = db.collection("beauty").document(beautyOnList[i])
                    docRef.get()
                        .addOnSuccessListener { document ->
                            if (document != null) {
                                val title = document.getString("title")
                                val content = document.getString("content")

                                beautyOnData.apply {
                                    add(
                                        BeautyOnData(
                                            image = it.result,
                                            title = title!!,
                                            content = content!!
                                        )
                                    )

                                    beautyOnAdapter.datas = beautyOnData
                                    beautyOnAdapter.notifyDataSetChanged()

                                }

                            } else {
                            }
                        }
                        .addOnFailureListener { exception ->
                            Log.d(TAG, "상품 정보 불러오기 실패")
                        }

                }
            }
        }


        beautyOnAdapter.setOnItemClickListener(object : BeautyOnAdapter.OnItemClickListener {
            override fun onItemClick(v: View, data: BeautyOnData, pos: Int) {
                Log.d("테스트", data.title)
            }

        })
    }


}