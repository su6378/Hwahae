package com.birdview.hwahae.login

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.birdview.hwahae.R

class LoginVP (private val context: Context, loginList: ArrayList<Int>) : RecyclerView.Adapter<LoginVP.PagerViewHolder>() {
    var item = loginList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = PagerViewHolder((parent))

    override fun getItemCount(): Int = item.size

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        //holder.image.setImageResource(item[position])
        //글라이드 패키지 적용
        Glide.with(context).load(item[position]).into(holder.image)
    }

    inner class PagerViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder
        (LayoutInflater.from(parent.context).inflate(R.layout.login_image_item, parent, false)){

        val image = itemView.findViewById<ImageView>(R.id.login_image)
    }
}