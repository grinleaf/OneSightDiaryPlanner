package com.grinleaf.onesightdiaryplanner
//
//import android.content.Context
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.Adapter
//import android.widget.ImageView
//import android.widget.TextView
//import androidx.recyclerview.widget.RecyclerView
//import com.bumptech.glide.Glide
//import com.grinleaf.onesightdiaryplanner.databinding.RecyclerTimelineTotalBinding
//
//class TimelineTotalAdapter(val context:Context, val binding: RecyclerTimelineTotalBinding):RecyclerView.Adapter<TimelineTotalAdapter.VH>() {
//    inner class VH(itemView:View):RecyclerView.ViewHolder(itemView){
//        val dailyAdapter by lazy { TimelineDailyAdapter(context,G.dailyNoteItems) }
//        val checkAdapter by lazy { TimelineCheckAdapter(context,G.checklistItems) }
//        val lifeAdapter by lazy { TimelineLifeAdapter(context,G.lifecycleItems) }
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
//        val itemView= LayoutInflater.from(context).inflate(R.layout.recycler_timeline_total,parent,false)
//        return VH(itemView)
//    }
//
//    override fun onBindViewHolder(holder: VH, position: Int) {
//        if(G.dailyNoteItems.size!=0) binding.recyclerTimelineDaily.adapter= dailyAdapter
//        if(G.checklistItems.size!=0) binding.recyclerTimelineCheck.adapter= checkAdapter
//        if(G.lifecycleItems.size!=0) binding.recyclerTimelineLife.adapter= lifeAdapter
//    }
//
//    override fun getItemCount(): Int {
//
//    }
//}