package com.grinleaf.onesightdiaryplanner

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainCheckListAdapter(val context:Context, val checkListItems:MutableList<ChecklistItem>):RecyclerView.Adapter<MainCheckListAdapter.VH>() {
    inner class VH(itemView: View):RecyclerView.ViewHolder(itemView){
        val content:CheckBox by lazy { itemView.findViewById(R.id.checkbox_maincontent_checklist_theme) }
        lateinit var day:TextView
        val categoryImage:ImageView by lazy { itemView.findViewById(R.id.iv_category_checklist_theme) }
//        val subContent:CheckBox? by lazy { itemView.findViewById(R.id.checkbox_subcontent_checklist_theme) }
    }
//    val TYPE_MAIN = 0
//    val TYPE_SUB = 1
//
//    override fun getItemViewType(position: Int): Int {
//        return if (checkListItems.get(position).subContent==null) TYPE_MAIN
//        else TYPE_SUB
//    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        var itemView:View = LayoutInflater.from(context).inflate(R.layout.recycler_checklist_maincontent, parent, false)
        return VH(itemView)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val checklistItem= checkListItems.get(position)
        holder.content.text= checklistItem.content
        holder.categoryImage.setImageResource(checklistItem.categoryImage)
//        holder.subContent?.text= checklistItem.subContent
//        LinearLayoutManager(
//            holder.
//        )
    }

    override fun getItemCount(): Int { return checkListItems.size }
}