package com.example.bamincloneui.presentation

import com.example.bamincloneui.R
import com.example.bamincloneui.data.model.BannerItem
import com.example.bamincloneui.data.model.DeliveryGridItem
import com.example.bamincloneui.data.model.DeliveryListItem
import com.example.bamincloneui.data.model.SubBannerItem

const val IMG1 = "https://i.ytimg.com/vi/7p6rcKJNAUg/hqdefault.jpg"
const val IMG2 =
    "https://images2.minutemediacdn.com/image/upload/c_crop,h_1126,w_2000,x_0,y_181/f_auto,q_auto,w_1100/v1554932288/shape/mentalfloss/12531-istock-637790866.jpg"
const val IMG3 =
    "https://i2.wp.com/www.foodrepublic.com/wp-content/uploads/2012/05/testkitchen_argentinesteak.jpg?resize=1280%2C%20560&ssl=1"
const val IMG4 =
    "https://i0.wp.com/post.healthline.com/wp-content/uploads/2019/05/Various_Sandwiches_1296x728-header-1296x728.jpg?w=1155&h=1528"
const val IMG5 =
    "https://images.unsplash.com/photo-1498837167922-ddd27525d352?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=2850&q=80"


val fakeGridItemList = listOf(
    DeliveryGridItem(R.drawable.a, "1인분"),
    DeliveryGridItem(R.drawable.b, "배민오더"),
    DeliveryGridItem(R.drawable.c, "배민라이더스"),
    DeliveryGridItem(R.drawable.d, "한식"),
    DeliveryGridItem(R.drawable.e, "분식"),
    DeliveryGridItem(R.drawable.f, "카페·디저트"),
    DeliveryGridItem(R.drawable.g, "돈까스·회·일식"),
    DeliveryGridItem(R.drawable.h, "치킨"),
    DeliveryGridItem(R.drawable.i, "피자"),
    DeliveryGridItem(R.drawable.a, "이시안·양식"),
    DeliveryGridItem(R.drawable.b, "중국집"),
    DeliveryGridItem(R.drawable.c, "족발·보쌈"),
    DeliveryGridItem(R.drawable.d, "야식"),
    DeliveryGridItem(R.drawable.e, "찜·탕"),
    DeliveryGridItem(R.drawable.f, "도시락"),
    DeliveryGridItem(R.drawable.g, "패스트푸드"),
    DeliveryGridItem(R.drawable.h, "프랜차이즈"),
)

val fakeBannerList = listOf(
    BannerItem(R.drawable.b1),
    BannerItem(R.drawable.b2),
    BannerItem(R.drawable.b3),
    BannerItem(R.drawable.b1),
    BannerItem(R.drawable.b2)
)

val fakeSubBannerList = listOf(
    SubBannerItem(IMG1),
    SubBannerItem(IMG2),
    SubBannerItem(IMG3),
    SubBannerItem(IMG4),
    SubBannerItem(IMG5),
)

val fakeDeliveryItemList = listOf(
    DeliveryListItem(
        listOf(IMG1),
        "페이크피자집",
        5.0,
        listOf("신규"),
        listOf(9, 35),
        mapOf(Pair("최소주문", "7000"), Pair("배달팁", "0원 ~ 3000원"))
    ),
    DeliveryListItem(
        listOf(IMG2, IMG3, IMG5),
        "페이크치킨집",
        5.0,
        listOf("쿠폰", "포장가능"),
        listOf(9, 35),
        mapOf(Pair("최소주문", "7000"), Pair("배달팁", "0원 ~ 3000원"))
    ),
    DeliveryListItem(
        listOf(IMG3, IMG4, IMG5),
        "페이크파스타집",
        5.0,
        listOf("신규"),
        listOf(9, 35),
        mapOf(Pair("최소주문", "7000"))
    ),
    DeliveryListItem(
        listOf(IMG3),
        "페이크라면집",
        5.0,
        listOf("신규"),
        listOf(9, 35),
        mapOf(Pair("최소주문", "7000"), Pair("배달팁", "0원 ~ 3000원"))
    ),
    DeliveryListItem(
        listOf(IMG5),
        "페이크마라탕집",
        5.0,
        listOf("포장가능", "예약가능"),
        listOf(9, 35),
        mapOf(Pair("최소주문", "7000"))
    ),
    DeliveryListItem(
        listOf(IMG1, IMG3, IMG5),
        "페이크햄버거집",
        5.0,
        null,
        listOf(9, 35),
        mapOf(Pair("최소주문", "7000"), Pair("배달팁", "0원 ~ 3000원"))
    )
)
