package com.grinleaf.onesightdiaryplanner

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class TimelineLifeAdapter(val context:Context, val lifecycleItems:MutableList<LifecycleItem>):RecyclerView.Adapter<TimelineLifeAdapter.VH>() {
    inner class VH(itemView: View):RecyclerView.ViewHolder(itemView){
        val titleLifecycle: TextView by lazy { itemView.findViewById(R.id.tv_title_timeline_lifecycle) }
        val startDayLifecycle: TextView by lazy { itemView.findViewById(R.id.tv_start_day_timeline_lifecycle) }
        val endDayLifecycle: TextView by lazy { itemView.findViewById(R.id.tv_end_day_timeline_lifecycle) }
        val repeatCycle: TextView by lazy { itemView.findViewById(R.id.tv_cycle_timeline_lifecycle) }
        val categoryLifecycle: ImageView by lazy { itemView.findViewById(R.id.iv_category_timeline_lifecycle) }
        val layout: RelativeLayout by lazy { itemView.findViewById(R.id.layout_timeline_lifecycle) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val itemView= LayoutInflater.from(context).inflate(R.layout.recycler_timeline_life,parent,false)
        return VH(itemView)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val lifecycleItem = lifecycleItems.get(position)
        holder.startDayLifecycle.text= lifecycleItem.day
        if (G.dayOfTimeline == holder.startDayLifecycle.text) {
            holder.titleLifecycle.text = lifecycleItem.content
            holder.endDayLifecycle.text = lifecycleItem.endDay
            holder.repeatCycle.text = lifecycleItem.repeatCycle
            Glide.with(context).load(lifecycleItem.categoryImage).into(holder.categoryLifecycle)
        }else{
            holder.layout.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int { return lifecycleItems.size }
}