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

class TimelineCheckAdapter(val context:Context, val checklistItems:MutableList<ChecklistItem>):RecyclerView.Adapter<TimelineCheckAdapter.VH>() {
    inner class VH(itemView: View):RecyclerView.ViewHolder(itemView){
        val titleCheckList: TextView by lazy { itemView.findViewById(R.id.tv_title_timeline_checklist) }
        val dayCheckList: TextView by lazy { itemView.findViewById(R.id.tv_day_timeline_checklist) }
        val contentCheckList: TextView by lazy { itemView.findViewById(R.id.tv_detail_timeline_checklist) }
        val categoryCheckList: ImageView by lazy { itemView.findViewById(R.id.iv_category_timeline_checklist) }
        val layout: RelativeLayout by lazy { itemView.findViewById(R.id.layout_timeline_checklist) }

//        val subContentCheckList: TextView by lazy { itemView.findViewById(R.id.tv_detail_timeline_checklist) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val itemView= LayoutInflater.from(context).inflate(R.layout.recycler_timeline_check,parent,false)
        return VH(itemView)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val checklistItem = checklistItems.get(position)
        holder.dayCheckList.text= checklistItem.day
        if (G.dayOfTimeline == holder.dayCheckList.text) { //상단 선택날짜와 day 값이 동일한 경우 출력
            holder.titleCheckList.text = checklistItem.content
            holder.contentCheckList.text = checklistItem.detailContent
            holder.dayCheckList.text = checklistItem.day
            Glide.with(context).load(checklistItem.categoryImage).into(holder.categoryCheckList)
        }else{
            holder.layout.visibility= View.GONE
        }
//        holder.subContentCheckList.text= timelineItem.subContentCheckList
    }

    override fun getItemCount(): Int { return checklistItems.size }
}