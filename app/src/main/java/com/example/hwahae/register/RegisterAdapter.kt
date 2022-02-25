package com.example.hwahae.register

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.hwahae.R

class RegisterAdapter(private val context: Context) : RecyclerView.Adapter<RegisterAdapter.ViewHolder>() {

    var datas = mutableListOf<RegisterData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RegisterAdapter.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.register_item,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RegisterAdapter.ViewHolder, position: Int) {
        holder.bind(datas[position])
    }

    override fun getItemCount(): Int = datas.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val register_item_text: TextView = itemView.findViewById(R.id.register_item_text)
        private val register_item_image: ImageView = itemView.findViewById(R.id.register_item_image)


        fun bind(item: RegisterData) {
            register_item_text.text = item.route
            Glide.with(itemView).load(item.img).into(register_item_image)
        }
    }
}