package com.birdview.hwahae.main.home

import android.net.Uri

data class ShoppingData(
    val image : Uri,
    val title : String,
    val tag : String,
    val price : String,
    val sale : String,
    val salePrice : String
    )
