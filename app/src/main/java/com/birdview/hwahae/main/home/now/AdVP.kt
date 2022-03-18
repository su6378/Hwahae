package com.birdview.hwahae.main.home.now

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.birdview.hwahae.R

class AdVP (private val context: Context, adList: MutableList<Uri>) : RecyclerView.Adapter<AdVP.PagerViewHolder>() {
    var item = adList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = PagerViewHolder((parent))

    override fun getItemCount(): Int = item.size

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        //글라이드 패키지 적용
        Glide.with(context).load(item[position]).fitCenter().into(holder.ad_image)
    }

    inner class PagerViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder
        (LayoutInflater.from(parent.context).inflate(R.layout.main_home_now_ad_item, parent, false)){

        val ad_image = itemView.findViewById<ImageView>(R.id.ad_image)
    }
}