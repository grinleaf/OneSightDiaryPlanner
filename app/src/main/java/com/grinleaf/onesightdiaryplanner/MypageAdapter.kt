package com.grinleaf.onesightdiaryplanner

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MypageAdapter(val context: Context, val gridItems:MutableList<GridItem>):RecyclerView.Adapter<MypageAdapter.VH>() {
    inner class VH(itemView: View):RecyclerView.ViewHolder(itemView){
        val gridImage:ImageView by lazy { itemView.findViewById(R.id.iv_grid_mypage) }
        val gridContent: TextView by lazy { itemView.findViewById(R.id.tv_grid_mypage) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val itemView= LayoutInflater.from(context).inflate(R.layout.recycler_theme_mypage,parent,false)
        return VH(itemView)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val gridItem= gridItems.get(position)
        holder.gridImage.setImageResource(gridItem.gridImage)
        holder.gridContent.text= gridItem.gridContent
    }

    override fun getItemCount(): Int {return gridItems.size}
}