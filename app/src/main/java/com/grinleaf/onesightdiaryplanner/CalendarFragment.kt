package com.grinleaf.onesightdiaryplanner

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.core.view.size
import androidx.fragment.app.Fragment
import com.applikeysolutions.cosmocalendar.dialog.CalendarDialog
import com.applikeysolutions.cosmocalendar.dialog.OnDaysSelectionListener
import com.applikeysolutions.cosmocalendar.selection.OnDaySelectedListener
import com.applikeysolutions.cosmocalendar.selection.RangeSelectionManager
import com.applikeysolutions.cosmocalendar.selection.SingleSelectionManager
import com.bumptech.glide.Glide
import com.grinleaf.onesightdiaryplanner.databinding.FragmentCalendarMainBinding
import java.text.SimpleDateFormat
import java.util.*


class CalendarFragment:Fragment() {
    val binding by lazy { FragmentCalendarMainBinding.inflate(layoutInflater) }
    lateinit var selectedDay:String
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        binding.ivTodayEmoCalendar.visibility= View.GONE //시작시 숨겨뒀다가 날짜 클릭 시 visible 되도록

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
            selectedDay= "$year-$month-$dayMultiWord"
            Log.i("aaa", "selectedDay: $selectedDay")
            Log.i("aaa", "dayOfWeek : $dayOfWeek  month: $month  day: $daySingleWord  year: $year")
            //DB 에서 day 리스트를 다운로드하고(이거 튜토리얼 페이지에서 하자) selectedDay 와 비교, 그에 맞는 이모티콘 번호를 가져오기. 해당 번호에 따른 이미지 glide(when 절)
            if(selectedDay==G.dayOfCalendar){ //요걸 비교하면 안된당
                binding.cardviewTodayStateCalendar.visibility= View.VISIBLE
            }else{
                binding.cardviewTodayStateCalendar.visibility= View.GONE
            }
        })
        binding.tvTodayEmoCalendar.setOnClickListener {
            val intent= Intent(requireContext(),DialogEmoActivity::class.java)
            //resultLauncher로 결과값 가져오기
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        if(G.saveEmoImages=="") Glide.with(requireContext()).load(R.drawable.ic_question_mark).into(binding.ivTodayEmoCalendar)
        else Glide.with(requireContext()).load(G.saveEmoImages).into(binding.ivTodayEmoCalendar)

        //G.saveEmo + 선택된 날짜와 함께 DB에 업로드, 날짜 클릭 시 해당 이모티콘과 todayis 레이아웃 visible 되도록 코드
        val emoUploadList= SelectedDayEmo(G.userEmail, selectedDay,G.saveEmoImages)
    }
}