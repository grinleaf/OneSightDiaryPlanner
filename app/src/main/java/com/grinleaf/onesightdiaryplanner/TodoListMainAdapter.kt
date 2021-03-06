package com.grinleaf.onesightdiaryplanner

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Paint
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.core.view.isEmpty
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.properties.Delegates

class TodoListMainAdapter(val context:Context, val checkListItems:MutableList<ChecklistItem>):RecyclerView.Adapter<TodoListMainAdapter.VH>() {
    inner class VH(itemView: View):RecyclerView.ViewHolder(itemView){
        val checkBox:CheckBox by lazy { itemView.findViewById(R.id.checkbox_maincontent_checklist_theme) }
        val content:TextView by lazy { itemView.findViewById(R.id.content_maincontent_checklist_theme) }
        val day:TextView by lazy { itemView.findViewById(R.id.tv_day_checklist_theme)}
        val categoryImage:ImageView by lazy { itemView.findViewById(R.id.iv_category_checklist_theme) }
        val deleteBtn:ImageView by lazy { itemView.findViewById(R.id.iv_delete_icon_maincontent_checklist_theme) }
        val layout:LinearLayout by lazy { itemView.findViewById(R.id.layout_visible) }
//        val recycler:RecyclerView by lazy { itemView.findViewById(R.id.recycler_sub) }
    }
    val subItems= mutableListOf<ChecklistSubItem>()
    val adapter= TodoListSubAdapter(context, subItems)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        var itemView:View = LayoutInflater.from(context).inflate(R.layout.recycler_checklist_maincontent, parent, false)
        return VH(itemView)
    }

    @SuppressLint("ResourceAsColor")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: VH, position: Int) {
        val checklistItem = checkListItems.get(position)
        holder.day.text= checklistItem.day
        holder.layout.visibility= View.VISIBLE
        holder.content.text = checklistItem.content
        holder.checkBox.isChecked= checklistItem.isChecked=="true"
        if(checklistItem.isChecked=="true") holder.content.paintFlags= Paint.STRIKE_THRU_TEXT_FLAG
        else holder.content.paintFlags= 0
        Glide.with(context).load(checklistItem.categoryImage).into(holder.categoryImage)
        holder.deleteBtn.setOnClickListener {
            if(checkListItems.isNotEmpty()) {
                deleteChecklistDate(position)
                checkListItems.remove(checkListItems[position])
            }
            notifyDataSetChanged()
        }
        holder.checkBox.setOnCheckedChangeListener { compoundButton, b ->
            if(b) {
                checkListItems.get(position).isChecked= true.toString()
                holder.content.setTextColor(R.color.inactivate_gray)
                holder.content.paintFlags= Paint.STRIKE_THRU_TEXT_FLAG
                Log.i("aaa", "isChecked : ${holder.checkBox.isChecked}")
                Log.i("aaa", "checkListItems[position]: ${checkListItems[position]}")
                //????????? ????????? ???????????? DB??? ?????????????????? ?????? ?????????
                updateCheckedState(position)
            }
            else if(!b) {
                checkListItems.get(position).isChecked= false.toString()
                holder.content.setTextColor(R.color.black)
                holder.content.paintFlags= 0
                Log.i("aaa", "isChecked : ${holder.checkBox.isChecked}")
                Log.i("aaa", "checkListItems[position]: ${checkListItems[position]}")
                updateCheckedState(position)
            }
        }

        //if subcontent ????????? ?????? ?????? recycler ??? visibility= visible ??? ????????? ?????? ??????
//            holder.layout.visibility= View.GONE
//            Log.i("aaa", "checklistmain bindviewholder else")
//            holder.recycler.adapter= adapter
//        }

    }

    override fun getItemCount(): Int { return checkListItems.size }

    private fun updateCheckedState(position: Int){
        val retrofit= RetrofitHelper.getRetrofitInstance()
        val retrofitService= retrofit.create(RetrofitService::class.java)
        val call= retrofitService.updateCheckedStateChecklist(
            checkListItems.get(position).email,
            checkListItems.get(position).day,
            checkListItems.get(position).content,
            checkListItems.get(position).categoryImage,
            checkListItems.get(position).detailContent,
            checkListItems.get(position).isChecked
        )
        call.enqueue(object: Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                val list= response.body()
                Log.i("aaa","update success: ${list.toString()}")
            }
            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.i("aaa", "update failed : ${t.message}")
            }
        })
    }

    private fun deleteChecklistDate(position: Int){
        val retrofit= RetrofitHelper.getRetrofitInstance()
        val retrofitService= retrofit.create(RetrofitService::class.java)
        val call= retrofitService.deleteChecklistDate(
            checkListItems.get(position).email,
            checkListItems.get(position).day,
            checkListItems.get(position).content,
            checkListItems.get(position).categoryImage,
            checkListItems.get(position).detailContent,
            checkListItems.get(position).isChecked
        )
        call.enqueue(object: Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                val list= response.body()
                Log.i("aaa","delete success: ${list.toString()}")
            }
            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.i("aaa", "delete failed : ${t.message}")
            }
        })
    }
}