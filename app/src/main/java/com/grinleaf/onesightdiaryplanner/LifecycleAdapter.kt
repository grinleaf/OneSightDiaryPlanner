package com.grinleaf.onesightdiaryplanner

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class LifecycleAdapter(val context: Context, val lifecycleItems:MutableList<LifecycleItem>): RecyclerView.Adapter<LifecycleAdapter.VH>() {
    inner class VH(itemView: View):RecyclerView.ViewHolder(itemView){
        val content: TextView by lazy { itemView.findViewById(R.id.tv_title_pager_lifecycle) }
        val startDay: TextView by lazy { itemView.findViewById(R.id.tv_start_day_pager_lifecycle) }
        val endDay: TextView by lazy { itemView.findViewById(R.id.tv_end_day_pager_lifecycle) }
        val repeatCycle: TextView by lazy { itemView.findViewById(R.id.tv_repeat_pager_lifecycle) }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val itemView= LayoutInflater.from(context).inflate(R.layout.recycler_lifecycle,parent,false)
        return VH(itemView)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val lifecycleItem= lifecycleItems.get(position)
        holder.content.text= lifecycleItem.content
        holder.startDay.text= lifecycleItem.day
        holder.endDay.text= lifecycleItem.endDay
        holder.repeatCycle.text= lifecycleItem.repeatCycle
    }

    override fun getItemCount(): Int { return lifecycleItems.size }
}