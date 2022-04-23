package com.example.bamincloneui.constants

import com.example.bamincloneui.R

object Common{
    const val DELIVERY_FRAGMENT_GRID_MENU_ROW = 5
    const val DELIVERY_FILTER_COUNT = 13

    const val POSITION_WITHOUT_RESET = 7
    const val POSITION_WITH_RESET = 8

}

enum class FILTER(val code:Int, val text:String, val icon:Int, val iconPosition:String, val enabled:Boolean) {
    RESET(0, "초기화", R.drawable.ic_baseline_subdirectory_arrow_left_24, "left", false),
    LOW_TIPS(1, "배달팁 낮은 순", R.drawable.ic_baseline_arrow_downward_24, "left", true),
    BASIC(2, "기본 순", 0, "", true),
    HIGH_ORDER(3, "주문 많은 순", 0, "", true),
    HIGH_STARS(4, "별점 높은 순", 0, "", true),
    NEAR_LOCATION(5, "가까운 순", 0, "", true),
    HIGH_FAVORITE(6, "찜 많은 순",0, "", true),
    MIN_PRICE(7, "최소주문", R.drawable.ic_baseline_keyboard_arrow_down_24, "right", true),
    COUPON(8, "쿠폰", R.drawable.ic_baseline_close_24, "right", false),
    TAKEOUT(9, "포장/방문", R.drawable.ic_baseline_close_24, "right", false),
    SELF_MEAL(10, "1인분", R.drawable.ic_baseline_close_24, "right", false),
    BOOKING(11, "예약가능", R.drawable.ic_baseline_close_24, "right", false),
    ETC(12, "기타", R.drawable.ic_baseline_settings_24, "left", true),
}

enum class MinPrice(val position:Int, val text:String) {
    ALL(0, "전체"),
    UNDER_5000(1, "5,000원 이하"),
    UNDER_10000(2, "10,000원 이하"),
    UNDER_12000(3, "12,000원 이하"),
    UNDER_15000(4, "15,000원 이하"),
    UNDER_20000(5, "20,000원 이하"),
}

enum class Etc(val position:Int, val text:String){
    COUPON(0, "쿠폰"),
    TAKEOUT(1, "포장/방문"),
    SELF_MEAL(2, "1인분"),
    BOOKING(3, "예약가능")
}

