package com.grinleaf.onesightdiaryplanner

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TimelineAdapter(val context:Context, val timelineItems:MutableList<TimelineItem>):RecyclerView.Adapter<TimelineAdapter.VH>() {
    inner class VH(itemView: View):RecyclerView.ViewHolder(itemView){
        val titleDailyNote: TextView by lazy { itemView.findViewById(R.id.tv_title_timeline_daily) }
        val dayDailyNote: TextView by lazy { itemView.findViewById(R.id.tv_day_timeline_daily) }
        val categoryDailyNote: ImageView by lazy { itemView.findViewById(R.id.iv_category_timeline_daily) }
        val attachImageDailyNote: ImageView by lazy { itemView.findViewById(R.id.iv_image_timeline_daily) }
        val contentDailyNote: TextView by lazy { itemView.findViewById(R.id.tv_detail_timeline_daily) }

        val titleCheckList: TextView by lazy { itemView.findViewById(R.id.tv_title_timeline_checklist) }
        val contentCheckList: TextView by lazy { itemView.findViewById(R.id.tv_detail_timeline_checklist) }
        val categoryCheckList: ImageView by lazy { itemView.findViewById(R.id.iv_category_timeline_checklist) }

//        val subContentCheckList: TextView by lazy { itemView.findViewById(R.id.tv_detail_timeline_checklist) }

        val titleLifecycle: TextView by lazy { itemView.findViewById(R.id.tv_title_timeline_lifecycle) }
        val startDayLifecycle: TextView by lazy { itemView.findViewById(R.id.tv_start_day_lifecycle_date_edit) }
        val endDayLifecycle: TextView by lazy { itemView.findViewById(R.id.tv_end_day_lifecycle_date_edit) }
        val repeatCycle: TextView by lazy { itemView.findViewById(R.id.tv_cycle_timeline_lifecycle) }
        val categoryLifecycle: ImageView by lazy { itemView.findViewById(R.id.iv_category_timeline_lifecycle) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val itemView= LayoutInflater.from(context).inflate(R.layout.recycler_timeline,parent,false)
        return VH(itemView)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val timelineItem = timelineItems.get(position)
        holder.titleDailyNote.text= timelineItem.titleDailyNote
        holder.dayDailyNote.text= timelineItem.dayDailyNote
        holder.categoryDailyNote.setImageResource(timelineItem.categoryDailyNote)
        holder.attachImageDailyNote.setImageResource(timelineItem.attachImageDailyNote) //일단 int..
        holder.contentDailyNote.text= timelineItem.contentDailyNote

        holder.titleCheckList.text= timelineItem.titleCheckList
        holder.contentCheckList.text= timelineItem.contentCheckList
        holder.categoryCheckList.setImageResource(timelineItem.categoryCheckList)

//        holder.subContentCheckList.text= timelineItem.subContentCheckList

        holder.titleLifecycle.text= timelineItem.titleLifecycle
        holder.startDayLifecycle.text= timelineItem.startDayLifecycle
        holder.endDayLifecycle.text= timelineItem.endDayLifecycle
        holder.repeatCycle.text= timelineItem.repeatCycle
        holder.categoryLifecycle.setImageResource(timelineItem.categoryLifecycle)
    }

    override fun getItemCount(): Int { return timelineItems.size }
}