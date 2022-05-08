package com.grinleaf.onesightdiaryplanner

import android.content.Context
import android.content.CursorLoader
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide


class DailyNoteAdapter(val context:Context, val dailyItems:MutableList<DailyItem>):RecyclerView.Adapter<DailyNoteAdapter.VH>() {
    inner class VH(itemView: View):RecyclerView.ViewHolder(itemView){
        val content:TextView by lazy { itemView.findViewById(R.id.tv_title_daily_theme) }
        val detailContent:TextView by lazy { itemView.findViewById(R.id.tv_content_daily_theme) }
        val day:TextView by lazy { itemView.findViewById(R.id.tv_day_daily_theme) }
        val categoryImage:ImageView by lazy { itemView.findViewById(R.id.iv_category_daily_theme) }
        val dayImage:ImageView by lazy { itemView.findViewById(R.id.iv_image_daily_theme) }
        val layout:RelativeLayout by lazy { itemView.findViewById(R.id.layout_daily_theme) }
        val categoryLayout:ImageView by lazy { itemView.findViewById(R.id.iv_category_daily_theme) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        lateinit var itemView:View
        if(false) {  //이미지가 등록되지 않았을 때
            val random = (0..3).random()
            when (random) {
                0 -> { itemView = LayoutInflater.from(context).inflate(R.layout.recycler_theme_daily_01, parent, false) }
                1 -> { itemView = LayoutInflater.from(context).inflate(R.layout.recycler_theme_daily_02, parent, false) }
                2 -> { itemView = LayoutInflater.from(context).inflate(R.layout.recycler_theme_daily_03, parent, false) }
                3 -> { itemView = LayoutInflater.from(context).inflate(R.layout.recycler_theme_daily_04, parent, false) }
            }
        }else{  //이미지가 등록되었을 때
            val random = (0..1).random()
            when(random){
                0 -> itemView = LayoutInflater.from(context).inflate(R.layout.recycler_theme_daily_05, parent, false)
                1 -> itemView = LayoutInflater.from(context).inflate(R.layout.recycler_theme_daily_06, parent, false)
            }
        }
        return VH(itemView)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val dailyItem= dailyItems.get(position)
        holder.day.text= dailyItem.day
        if (G.dayOfDailyNote == holder.day.text) { //상단 선택날짜와 day 값이 동일한 경우 출력
            holder.layout.visibility= View.VISIBLE
            holder.categoryLayout.visibility= View.VISIBLE
            holder.content.text= dailyItem.content
            holder.detailContent.text= dailyItem.detailContent
            Glide.with(context).load(dailyItem.categoryImage).into(holder.categoryImage)
            Glide.with(context).load(dailyItem.dayImage).into(holder.dayImage)
            Log.i("aaa","dailynoteAdapter bindvh if 문")
            Log.i("aaa","dayImage:"+dailyItem.dayImage)
        }else{
            holder.layout.visibility= View.GONE
            holder.categoryLayout.visibility= View.GONE
            Log.i("aaa","dailynoteAdapter bindvh else 문")
            Log.i("aaa","G.dayOfDailyNote: "+G.dayOfDailyNote+"   holder.day.text: "+holder.day.text)
        }
    }

    override fun getItemCount(): Int { return dailyItems.size }
}