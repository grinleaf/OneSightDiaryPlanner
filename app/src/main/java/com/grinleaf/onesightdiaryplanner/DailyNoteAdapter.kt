package com.grinleaf.onesightdiaryplanner

import android.content.Context
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class DailyNoteAdapter(val context:Context, val dailyItems:MutableList<DailyItem>):RecyclerView.Adapter<DailyNoteAdapter.VH>() {
    inner class VH(itemView: View):RecyclerView.ViewHolder(itemView){
        val content:TextView by lazy { itemView.findViewById(R.id.tv_content_daily_theme) }
        val day:TextView by lazy { itemView.findViewById(R.id.tv_day_daily_theme) }
        val categoryImage:ImageView by lazy { itemView.findViewById(R.id.iv_category_daily_theme) }
        val dayImage:ImageView by lazy { itemView.findViewById(R.id.iv_image_daily_theme) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        lateinit var itemView:View
        Log.i("aaa", dailyItems.get(dailyItems.size-1).dayImage.toString())
        if(dailyItems.get(dailyItems.size-1).dayImage==null) {  //이미지가 등록되지 않았을 때
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
        holder.content.text= dailyItem.content
        Glide.with(context).load(dailyItem.categoryImage).into(holder.categoryImage)
        holder.dayImage.setImageResource(R.drawable.tutorial_sample04)
//        val uri:Uri= dailyItem.dayImage
//        Glide.with(context).load(uri).into(holder.dayImage)  //요건 db 구축 후에 String 으로 바꿔줄 것! (VH 클래스도 마찬가지)
    }

    override fun getItemCount(): Int { return dailyItems.size }
}