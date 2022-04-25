package com.grinleaf.onesightdiaryplanner

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RewardAdapter(val context: Context, val rewardGridItems: MutableList<RewardGridItem>):RecyclerView.Adapter<RewardAdapter.VH>() {
    inner class VH(itemView: View):RecyclerView.ViewHolder(itemView){
        val rewardImage:ImageView by lazy { itemView.findViewById(R.id.iv_reward_theme) }
        val rewardContent:TextView by lazy { itemView.findViewById(R.id.tv_reward_theme) }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val itemView= LayoutInflater.from(context).inflate(R.layout.recycler_reward,parent,false)
        return VH(itemView)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val rewardGridItem= rewardGridItems.get(position)
        holder.rewardImage.setImageResource(rewardGridItem.rewardImage)
        holder.rewardContent.text= rewardGridItem.rewardContent
    }

    override fun getItemCount(): Int { return rewardGridItems.size }
}