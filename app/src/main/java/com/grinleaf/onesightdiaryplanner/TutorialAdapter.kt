package com.grinleaf.onesightdiaryplanner

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

class TutorialAdapter(val context: Context,var imgIds:MutableList<TutorialImage>):RecyclerView.Adapter<TutorialAdapter.VH>() {
    inner class VH(itemView:View):RecyclerView.ViewHolder(itemView){
        val ivPager:ImageView by lazy { itemView.findViewById(R.id.pager_iv) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val itemView= LayoutInflater.from(context).inflate(R.layout.activity_tutorial_pager,parent,false)
        return VH(itemView)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        var imgId= imgIds.get(position)
        holder.ivPager.setImageResource(imgId.imgId)
    }

    override fun getItemCount(): Int { return imgIds.size }
}