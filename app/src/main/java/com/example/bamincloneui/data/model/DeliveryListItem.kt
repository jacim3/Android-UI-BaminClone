package com.example.bamincloneui.data.model

data class DeliveryListItem(
    val imgs:List<String>,
    val title:String,
    val star:Double,
    val tags: List<String>?,
    val time: List<Int>?,
    val desc: Map<String, String>?
)