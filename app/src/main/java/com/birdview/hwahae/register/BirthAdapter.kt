package com.birdview.hwahae.register

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.birdview.hwahae.R

class BirthAdapter(private val items: ArrayList<BirthData>) : RecyclerView.Adapter<BirthAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BirthAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.register_birth_item, parent, false)
        return BirthAdapter.ViewHolder(view)
    }

    override fun onBindViewHolder(holder: BirthAdapter.ViewHolder, position: Int) {
        val item = items[position]
        holder.birth_text.text = item.birth.toString()

        holder.itemView.setOnClickListener {
            itemClickListener.onClick(it, position)
        }
    }

    //ClickListener
    interface OnItemClickListener {
        fun onClick(v: View, position: Int)
    }

    fun setItemClickListener(itemClickListener: OnItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    private lateinit var itemClickListener : OnItemClickListener

    override fun getItemCount(): Int = items.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val birth_text: TextView = itemView.findViewById(R.id.birth_text)
    }
}