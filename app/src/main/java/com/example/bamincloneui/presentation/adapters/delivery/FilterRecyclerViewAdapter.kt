package com.example.bamincloneui.presentation.adapters.delivery

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.bamincloneui.R
import com.example.bamincloneui.data.model.BannerItem
import com.example.bamincloneui.presentation.interaction.BannerInteraction

class FilterRecyclerViewAdapter(private val interaction:BannerInteraction) : RecyclerView.Adapter<BannerPagerRecyclerAdapter.BannerViewHolder>() {

    private var bannerItemList: List<BannerItem>? = null
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BannerPagerRecyclerAdapter.BannerViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(
        holder: BannerPagerRecyclerAdapter.BannerViewHolder,
        position: Int
    ) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }


}