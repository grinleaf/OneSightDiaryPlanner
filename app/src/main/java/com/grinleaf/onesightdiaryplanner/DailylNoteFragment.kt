package com.grinleaf.onesightdiaryplanner

import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.grinleaf.onesightdiaryplanner.databinding.FragmentDailynoteBinding
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

class DailylNoteFragment:Fragment() {
    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View? {
        return binding.root
    }
    val binding by lazy { FragmentDailynoteBinding.inflate(layoutInflater) }
    var dailyItems= mutableListOf<DailyItem>()
    var dateString= ""
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

        //DailyItem 데이터 배열 만들기
        //1. DateEditActivity --> 각 Fragment 로 보낸 값을 받기
        var dateTitle:String= ""
        var categoryImage= 0
        val bundle= arguments
        if(bundle!=null){
            dateTitle= bundle.getString("dateTitle","")
            categoryImage= bundle.getInt("categoryImage",0)
        }
        //2. 각 fragment 의 edit fragment (DateEditDailynoteFragment) --> 각 Fragment 로 보낸 값을 받기
        val todayAuto= arguments?.getString("todayAuto")
        val attachImage= arguments?.getString("attachImage")
        val detailContent= arguments?.getString("detailContent")
        Log.i("aaa",todayAuto+","+dateTitle+","+categoryImage+","+attachImage)
        //3. 배열 추가
        dailyItems.add((DailyItem(todayAuto.toString(), dateTitle,categoryImage,attachImage.toString())))
        
        binding.recyclerDailynote.adapter= DailyNoteAdapter(requireContext(),dailyItems)
    }

    override fun onResume() {
        super.onResume()
    }
}