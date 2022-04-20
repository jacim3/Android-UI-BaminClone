package com.example.bamincloneui.presentation.interaction

import android.view.View
import com.example.bamincloneui.data.model.BannerItem

interface BannerInteraction: View.OnClickListener {
    fun onBannerItemClicked(bannerItem: BannerItem)
}
