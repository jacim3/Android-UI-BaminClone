package com.example.bamincloneui.presentation.fragment.main.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bamincloneui.R
import com.example.bamincloneui.data.model.BannerItem
import com.example.bamincloneui.data.model.DeliveryGridItem
import com.example.bamincloneui.data.model.DeliveryListItem
import com.example.bamincloneui.data.model.SubBannerItem

class ADeliveryViewModel : ViewModel(){

    /*
        fragment 는 화면 전환 간 View 가 새롭게 생성되므로, 이를 기억해놓지 않으면, Position 이 0 으로
        초기화 된다. 이를 방지하기 위함.
    */

    val bannerItemList = MutableLiveData<List<BannerItem>>()
    val bannerItemPosition = MutableLiveData<Int>().apply {
        this.value = 0
    }

    val subBannerItemList = MutableLiveData<List<SubBannerItem>>()

    val gridMenuItems = MutableLiveData<List<DeliveryGridItem>>()

    val deliveryItemList = MutableLiveData<List<DeliveryListItem>>()

    val resultFilterData = MutableLiveData<Map<String, Any>>()

}