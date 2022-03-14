package com.birdview.hwahae.main.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.birdview.hwahae.R
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage


class NowFragment : Fragment() {

    //이 제품 어때요?
    private lateinit var recommend_recyclerview : RecyclerView
    private lateinit var recommendAdapter: RecommendAdapter
    private val recommendData = mutableListOf<RecommendData>()

    //NEW인기신제품
    private lateinit var newnhot_recyclerview : RecyclerView
    private lateinit var newnhotAdapter: NewnHotAdapter
    private val newnhotData = mutableListOf<NewnHotData>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.main_home_now_page, container, false)

        //이 제품 어때요?
        recommend_recyclerview = view.findViewById(R.id.recommend_recyclerview)
        initRecommend()

        //NEW인기신제품
        newnhot_recyclerview = view.findViewById(R.id.newnhot_recyclerview)
        initNewnHot()
        return view
    }

    //이 제품 어때요? 리사이클러뷰 초기화
    private fun initRecommend() {
        recommendAdapter = RecommendAdapter(requireContext())
        recommend_recyclerview.adapter = recommendAdapter

        val productList = arrayListOf<String>("simple_toner","teatree_toner","greentea_toner")
        val companyList = arrayListOf<String>("싸이닉","메디힐","시드물")
        val nameList = arrayListOf<String>("더 심플 카밍 토너","티트리바이옴 블레미쉬\n시카 토너","녹차 스킨")

        for(i in 0..2){
            Firebase.storage.reference.child("product/"+productList[i]+".png").downloadUrl.addOnCompleteListener {
                if (it.isSuccessful) {

                    recommendData.apply {
                        add(RecommendData(image = it.result, company = companyList[i], name = nameList[i]))

                        recommendAdapter.datas = recommendData
                        recommendAdapter.notifyDataSetChanged()

                    }
                }
            }
        }

        recommendAdapter.setOnItemClickListener(object : RecommendAdapter.OnItemClickListener{
            override fun onItemClick(v: View, data: RecommendData, pos : Int) {
                Log.d("테스트",data.name)
            /*  Intent(this@MainActivity, ProfileDetailActivity::class.java).apply {
                    putExtra("data", data)
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }.run { startActivity(this) }*/
            }

        })

    }

    //NEW인기신제품 리사이클러뷰 초기화
    private fun initNewnHot() {
        newnhotAdapter = NewnHotAdapter(requireContext())
        newnhot_recyclerview.adapter = newnhotAdapter

        val productList = arrayListOf<String>("retinol_ample","dokdo_suncream","birch_sunstick","aqua_eyecream","divein_suncream","chamomile_lipbalm")
        val companyList = arrayListOf<String>("이니스프리","라운드랩","라운드랩","에스네이처","토리든","비플레인")
        val nameList = arrayListOf<String>("레티놀 시카 흔적 앰플","1025 독도 선크림\n[SPF50+/PA++++]","자작나무 수분 선스틱\n[SPF50+/PA++++]","아쿠아 소이 요거\n아이크림","다이브인 무기자차\n마일드 선크림...","캐모마일 듀얼 보습\n립밤")

        for(i in 0..5){
            Firebase.storage.reference.child("product/"+productList[i]+".png").downloadUrl.addOnCompleteListener {
                if (it.isSuccessful) {

                    newnhotData.apply {
                        add(NewnHotData(image = it.result, company = companyList[i], name = nameList[i]))

                        newnhotAdapter.datas = newnhotData
                        newnhotAdapter.notifyDataSetChanged()


                    }
                }
            }
        }



        newnhotAdapter.setOnItemClickListener(object : NewnHotAdapter.OnItemClickListener{
            override fun onItemClick(v: View, data: NewnHotData, pos : Int) {
                Log.d("테스트",data.name)
            }

        })

    }
}