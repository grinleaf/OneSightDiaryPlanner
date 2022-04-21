package com.grinleaf.onesightdiaryplanner

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class BucketlistAdapter(val context: Context, val bucketlistItems:MutableList<BucketlistItem>):RecyclerView.Adapter<BucketlistAdapter.VH>() {
    inner class VH(itemView: View):RecyclerView.ViewHolder(itemView){
        val day: TextView by lazy { itemView.findViewById(R.id.tv_day_bucketlist_theme) }
        val categoryImage: ImageView by lazy { itemView.findViewById(R.id.iv_category_bucketlist_theme) }
        val content: TextView by lazy { itemView.findViewById(R.id.tv_title_bucketlist_theme) }
    }

    private var i= 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        lateinit var itemView:View
        when(i%2) {
            0 -> {
                itemView = LayoutInflater.from(context).inflate(R.layout.recycler_theme_bucket_01, parent, false)
                i++
            }
            1 -> {
                itemView = LayoutInflater.from(context).inflate(R.layout.recycler_theme_bucket_02, parent, false)
                i++
            }
        }
        return VH(itemView)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val bucketlistItem= bucketlistItems.get(position)
        holder.day.text= bucketlistItem.day
        holder.categoryImage.setImageResource(bucketlistItem.categoryImage)
        holder.content.text= bucketlistItem.content
    }

    override fun getItemCount(): Int { return bucketlistItems.size }
}