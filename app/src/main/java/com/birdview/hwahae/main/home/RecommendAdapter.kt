package com.birdview.hwahae.main.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.birdview.hwahae.R
import com.bumptech.glide.Glide

class RecommendAdapter(private val context: Context) :
    RecyclerView.Adapter<RecommendAdapter.ViewHolder>() {
    var datas = mutableListOf<RecommendData>()

    interface OnItemClickListener {
        fun onItemClick(v: View, data: RecommendData, pos: Int)
    }

    private var listener: OnItemClickListener? = null
    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.recommend_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecommendAdapter.ViewHolder, position: Int) {
        holder.bind(datas[position])
    }

    override fun getItemCount(): Int = datas.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val product_image: ImageView = itemView.findViewById(R.id.product_image)
        private val product_company: TextView = itemView.findViewById(R.id.product_company)
        private val product_name: TextView = itemView.findViewById(R.id.product_name)

        fun bind(item: RecommendData) {
            product_company.text = item.company
            product_name.text = item.name
            Glide.with(itemView).load(item.image).into(product_image)

            val pos = bindingAdapterPosition
            if (pos != RecyclerView.NO_POSITION) {
                itemView.setOnClickListener {
                    listener?.onItemClick(itemView, item, pos)
                }
            }

        }
    }
}