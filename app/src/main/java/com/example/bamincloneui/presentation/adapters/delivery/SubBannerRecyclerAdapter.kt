package com.example.bamincloneui.presentation.adapters.delivery

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.bamincloneui.R
import com.example.bamincloneui.data.model.SubBannerItem

class SubBannerRecyclerAdapter : RecyclerView.Adapter<SubBannerRecyclerAdapter.SubBannerViewHolder>() {

    private var subBannerItemList: List<SubBannerItem>? = null

    class SubBannerViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageViewSubBanner: AppCompatImageView = itemView.findViewById(R.id.imageViewSubBanner)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubBannerViewHolder {
        return SubBannerViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_delivery_banner_sub, parent, false)
        )
    }

    override fun onBindViewHolder(holder: SubBannerViewHolder, position: Int) {

        Glide.with(holder.itemView).load(subBannerItemList!![position].image)
            .transform(CenterCrop(), RoundedCorners(16)).into(holder.imageViewSubBanner)
    }

    override fun getItemCount(): Int {
        return subBannerItemList?.size?:0
    }

    fun setSubBannerItems(list: List<SubBannerItem>) {
        this.subBannerItemList = list
        notifyDataSetChanged()
    }
}