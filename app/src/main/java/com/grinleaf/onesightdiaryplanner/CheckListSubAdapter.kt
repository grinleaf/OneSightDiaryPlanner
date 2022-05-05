package com.grinleaf.onesightdiaryplanner

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView

class CheckListSubAdapter(val context:Context, val subItems:MutableList<ChecklistSubItem>):RecyclerView.Adapter<CheckListSubAdapter.VH>() {
    inner class VH(val itemView: View):RecyclerView.ViewHolder(itemView){
        val content: CheckBox by lazy { itemView.findViewById(R.id.checkbox_subcontent_checklist_theme) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val itemView= LayoutInflater.from(context).inflate(R.layout.recycler_checklist_subcontent, parent, false)
        return VH(itemView)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val subItem= subItems.get(position)
        holder.content.text= subItem.subContent
    }

    override fun getItemCount(): Int { return subItems.size }
}