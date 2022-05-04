package com.grinleaf.onesightdiaryplanner

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AchievementAdapter(val context: Context, val userInformationItems: MutableList<UserInformationItem>):RecyclerView.Adapter<AchievementAdapter.VH>() {
    inner class VH(itemView:View):RecyclerView.ViewHolder(itemView){
        val userLank:TextView by lazy { itemView.findViewById(R.id.tv_lank_achievement_theme) }
        val userProfile:ImageView by lazy { itemView.findViewById(R.id.iv_lank_achievement_theme) }
        val userNickname:TextView by lazy { itemView.findViewById(R.id.tv_usernickname_achievement_theme) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val itemView= LayoutInflater.from(context).inflate(R.layout.recycler_achievement,parent,false)
        return VH(itemView)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val userInformationItem= userInformationItems.get(position)
        holder.userLank.text= userInformationItem.userLank.toString()
        holder.userProfile.setImageResource(R.drawable.tutorial_sample04)
        holder.userNickname.text= userInformationItem.userNickname
    }

    override fun getItemCount(): Int { return userInformationItems.size }
}