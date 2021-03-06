package com.example.bamincloneui.presentation.adapters.delivery

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bamincloneui.R
import com.example.bamincloneui.data.model.DeliveryListItem

class DeliveryItemRecyclerViewAdapter :
    RecyclerView.Adapter<DeliveryItemRecyclerViewAdapter.ListItemViewHolder>() {

    private var listItem: List<DeliveryListItem>? = null

    class ListItemViewHolder constructor(
        itemView: View,
        tagView: Array<View>,
        descView: Array<View>
    ) :
        RecyclerView.ViewHolder(itemView) {

        // ---- itemView
        val imageViewMain: AppCompatImageView = itemView.findViewById(R.id.imageViewListItemMain)
        val imageViewSecond: AppCompatImageView =
            itemView.findViewById(R.id.imageViewListItemSecond)
        val imageViewThird: AppCompatImageView = itemView.findViewById(R.id.imageViewListItemThird)

        val textViewTitle: AppCompatTextView = itemView.findViewById(R.id.textViewTitle)
        val textViewStar: AppCompatTextView = itemView.findViewById(R.id.textViewStar)
        val textViewTime: AppCompatTextView = itemView.findViewById(R.id.textViewTime)

        val linearLayoutAreaSubImages: LinearLayoutCompat =
            itemView.findViewById(R.id.linearLayoutSubImages)
        val linearLayoutAreaDesc: LinearLayoutCompat =
            itemView.findViewById(R.id.linearLayoutAddDesc)
        val linearLayoutAreaTags: LinearLayoutCompat =
            itemView.findViewById(R.id.linearLayoutAddTags)

        // ---- tagView
        val tagNew: LinearLayoutCompat = tagView[0].findViewById(R.id.linearLayoutTags)
        val tagCoupon: LinearLayoutCompat = tagView[1].findViewById(R.id.linearLayoutTags)
        val tagReservation: LinearLayoutCompat = tagView[2].findViewById(R.id.linearLayoutTags)
        val tagTakeOut: LinearLayoutCompat = tagView[3].findViewById(R.id.linearLayoutTags)

        // ---- descView
        val descMinPrice: LinearLayoutCompat = descView[0].findViewById(R.id.linearLayoutDescMinPrice)
        val descMinPriceText: AppCompatTextView = descView[0].findViewById(R.id.textViewDescMinPriceText)

        val descDeliveryTip: LinearLayoutCompat = descView[1].findViewById(R.id.linearLayoutDescDeliveryTip)
        val descDeliveryTipText: AppCompatTextView = descView[1].findViewById(R.id.textViewDescDeliveryTipText)

        val descDistance: LinearLayoutCompat = descView[2].findViewById(R.id.linearLayoutDistance)
        val descDistanceText: AppCompatTextView = descView[2].findViewById(R.id.textViewDescDistanceText)

/*
        val descLabel: LinearLayoutCompat = descView[0].findViewById(R.id.linearLayoutDescLabel)
        val descLabelText: AppCompatTextView = descView[0].findViewById(R.id.textViewDescLabel)

        val descDetail: LinearLayoutCompat = descView[1].findViewById(R.id.linearLayoutDescDetail)
        val descDetailText: AppCompatTextView = descView[1].findViewById(R.id.textViewDescDetail)

        val descSeperator: LinearLayoutCompat = descView[2].findViewById(R.id.linearLayoutDescSeperator)
*/
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListItemViewHolder {

        return ListItemViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_delivery_list_item, parent, false),
            arrayOf(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_delivery_list_item_tag1, parent, false),
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_delivery_list_item_tag2, parent, false),
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_delivery_list_item_tag3, parent, false),
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_delivery_list_item_tag4, parent, false),
                ),
            arrayOf(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_delivery_list_item_desc1, parent, false),
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_delivery_list_item_desc2, parent, false),
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_delivery_list_item_desc3, parent, false)
            )
        )
    }

    @RequiresApi(Build.VERSION_CODES.N)
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ListItemViewHolder, position: Int) {
        listItem?.let {

            val item = listItem!![position]
            holder.apply {
                this.textViewTitle.text = item.title

                when (item.imgs.size) {
                    0 -> {

                    }
                    1, 2 -> {
                        Glide.with(holder.itemView).load(item.imgs[0]).into(holder.imageViewMain)
                        holder.linearLayoutAreaSubImages.visibility = View.GONE
                    }
                    else -> {
                        Glide.with(holder.itemView).load(item.imgs[0]).into(holder.imageViewMain)
                        Glide.with(holder.itemView).load(item.imgs[1]).into(holder.imageViewSecond)
                        Glide.with(holder.itemView).load(item.imgs[2]).into(holder.imageViewThird)
                    }
                }

                this.textViewStar.text = item.star.toString()

                when (item.time?.size) {
                    1 -> {
                        holder.textViewTime.text = "${item.time[0]} ???"
                    }
                    2 -> {
                        holder.textViewTime.text = "${item.time[0]} ~ ${item.time[1]} ???"
                    }
                    else -> {}
                }

                if (item.tags != null) {
                    for (i in item.tags) {
                        when(i) {
                            "??????" -> {
                                holder.linearLayoutAreaTags.addView(holder.tagNew)
                            }
                            "????????????" -> {
                                holder.linearLayoutAreaTags.addView(holder.tagTakeOut)
                            }
                            "??????" -> {
                                holder.linearLayoutAreaTags.addView(holder.tagCoupon)
                            }
                            "????????????" -> {
                                holder.linearLayoutAreaTags.addView(holder.tagReservation)
                            }
                        }
                    }
                }

                // TODO ????????? ????????? GPS ??? ???????????? ??????
                if (item.desc != null) {
                    val currentKeys = item.desc.keys.toList()
                    for (i in currentKeys.indices) {
                        when(currentKeys[i]) {
                            "????????????" -> {
                                holder.descMinPriceText.text = item.desc["????????????"]
                                holder.linearLayoutAreaDesc.addView(holder.descMinPrice)
                            }
                            "?????????" -> {
                                holder.descDeliveryTipText.text = item.desc["?????????"]
                                holder.linearLayoutAreaDesc.addView(holder.descDeliveryTip)
                            }
                            else -> {

                            }
                        }
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return listItem?.size ?: 0
    }

    fun setListItem(listItem: List<DeliveryListItem>) {
        this.listItem = listItem
        notifyDataSetChanged()
    }
}
