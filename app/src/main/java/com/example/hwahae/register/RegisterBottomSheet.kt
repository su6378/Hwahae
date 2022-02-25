package com.example.hwahae.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hwahae.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class RegisterBottomSheet(var adapter: RegisterAdapter) : BottomSheetDialogFragment() {

    lateinit var registerAdapter: RegisterAdapter
    val datas = mutableListOf<RegisterData>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.register_bottom_sheet, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<RecyclerView>(R.id.route_recyclerview).adapter = adapter
    }

}