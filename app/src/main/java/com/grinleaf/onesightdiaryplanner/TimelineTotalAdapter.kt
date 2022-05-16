package com.grinleaf.onesightdiaryplanner

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class TimelineTotalAdapter(val context:Context, val totalItems:MutableList<String>):RecyclerView.Adapter<TimelineTotalAdapter.VH>(){
    inner class VH(itemView: View):RecyclerView.ViewHolder(itemView){
        val dayOfTimeline:TextView by lazy { itemView.findViewById(R.id.today_of_timeline) }
        val dailyRecycler:RecyclerView by lazy { itemView.findViewById(R.id.recycler_timeline_daily) }
        val checkRecycler:RecyclerView by lazy { itemView.findViewById(R.id.recycler_timeline_check) }
        val lifeRecycler:RecyclerView by lazy { itemView.findViewById(R.id.recycler_timeline_life) }
        val layout:LinearLayout by lazy { itemView.findViewById(R.id.layout_visible) }
    }
    private val dailyAdapter by lazy { TimelineDailyAdapter(context,G.dailyNoteItems) }
    private val checkAdapter by lazy { TimelineCheckAdapter(context,G.checklistItems) }
    private val lifeAdapter by lazy { TimelineLifeAdapter(context,G.lifecycleItems) }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val itemView= LayoutInflater.from(context).inflate(R.layout.recycler_timeline_total,parent,false)
        return VH(itemView)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val totalItem = totalItems.get(position)
        holder.dayOfTimeline.text= totalItem
        G.dayOfTimeline= totalItem
        holder.dailyRecycler.adapter= dailyAdapter
        holder.checkRecycler.adapter= checkAdapter
        holder.lifeRecycler.adapter= lifeAdapter
    }

    override fun onViewRecycled(holder: VH) {
        super.onViewRecycled(holder)
        if(G.visibleCountDaily==0) holder.dailyRecycler.visibility= View.GONE
        else holder.dailyRecycler.visibility= View.VISIBLE
        if(G.visibleCountCheck==0) holder.checkRecycler.visibility= View.GONE
        else holder.checkRecycler.visibility= View.VISIBLE
        if(G.visibleCountLife==0) holder.lifeRecycler.visibility= View.GONE
        else holder.lifeRecycler.visibility= View.VISIBLE
    }

    override fun getItemCount(): Int { return totalItems.size }
}