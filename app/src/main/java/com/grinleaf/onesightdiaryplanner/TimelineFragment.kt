package com.grinleaf.onesightdiaryplanner

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.grinleaf.onesightdiaryplanner.databinding.FragmentTimelineBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

class TimelineFragment:Fragment() {
    val binding by lazy { FragmentTimelineBinding.inflate(layoutInflater) }
    val dailyAdapter by lazy { TimelineDailyAdapter(requireContext(),G.dailyNoteItems) }
    val checkAdapter by lazy { TimelineCheckAdapter(requireContext(),G.checklistItems) }
    val lifeAdapter by lazy { TimelineLifeAdapter(requireContext(),G.lifecycleItems) }
//    val recyclerList= mutableListOf<MutableList<Adapter>>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        var now = ""+ LocalDate.now()
        val nowDate: Date = SimpleDateFormat("yyyy-MM-dd", Locale("ko","KR")).parse(now)
        now= SimpleDateFormat("yyyy-MM-dd", Locale("ko","KR")).format(nowDate)
        binding.todayOfTimeline.text= now
        G.dayOfTimeline= now

        binding.todayOfTimeline.setOnClickListener{
            val cal= Calendar.getInstance()
            val dateSetListener = DatePickerDialog.OnDateSetListener{
                    view, year, month, dayOfMonth -> G.dayOfTimeline = "${year}-${month+1}-${dayOfMonth}"
                val date:Date= SimpleDateFormat("yyyy-MM-dd", Locale("ko","KR")).parse(G.dayOfTimeline)
                G.dayOfTimeline= SimpleDateFormat("yyyy-MM-dd", Locale("ko","KR")).format(date)
                binding.todayOfTimeline.text = G.dayOfTimeline
                dailyAdapter.notifyDataSetChanged()
                checkAdapter.notifyDataSetChanged()
                lifeAdapter.notifyDataSetChanged()
            }
            DatePickerDialog(requireContext(),dateSetListener,cal.get(Calendar.YEAR),cal.get(
                Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH)).show()
        }
        
        //구분선의 날짜와 동일한 날짜에 등록된 일정만 daily/check/life 순으로 보여주기 ( recycler adapter 3개 )
        binding.recyclerTimelineDaily.adapter= dailyAdapter
        binding.recyclerTimelineCheck.adapter= checkAdapter
        binding.recyclerTimelineLife.adapter= lifeAdapter
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onResume() {
        super.onResume()

        dailyAdapter.notifyDataSetChanged()
        checkAdapter.notifyDataSetChanged()
        lifeAdapter.notifyDataSetChanged()
    }

    //데이터가 입력되지 않았으면 빈문자
    private fun ifnullData(con:Int, data:String):String{
        if(con==0) return ""
        else return data
    }
}