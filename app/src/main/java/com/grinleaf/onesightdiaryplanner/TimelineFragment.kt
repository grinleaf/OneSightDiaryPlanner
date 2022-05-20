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
    val totalAdapter by lazy { TimelineTotalAdapter(requireContext(),G.dayOfTimelines)}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //오늘 날짜 구하기
        var now = ""+ LocalDate.now()
        val nowDate: Date = SimpleDateFormat("yyyy-MM-dd", Locale("ko","KR")).parse(now) //date 파싱
        now= SimpleDateFormat("yyyy-MM-dd", Locale("ko","KR")).format(nowDate) //date -> 오늘날짜(String)
        G.dayOfTimeline= now
        //이전 날짜들 구해서 dayOfTimelines 리스트에 add 하기(해당 지난 일수만큼 반복)
        loop@ for(i in 0 until LocalDate.now().dayOfMonth) {
            val calendar = Calendar.getInstance()
            calendar.time = nowDate
            calendar.add(Calendar.DATE,-i)
            if(nowDate.year==calendar.time.year) {
                val prevDate = calendar.time
                val format = SimpleDateFormat("yyyy-MM-dd")
                var prevDay = format.format(prevDate)
                G.dayOfTimelines.add(prevDay)
            }else break@loop
        }
        binding.recyclerTimelineTotal.adapter= totalAdapter
        totalAdapter.notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onResume() {
        super.onResume()

        totalAdapter.notifyDataSetChanged()
    }

}