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

class BeautyOnAdapter(private val context: Context) :
    RecyclerView.Adapter<BeautyOnAdapter.ViewHolder>() {
    var datas = mutableListOf<BeautyOnData>()

    interface OnItemClickListener {
        fun onItemClick(v: View, data: BeautyOnData, pos: Int)
    }

    private var listener: OnItemClickListener? = null
    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.main_home_now_beautyon_item, parent, false)
        view.layoutParams = ViewGroup.LayoutParams((parent.width * 0.5).toInt(),ViewGroup.LayoutParams.WRAP_CONTENT)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(datas[position])
    }

    override fun getItemCount(): Int = datas.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val beautyON_image: ImageView = itemView.findViewById(R.id.beautyON_image)
        private val beautyON_title: TextView = itemView.findViewById(R.id.beautyON_title)
        private val beautyON_content: TextView = itemView.findViewById(R.id.beautyON_content)

        fun bind(item: BeautyOnData) {

            beautyON_title.text = item.title
            beautyON_content.text = item.content

            Glide.with(itemView).load(item.image).centerCrop().into(beautyON_image)

            val pos = bindingAdapterPosition
            if (pos != RecyclerView.NO_POSITION) {
                itemView.setOnClickListener {
                    listener?.onItemClick(itemView, item, pos)
                }
            }

        }
    }
}