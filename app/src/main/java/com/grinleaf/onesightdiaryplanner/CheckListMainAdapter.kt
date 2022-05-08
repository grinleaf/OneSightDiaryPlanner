package com.grinleaf.onesightdiaryplanner

import android.app.DatePickerDialog
import android.content.Context
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.core.os.persistableBundleOf
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import java.text.SimpleDateFormat
import java.util.*

class CheckListMainAdapter(val context:Context, val checkListItems:MutableList<ChecklistItem>):RecyclerView.Adapter<CheckListMainAdapter.VH>() {
    inner class VH(itemView: View):RecyclerView.ViewHolder(itemView){
        val content:CheckBox by lazy { itemView.findViewById(R.id.checkbox_maincontent_checklist_theme) }
        val day:TextView by lazy { itemView.findViewById(R.id.tv_day_checklist_theme)}
        val categoryImage:ImageView by lazy { itemView.findViewById(R.id.iv_category_checklist_theme) }
        val layout:LinearLayout by lazy { itemView.findViewById(R.id.layout_visible) }
//        val recycler:RecyclerView by lazy { itemView.findViewById(R.id.recycler_sub) }
    }
    val subItems= mutableListOf<ChecklistSubItem>()
    val adapter= CheckListSubAdapter(context, subItems)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        var itemView:View = LayoutInflater.from(context).inflate(R.layout.recycler_checklist_maincontent, parent, false)
        return VH(itemView)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: VH, position: Int) {
        val checklistItem = checkListItems.get(position)
        holder.day.text= checklistItem.day
        if (G.dayOfCheckList == holder.day.text) {
            holder.layout.visibility= View.VISIBLE
            holder.content.text = checklistItem.content
            Glide.with(context).load(checklistItem.categoryImage).into(holder.categoryImage)
            Log.i("aaa", "checklistmain bindviewholder if")
        }else{
            holder.layout.visibility= View.GONE
            Log.i("aaa", "checklistmain bindviewholder else")
            Log.i("aaa","G.checklistmain: "+G.dayOfCheckList+"   holder.day.text: "+holder.day.text)
        }
        //if subcontent 내용이 있을 경우 recycler 의 visibility= visible 로 바꾸는 코드 영역
//            holder.layout.visibility= View.GONE
//            Log.i("aaa", "checklistmain bindviewholder else")
//            holder.recycler.adapter= adapter
//        }




    }

    override fun getItemCount(): Int { return checkListItems.size }
}