package com.example.bamincloneui.presentation.adapters.delivery

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bamincloneui.R
import com.example.bamincloneui.data.model.DeliveryGridItem

class GridMenuRecyclerAdapter : RecyclerView.Adapter<GridMenuRecyclerAdapter.GridItemVIewHolder>() {

    private var gridItemMenus: List<DeliveryGridItem>? = null

    class GridItemVIewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val gridMenuTitle:AppCompatTextView = itemView.findViewById(R.id.textViewGridTitle)
        val gridMenuIcon:AppCompatImageView = itemView.findViewById(R.id.imageViewGridIcon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GridItemVIewHolder {
        return GridItemVIewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_delivery_grid_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: GridItemVIewHolder, position: Int) {
        gridItemMenus?.get(position)?.image?.let { holder.gridMenuIcon.setImageResource(it) }
        holder.gridMenuTitle.text = gridItemMenus?.get(position)?.title
    }

    override fun getItemCount(): Int {
        return gridItemMenus?.size ?: 0
    }

    fun setGridItems(list: List<DeliveryGridItem>) {
        this.gridItemMenus = list
    }
}