package com.grinleaf.onesightdiaryplanner

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView


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

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: VH, position: Int) {
        val totalItem = totalItems.get(position)
        holder.dayOfTimeline.text= totalItem
        G.dayOfTimeline= totalItem  //day 바의 해당 날짜
        holder.dailyRecycler.adapter= dailyAdapter
        holder.checkRecycler.adapter= checkAdapter
        holder.lifeRecycler.adapter= lifeAdapter

        Handler(Looper.getMainLooper()).postDelayed({
            if(G.visibleCountDaily.contains(holder.dayOfTimeline.text.toString())||
                G.visibleCountCheck.contains(holder.dayOfTimeline.text.toString())||
                G.visibleCountLife.contains(holder.dayOfTimeline.text.toString())){
                G.lastVisibilityDaily= HAVE_ITEM
            }else{
                G.lastVisibilityDaily= EMPTY_ITEM
            }
            when(G.lastVisibilityDaily){
                HAVE_ITEM-> holder.layout.visibility= View.VISIBLE
                EMPTY_ITEM-> holder.layout.visibility= View.GONE
            }
        }, 5) //1000 = 1초 후 실행
    }
    val HAVE_ITEM= 1
    val EMPTY_ITEM= 0

    override fun getItemCount(): Int { return totalItems.size }


}