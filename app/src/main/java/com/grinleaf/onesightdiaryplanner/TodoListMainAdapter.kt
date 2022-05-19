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
        val content:CheckBox by lazy { itemView.findViewById(R.id.checkbox_maincontent_checklist_theme) }
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
        if(!G.isNotEmptyChecklistRecyclerItem.contains(holder.layout.visibility.toString())) G.isNotEmptyChecklistRecyclerItem.add(holder.layout.visibility.toString())
        if (G.dayOfTodolist == holder.day.text) {
            Log.i("aaa","G.isNotEmptyRecyclerItem bindview if: ${G.isNotEmptyChecklistRecyclerItem}")
            holder.layout.visibility= View.VISIBLE
            holder.content.text = checklistItem.content
            holder.content.isChecked= checklistItem.isChecked=="true"
            Glide.with(context).load(checklistItem.categoryImage).into(holder.categoryImage)
            holder.deleteBtn.setOnClickListener {
                if(checkListItems.isNotEmpty()) {
                    deleteChecklistDate(position)
                    checkListItems.remove(checkListItems[position])
                }
                notifyDataSetChanged()
            }
            holder.content.setOnCheckedChangeListener { compoundButton, b ->
                if(b) {
                    checkListItems.get(position).isChecked= true.toString()
                    holder.content.setTextColor(R.color.inactivate_gray)
//                    holder.content.text.paintFlags(holder.content.text.paintFlags|Paint.STRIKE_THRU_TEXT_FLAG)
                    Log.i("aaa", "isChecked : ${holder.content.isChecked}")
                    Log.i("aaa", "checkListItems[position]: ${checkListItems[position]}")
                    //여기서 수정된 데이터를 DB로 업데이트하는 코드 필요함
                    updateCheckedState(position)
                }
                else if(!b) {
                    checkListItems.get(position).isChecked= false.toString()
                    holder.content.setTextColor(R.color.black)
                    Log.i("aaa", "isNotChecked : ${holder.content.isChecked}")
                    Log.i("aaa", "checkListItems[position]: ${checkListItems[position]}")
                    updateCheckedState(position)
                }
            }
        }else{
            holder.layout.visibility= View.GONE
        }
        //if subcontent 내용이 있을 경우 recycler 의 visibility= visible 로 바꾸는 코드 영역
//            holder.layout.visibility= View.GONE
//            Log.i("aaa", "checklistmain bindviewholder else")
//            holder.recycler.adapter= adapter
//        }
        Log.i("aaa","G.isnotemptycheck bindview : ${G.isNotEmptyChecklistRecyclerItem}")
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