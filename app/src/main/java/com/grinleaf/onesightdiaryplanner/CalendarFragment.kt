package com.grinleaf.onesightdiaryplanner

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.size
import androidx.fragment.app.Fragment
import com.applikeysolutions.cosmocalendar.dialog.CalendarDialog
import com.applikeysolutions.cosmocalendar.dialog.OnDaysSelectionListener
import com.applikeysolutions.cosmocalendar.selection.OnDaySelectedListener
import com.applikeysolutions.cosmocalendar.selection.RangeSelectionManager
import com.applikeysolutions.cosmocalendar.selection.SingleSelectionManager
import com.grinleaf.onesightdiaryplanner.databinding.FragmentCalendarMainBinding
import java.text.SimpleDateFormat
import java.util.*


class CalendarFragment:Fragment() {
    val binding by lazy { FragmentCalendarMainBinding.inflate(layoutInflater) }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.calendarView.isShowDaysOfWeekTitle= false
        binding.calendarView.selectionManager= SingleSelectionManager(OnDaySelectedListener {

            val selectedDaytoString= binding.calendarView.selectedDays[0].toString()
            val dateFormat= selectedDaytoString.replace("Day{day=","").replace("}","")
            Log.i("aaa","dateFormat: $dateFormat")
            var date= dateFormat.split(" ")
            Log.i("aaa","date: $date")

            val dayOfWeek= date[0]  //선택된 요일
            val year= date[5]
            val daySingleWord= binding.calendarView.selectedDays[0].dayNumber   //9
            val dayMultiWord= date[2]                                           //09
            val month= when(date[1]){
                "Jan" -> "01"
                "Feb" -> "02"
                "Mar" -> "03"
                "Apr" -> "04"
                "May" -> "05"
                "Jun" -> "06"
                "Jul" -> "07"
                "Aug" -> "08"
                "Sep" -> "09"
                "Oct" -> "10"
                "Nov" -> "11"
                "Dec" -> "12"
                else -> ""
            }

            val selectedDay= "$year-$month-$dayMultiWord"
            Log.i("aaa", "selectedDay: $selectedDay")
            Log.i("aaa", "dayOfWeek : $dayOfWeek  month: $month  day: $daySingleWord  year: $year")
            if(selectedDay==G.dayOfCalendar){
                binding.tvTodayStateCalendar.visibility= View.VISIBLE
            }else{
                binding.tvTodayStateCalendar.visibility= View.GONE
            }
        })
    }
}