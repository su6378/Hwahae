package com.birdview.hwahae.main.home

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.forEachIndexed
import androidx.viewpager2.widget.ViewPager2
import com.birdview.hwahae.R
import com.birdview.hwahae.databinding.HomePageBinding
import com.birdview.hwahae.databinding.MyPageBinding
import com.birdview.hwahae.main.MainActivity
import com.birdview.hwahae.main.home.now.NowFragment
import com.birdview.hwahae.main.my.SettingActivity
import com.birdview.hwahae.main.search.SearchActivity
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class HomeFragment : Fragment(){

    //뷰바인딩
    private var mBinding: HomePageBinding? = null
    private val binding get() = mBinding!!

    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout
    val tabTextList = arrayListOf("NOW", "뷰티ON","이벤트","어워드","쇼핑트렌드")


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        mBinding = HomePageBinding.inflate(inflater,container,false)

        viewPager = binding.pager
        tabLayout = binding.tabLayout

        //검색창
        binding.searchBar.setOnClickListener{
            val intent = Intent(requireContext(), SearchActivity::class.java)
            startActivity(intent)
            requireActivity().overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left)
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //탭 레이아웃 선택
        tabLayout.changeTabsFont(0)
        tabLayout.addOnTabSelectedListener(tabLayoutOnPageChangeListener)

        val pagerAdapter = HomeAdapter(requireActivity())
        //fragment 추가
        pagerAdapter.addFragment(NowFragment())
        pagerAdapter.addFragment(BeautyFragment())
        pagerAdapter.addFragment(EventFragment())
        pagerAdapter.addFragment(AwardFragment())
        pagerAdapter.addFragment(ShopTrendFragement())

        // adapter
        viewPager.adapter = pagerAdapter
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
            }
        })

        // tablayout attach
        TabLayoutMediator(tabLayout, viewPager){ tab, position ->
            tab.text = tabTextList[position]
        }.attach()


    }

    private val tabLayoutOnPageChangeListener = object : TabLayout.OnTabSelectedListener {
        override fun onTabReselected(tabItem: TabLayout.Tab?) {}

        override fun onTabUnselected(tabItem: TabLayout.Tab?) {}

        override fun onTabSelected(tabItem: TabLayout.Tab?) {
            tabItem?.position?.let {
                tabLayout.changeTabsFont(it)
            }
        }
    }

    private fun TabLayout.changeTabsFont(selectPosition: Int) {
        val vg = this.getChildAt(0) as ViewGroup
        val tabsCount = vg.childCount
        for (j in 0 until tabsCount) {
            val vgTab = vg.getChildAt(j) as ViewGroup
            vgTab.forEachIndexed { index, _ ->
                val tabViewChild = vgTab.getChildAt(index)
                if (tabViewChild is TextView) {
                    tabViewChild.setTextBold(j == selectPosition)
                }
            }
        }
    }

    private fun TextView.setTextBold(isBold: Boolean) {
        this.setTypeface(null, if(isBold) Typeface.BOLD else Typeface.NORMAL)
    }


}