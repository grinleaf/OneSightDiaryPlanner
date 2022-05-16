package com.grinleaf.onesightdiaryplanner

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class TimelineDailyAdapter(val context:Context, val dailyItems:MutableList<DailyItem>):RecyclerView.Adapter<TimelineDailyAdapter.VH>(){
    inner class VH(itemView: View):RecyclerView.ViewHolder(itemView){
        val titleDailyNote: TextView by lazy { itemView.findViewById(R.id.tv_title_timeline_daily) }
        val dayDailyNote: TextView by lazy { itemView.findViewById(R.id.tv_day_timeline_daily) }
        val categoryDailyNote: ImageView by lazy { itemView.findViewById(R.id.iv_category_timeline_daily) }
        val attachImageDailyNote: ImageView by lazy { itemView.findViewById(R.id.iv_image_timeline_daily) }
        val contentDailyNote: TextView by lazy { itemView.findViewById(R.id.tv_detail_timeline_daily) }
        val layout: RelativeLayout by lazy { itemView.findViewById(R.id.layout_timeline_daily) }
    }
    var i:Int=0
    var j:Int=0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val itemView= LayoutInflater.from(context).inflate(R.layout.recycler_timeline_daily,parent,false)
        return VH(itemView)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val dailyItem = dailyItems.get(position)
        holder.dayDailyNote.text= dailyItem.day
        if (G.dayOfTimeline == holder.dayDailyNote.text) {
            holder.layout.visibility= View.VISIBLE
            G.visibleCountDaily++
            holder.titleDailyNote.text = dailyItem.content
            Glide.with(context).load(dailyItem.categoryImage).into(holder.categoryDailyNote)
            Glide.with(context).load(dailyItem.dayImage).into(holder.attachImageDailyNote)
            holder.contentDailyNote.text = dailyItem.detailContent
            i++
            Log.i("aaa", "G.dayOfTimeline if문 실행 횟수 : ${G.dayOfTimeline}-> $i 회")
            i=0 // 요거 조정해서 어디 놔야하는지 확인하기
        }else{
            holder.layout.visibility= View.GONE
            j++
            Log.i("aaa", "else문 실행 횟수 : $j")
        }
    }

    override fun getItemCount(): Int { return dailyItems.size }
}