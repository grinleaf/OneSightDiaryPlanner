package com.grinleaf.onesightdiaryplanner

import android.app.DatePickerDialog
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.os.persistableBundleOf
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*

class CheckListMainAdapter(val context:Context, val checkListItems:MutableList<ChecklistItem>):RecyclerView.Adapter<CheckListMainAdapter.VH>() {
    inner class VH(itemView: View):RecyclerView.ViewHolder(itemView){
        val content:CheckBox by lazy { itemView.findViewById(R.id.checkbox_maincontent_checklist_theme) }
        val categoryImage:ImageView by lazy { itemView.findViewById(R.id.iv_category_checklist_theme) }
        val layout:LinearLayout by lazy { itemView.findViewById(R.id.layout_frame_maincontent_checklist_theme) }
    }
    lateinit var subItem:MutableList<ChecklistSubItem>
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
        val checklistItem = checkListItems.get(position)
//        if (checklistItem.day.equals(G.dayOfCheckList)) {
            holder.content.text = checklistItem.content
//            holder.categoryImage.setImageResource(checklistItem.categoryImage)
            Log.i("aaa", "checklistmain bindviewholder if")
//        }else{
//            holder.layout.visibility= View.GONE
//            Log.i("aaa", "checklistmain bindviewholder else")
//        }
    }

    override fun getItemCount(): Int { return checkListItems.size }
}