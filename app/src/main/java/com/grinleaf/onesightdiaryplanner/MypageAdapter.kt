package com.grinleaf.onesightdiaryplanner

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MypageAdapter(val context: Context, val mypageItems:MutableList<MypageGridItem>):RecyclerView.Adapter<MypageAdapter.VH>() {
    inner class VH(itemView: View):RecyclerView.ViewHolder(itemView){
        val mypageImage:ImageView by lazy { itemView.findViewById(R.id.iv_grid_mypage) }
        val mypageContent: TextView by lazy { itemView.findViewById(R.id.tv_grid_mypage) }
    }

    //각 아이템 클릭 이벤트 처리를 위한 인터페이스
    interface OnItemClickListener {
        fun onClick(v:View, position: Int)
    }
    //어댑터 외부에서 클릭 시 이벤트 설정
    fun setItemClickListener(onItemClickListener: OnItemClickListener){
        this.itemClickListener= onItemClickListener
    }

    private lateinit var itemClickListener: OnItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val itemView= LayoutInflater.from(context).inflate(R.layout.recycler_mypage,parent,false)
        return VH(itemView)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val gridItem= mypageItems.get(position)
        holder.mypageImage.setImageResource(gridItem.mypageImage)
        holder.mypageImage.setColorFilter(R.color.black)
        holder.mypageContent.text= gridItem.mypageContent
        holder.itemView.setOnClickListener {
            itemClickListener.onClick(it, position)
        }
    }

    override fun getItemCount(): Int {return mypageItems.size}
}