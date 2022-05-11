package com.grinleaf.onesightdiaryplanner

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.applikeysolutions.cosmocalendar.selection.OnDaySelectedListener
import com.applikeysolutions.cosmocalendar.selection.SingleSelectionManager
import com.bumptech.glide.Glide
import com.grinleaf.onesightdiaryplanner.databinding.FragmentCalendarMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class CalendarFragment:Fragment() {
    val binding by lazy { FragmentCalendarMainBinding.inflate(layoutInflater) }
    var selectedDay:String= ""
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
//            if(selectedDay==G.dayOfCalendar){ //요걸 비교하면 안된당
//                binding.cardviewTodayStateCalendar.visibility= View.VISIBLE
//            }else{
//                binding.cardviewTodayStateCalendar.visibility= View.GONE
//            }
        })
        binding.tvTodayEmoCalendar.setOnClickListener {
            val intent= Intent(requireContext(),DialogEmoActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        if(G.saveEmoImages=="") Glide.with(requireContext()).load(R.drawable.ic_question_mark).into(binding.ivTodayEmoCalendar)
        else Glide.with(requireContext()).load(G.saveEmoImages).into(binding.ivTodayEmoCalendar)
        uploadEmo()
    }

    fun uploadEmo(){
        //G.saveEmo + 선택된 날짜와 함께 DB에 업로드, 날짜 클릭 시 해당 이모티콘과 todayis 레이아웃 visible 되도록 코드
        val retrofit= RetrofitHelper.getRetrofitInstance()
        val retrofitService= retrofit.create(RetrofitService::class.java)
        val call= retrofitService.uploadSelectedEmo(G.userEmail, selectedDay,G.saveEmoImages)
        call.enqueue(object: Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                val list= response.body()
                Log.i("aaa","uploadEmo call 객체 : "+list?.get(0).toString())
                selectedDay= ""
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.i("aaa","error: "+t.message)
            }

        })
    }

    fun downloadEmo(){

    }
}