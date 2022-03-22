package com.birdview.hwahae.main.home.now

import android.content.Context
import android.graphics.Color
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.birdview.hwahae.R
import com.bumptech.glide.Glide

class NewProductAdapter(private val context: Context) :
    RecyclerView.Adapter<NewProductAdapter.ViewHolder>() {
    var datas = mutableListOf<NewProductData>()

    interface OnItemClickListener {
        fun onItemClick(v: View, data: NewProductData, pos: Int)
    }

    private var listener: OnItemClickListener? = null
    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.home_now_newproduct_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(datas[position])
    }

    override fun getItemCount(): Int = datas.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val product_image: ImageView = itemView.findViewById(R.id.product_image)
        private val product_name: TextView = itemView.findViewById(R.id.product_name)
        private val product_price : TextView = itemView.findViewById(R.id.product_price)
        private val product_sale : TextView = itemView.findViewById(R.id.product_sale)
        private val product_salePrice : TextView = itemView.findViewById(R.id.product_salePrice)

        fun bind(item: NewProductData) {

            product_name.text = item.company+" "+item.name
            product_price.text = item.price
            product_sale.text = item.sale
            product_salePrice.text = item.salePrice


            //회사명 부분 글자색 변경
            val nameData = product_name.text.toString()

            val builder = SpannableStringBuilder(nameData)

            val colorGraySpan = ForegroundColorSpan(Color.GRAY)
            builder.setSpan(colorGraySpan, 0, item.company.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

            product_name.text = builder

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