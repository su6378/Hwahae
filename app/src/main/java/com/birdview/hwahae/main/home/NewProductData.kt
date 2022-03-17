package com.birdview.hwahae.main.home

import android.net.Uri

data class NewProductData(
    val image : Uri,
    val company : String,
    val name : String,
    val price : String,
    val sale : String,
    val salePrice : String
)
