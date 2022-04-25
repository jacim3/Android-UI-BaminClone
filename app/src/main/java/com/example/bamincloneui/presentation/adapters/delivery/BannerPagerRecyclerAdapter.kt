package com.example.bamincloneui.presentation.adapters.delivery

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.bamincloneui.R
import com.example.bamincloneui.data.model.BannerItem

class BannerPagerRecyclerAdapter() : RecyclerView.Adapter<BannerPagerRecyclerAdapter.BannerViewHolder>() {

    private var bannerItemList: List<BannerItem>? = null

    class BannerViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageViewBanner:AppCompatImageView = itemView.findViewById(R.id.imageViewBanner)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannerViewHolder {
        return BannerViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_delivery_banner, parent, false)
        )
    }

    override fun onBindViewHolder(holder: BannerViewHolder, position: Int) {
        bannerItemList?.let {
            val bannerItem = bannerItemList!![position]

            holder.imageViewBanner.setImageResource(bannerItem.image)
            holder.imageViewBanner.setOnClickListener {

            }
        }
    }

    override fun getItemCount(): Int {
        return bannerItemList?.size ?: 0
    }

    fun setImageList(list: List<BannerItem>){
        this.bannerItemList = list
        notifyDataSetChanged()
    }
}