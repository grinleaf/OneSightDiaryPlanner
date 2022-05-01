package com.grinleaf.onesightdiaryplanner

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.grinleaf.onesightdiaryplanner.databinding.FragmentDailynoteBinding
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

class DailyNoteFragment:Fragment() {
    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View? {
        return binding.root
    }
    val binding by lazy { FragmentDailynoteBinding.inflate(layoutInflater) }
    val adapter by lazy { DailyNoteAdapter(requireContext(),G.dailyNoteItems) }
    var dateString= ""
    lateinit var dailyTitle:String
    var categoryImage:Int= 0
    lateinit var todayAuto:String
    lateinit var attachImage:String
    lateinit var detailContent:String

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var now = ""+LocalDate.now()
        val nowDate:Date= SimpleDateFormat("yyyy-MM-dd", Locale("ko","KR")).parse(now)
        now= SimpleDateFormat("yyyy. MM. dd.", Locale("ko","KR")).format(nowDate)
        binding.tvDailynoteDay.text= now
        binding.ivDatepickerDailynote.setOnClickListener{
            val cal= Calendar.getInstance()
            val dateSetListener = DatePickerDialog.OnDateSetListener{
                    view, year, month, dayOfMonth -> dateString = "${year}-${month+1}-${dayOfMonth}"
                val date:Date= SimpleDateFormat("yyyy-MM-dd", Locale("ko","KR")).parse(dateString)
                dateString= SimpleDateFormat("yyyy. MM. dd.", Locale("ko","KR")).format(date)
                binding.tvDailynoteDay.text = dateString
            }
            DatePickerDialog(requireContext(),dateSetListener,cal.get(Calendar.YEAR),cal.get(
                Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH)).show()
        }
        binding.recyclerDailynote.adapter= adapter
        Log.i("aaa", G.dailyNoteItems.size.toString()+"G 배열 : dailynote 화면 날짜")

        binding.refresherDailynote.setOnRefreshListener {
            binding.refresherDailynote.isRefreshing= false
            adapter.notifyDataSetChanged()
        }
    }

    override fun onResume() {
        super.onResume()
//        if(G.dailyNoteItems.size==0) {
//            arguments.let {
//                todayAuto = it?.getString("todayAuto").toString()
//                dailyTitle = it?.getString("dateTitle").toString()
//                if (it != null) {
//                    categoryImage = it.getInt("categoryImage")
//                }
//                attachImage = it?.getString("attachImage").toString()
//                detailContent = it?.getString("detailContent").toString()
//
//                Log.i(
//                    "aaa",
//                    dailyTitle + "," + categoryImage + "," + todayAuto + "," + attachImage + "," + detailContent+" DailynoteFragment onPause 안"
//                )
//                G.dailyNoteItems.add(
//                    DailyItem(todayAuto,dailyTitle,categoryImage,attachImage,detailContent)
//                )
//            }
//        }
        adapter.notifyDataSetChanged()
    }

    override fun onPause() {
        super.onPause()

    }
}