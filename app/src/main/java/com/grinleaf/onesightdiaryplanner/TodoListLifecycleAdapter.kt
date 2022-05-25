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
import java.text.SimpleDateFormat
import java.util.*

class TodoListLifecycleAdapter(val context:Context, val lifecycleItems:MutableList<LifecycleItem>):RecyclerView.Adapter<TodoListLifecycleAdapter.VH>() {
    inner class VH(itemView: View):RecyclerView.ViewHolder(itemView){
        val checkBox:CheckBox by lazy { itemView.findViewById(R.id.checkbox_lifecycle_todolist_theme) }
        val content:TextView by lazy { itemView.findViewById(R.id.content_lifecycle_todolist_theme) }
        val startday:TextView by lazy { itemView.findViewById(R.id.tv_start_day_lifecycle_todolist_theme)}
        val endday:TextView by lazy { itemView.findViewById(R.id.tv_end_day_lifecycle_todolist_theme)}
        val categoryImage:ImageView by lazy { itemView.findViewById(R.id.iv_category_lifecycle_theme) }
        val deleteBtn:ImageView by lazy { itemView.findViewById(R.id.iv_delete_icon_lifecycle_todolist_theme) }
        val layout:LinearLayout by lazy { itemView.findViewById(R.id.layout_visible) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        var itemView:View = LayoutInflater.from(context).inflate(R.layout.recycler_todolist_lifecycle, parent, false)
        return VH(itemView)
    }

    @SuppressLint("SimpleDateFormat", "ResourceAsColor")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: VH, position: Int) {
        val lifecycleItem = lifecycleItems.get(position)
        holder.startday.text= lifecycleItem.day
        holder.endday.text= lifecycleItem.endDay
        holder.layout.visibility= View.VISIBLE
        holder.content.text = lifecycleItem.content
        holder.checkBox.isChecked= lifecycleItem.isChecked=="true"
        if(lifecycleItem.isChecked=="true") holder.content.paintFlags= Paint.STRIKE_THRU_TEXT_FLAG
        Glide.with(context).load(lifecycleItem.categoryImage).into(holder.categoryImage)
        holder.deleteBtn.setOnClickListener {
            if(lifecycleItems.isNotEmpty()) {
                deleteChecklistDate(position)
                lifecycleItems.remove(lifecycleItems[position])
            }
            notifyDataSetChanged()
        }
        holder.checkBox.setOnCheckedChangeListener { compoundButton, b ->
            if(b) {
                lifecycleItems.get(position).isChecked= true.toString()
                holder.content.setTextColor(R.color.inactivate_gray)
                holder.content.paintFlags= Paint.STRIKE_THRU_TEXT_FLAG
                Log.i("aaa", "isChecked : ${holder.checkBox.isChecked}")
                Log.i("aaa", "lifecycleItems[position]: ${lifecycleItems[position]}")
                //여기서 수정된 데이터를 DB로 업데이트하는 코드 필요함
                updateCheckedState(position)
            }
            else if(!b) {
                lifecycleItems.get(position).isChecked= false.toString()
                holder.content.setTextColor(R.color.black)
                Log.i("aaa", "isChecked : ${holder.checkBox.isChecked}")
                Log.i("aaa", "lifecycleItems[position]: ${lifecycleItems[position]}")
                updateCheckedState(position)
            }
        }
    }

    override fun getItemCount(): Int { return lifecycleItems.size }

    private fun updateCheckedState(position: Int){
        val retrofit= RetrofitHelper.getRetrofitInstance()
        val retrofitService= retrofit.create(RetrofitService::class.java)
        val call= retrofitService.updateCheckedStateLifecycle(
            lifecycleItems.get(position).email,
            lifecycleItems.get(position).content,
            lifecycleItems.get(position).day,
            lifecycleItems.get(position).endDay,
            lifecycleItems.get(position).repeatCycle,
            lifecycleItems.get(position).categoryImage,
            lifecycleItems.get(position).isBucket,
            lifecycleItems.get(position).isChecked
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
        val call= retrofitService.deleteLifecycleDate(
            lifecycleItems.get(position).email,
            lifecycleItems.get(position).content,
            lifecycleItems.get(position).day,
            lifecycleItems.get(position).endDay,
            lifecycleItems.get(position).repeatCycle,
            lifecycleItems.get(position).categoryImage,
            lifecycleItems.get(position).isBucket,
            lifecycleItems.get(position).isChecked
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