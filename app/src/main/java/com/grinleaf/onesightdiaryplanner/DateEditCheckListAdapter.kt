package com.grinleaf.onesightdiaryplanner

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.persistableBundleOf
import androidx.recyclerview.widget.RecyclerView

class DateEditCheckListAdapter(val context: Context, val subPlans:MutableList<String>): RecyclerView.Adapter<DateEditCheckListAdapter.VH>() {
    inner class VH(itemView: View):RecyclerView.ViewHolder(itemView){
        val subPlan:TextView by lazy { itemView.findViewById(R.id.tv_recycler_theme_checklist_date_edit) }
        val deleteBtn: ImageView by lazy { itemView.findViewById(R.id.iv_recycler_theme_checklist_date_edit) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val itemView= LayoutInflater.from(context).inflate(R.layout.recycler_dateedit_checklist,parent,false)
        return VH(itemView)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val subPlan= subPlans.get(position)
        holder.subPlan.visibility= View.VISIBLE
        holder.subPlan.text= subPlan
        holder.deleteBtn.setOnClickListener{
            subPlans.removeAt(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, subPlans.size);
        }
    }

    override fun getItemCount(): Int { return subPlans.size }
}