package com.birdview.hwahae.main.home.now

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.birdview.hwahae.R
import com.bumptech.glide.Glide

class ShoppingAdapter(private val context: Context) :
    RecyclerView.Adapter<ShoppingAdapter.ViewHolder>() {
    var datas = mutableListOf<ShoppingData>()

    interface OnItemClickListener {
        fun onItemClick(v: View, data: ShoppingData, pos: Int)
    }

    private var listener: OnItemClickListener? = null
    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.main_home_now_shopping_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(datas[position])
    }

    override fun getItemCount(): Int = datas.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val product_image: ImageView = itemView.findViewById(R.id.product_image)
        private val product_title: TextView = itemView.findViewById(R.id.product_title)
        private val product_tag: TextView = itemView.findViewById(R.id.product_tag)
        private val product_price : TextView = itemView.findViewById(R.id.product_price)
        private val product_sale : TextView = itemView.findViewById(R.id.product_sale)
        private val product_salePrice : TextView = itemView.findViewById(R.id.product_salePrice)

        fun bind(item: ShoppingData) {

            product_title.text = item.title
            product_tag.text = item.tag
            product_price.text = item.price
            product_sale.text = item.sale
            product_salePrice.text = item.salePrice

            Glide.with(itemView).load(item.image).fitCenter().into(product_image)

            val pos = bindingAdapterPosition
            if (pos != RecyclerView.NO_POSITION) {
                itemView.setOnClickListener {
                    listener?.onItemClick(itemView, item, pos)
                }
            }

        }
    }
}