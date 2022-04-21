package com.grinleaf.onesightdiaryplanner

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class DailyNoteAdapter(val context:Context, val dailyItems:MutableList<DailyItem>):RecyclerView.Adapter<DailyNoteAdapter.VH>() {
    inner class VH(itemView: View):RecyclerView.ViewHolder(itemView){
        val content:TextView by lazy { itemView.findViewById(R.id.tv_content_daily_theme) }
        val day:TextView by lazy { itemView.findViewById(R.id.tv_day_daily_theme) }
        val categoryImage:ImageView by lazy { itemView.findViewById(R.id.iv_category_daily_theme) }
        val dayImage:ImageView by lazy { itemView.findViewById(R.id.iv_image_daily_theme) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        lateinit var itemView:View
        if(true) {//dayImage 값이 입력되지 않았을 경우 조건식 사용할 것
            val random = (0..3).random()    //random 대신 shuffle() 써보기 ! 중복 제거해야함
            when (random) {
                0 -> { itemView = LayoutInflater.from(context).inflate(R.layout.recycler_theme_daily_01, parent, false) }
                1 -> { itemView = LayoutInflater.from(context).inflate(R.layout.recycler_theme_daily_02, parent, false) }
                2 -> { itemView = LayoutInflater.from(context).inflate(R.layout.recycler_theme_daily_03, parent, false) }
                3 -> { itemView = LayoutInflater.from(context).inflate(R.layout.recycler_theme_daily_04, parent, false) }
            }
        }else itemView = LayoutInflater.from(context).inflate(R.layout.recycler_theme_daily_05, parent, false)
        return VH(itemView)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val dailyItem= dailyItems.get(position)
        holder.day.text= dailyItem.day
        holder.content.text= dailyItem.content
        holder.categoryImage.setImageResource(dailyItem.dayImage)   //요건 int로
        holder.dayImage.setImageResource(dailyItem.dayImage)    //요건 db 구축 후에 String 으로 바꿔줄 것! (VH 클래스도 마찬가지)
    }

    override fun getItemCount(): Int { return dailyItems.size }
}