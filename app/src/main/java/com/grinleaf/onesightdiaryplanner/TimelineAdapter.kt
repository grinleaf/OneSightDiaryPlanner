package com.grinleaf.onesightdiaryplanner

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TimelineAdapter(val context:Context, val tlItems:MutableList<TimelineItem>):RecyclerView.Adapter<TimelineAdapter.VH>() {

    inner class VH(itemView: View):RecyclerView.ViewHolder(itemView){
        //if 문으로 daily / checklist / lifecycle 로 나눠서 보내기?
        val tvDay: TextView by lazy { itemView.findViewById(R.id.tv_day_timeline_daily) }
        val tvDetail: TextView by lazy { itemView.findViewById(R.id.tv_detail_timeline_daily) }
        val tvCategoryImage: ImageView by lazy { itemView.findViewById(R.id.iv_category_timeline_daily) }
        val tvAddData: ImageView by lazy { itemView.findViewById(R.id.iv_image_timeline_daily) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val itemView= LayoutInflater.from(context).inflate(R.layout.recycler_timeline,parent,false)
        return VH(itemView)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val itemView = tlItems.get(position)
        holder.tvDay.setText(itemView.day)
        holder.tvDetail.setText(itemView.content)
        holder.tvCategoryImage.setImageResource(itemView.categoryImage)
        holder.tvAddData.setImageResource(itemView.dayImage)
    }

    override fun getItemCount(): Int { return tlItems.size }
}