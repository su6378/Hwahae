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
import com.github.nikartm.button.FitButton

class NewnHotAdapter(private val context: Context) :
    RecyclerView.Adapter<NewnHotAdapter.ViewHolder>() {
    var datas = mutableListOf<NewnHotData>()

    interface OnItemClickListener {
        fun onItemClick(v: View, data: NewnHotData, pos: Int)
    }

    private var listener: OnItemClickListener? = null
    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.newnhot_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(datas[position])

        if(datas[position].name.equals("레티놀 시카 흔적 앰플") || datas[position].name.equals("1025 독도 선크림\n" +
                    "[SPF50+/PA++++]")){
            holder.itemView.findViewById<FitButton>(R.id.new_icon).visibility = View.VISIBLE
        }
    }

    override fun getItemCount(): Int = datas.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val product_image: ImageView = itemView.findViewById(R.id.product_image)
        private val product_company: TextView = itemView.findViewById(R.id.product_company)
        private val product_name: TextView = itemView.findViewById(R.id.product_name)
        private val new_icon : FitButton = itemView.findViewById(R.id.new_icon)

        fun bind(item: NewnHotData) {
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