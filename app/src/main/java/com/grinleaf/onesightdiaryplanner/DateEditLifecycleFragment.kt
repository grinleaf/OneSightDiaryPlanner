package com.grinleaf.onesightdiaryplanner

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.timepicker.TimeFormat
import com.grinleaf.onesightdiaryplanner.databinding.FragmentDateeditDailynoteBinding
import com.grinleaf.onesightdiaryplanner.databinding.FragmentDateeditLifecycleBinding
import java.sql.Time
import java.text.SimpleDateFormat
import java.util.*

class DateEditLifecycleFragment:Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }
    val binding by lazy { FragmentDateeditLifecycleBinding.inflate(layoutInflater) }
    var dateString=""
    var timeString=""
    var isCheckedBucket= false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //시작일자 설정
        binding.tvStartDayLifecycleDateEdit.setOnClickListener {
            val cal= Calendar.getInstance()
            val dateSetListener = DatePickerDialog.OnDateSetListener{
                    view, year, month, dayOfMonth -> dateString = "${year}-${month+1}-${dayOfMonth}"
                val date: Date = SimpleDateFormat("yyyy-MM-dd", Locale("ko","KR")).parse(dateString)
                dateString= SimpleDateFormat("yyyy-MM-dd", Locale("ko","KR")).format(date)
                binding.tvStartDayLifecycleDateEdit.text = dateString
            }
            DatePickerDialog(requireContext(),dateSetListener,cal.get(Calendar.YEAR),cal.get(
                Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH)).show()
        }
        //종료일자 설정
        binding.tvEndDayLifecycleDateEdit.setOnClickListener {
            val cal= Calendar.getInstance()
            val dateSetListener = DatePickerDialog.OnDateSetListener{
                    view, year, month, dayOfMonth -> dateString = "${year}-${month+1}-${dayOfMonth}"
                val date: Date = SimpleDateFormat("yyyy-MM-dd", Locale("ko","KR")).parse(dateString)
                dateString= SimpleDateFormat("yyyy-MM-dd", Locale("ko","KR")).format(date)
                binding.tvEndDayLifecycleDateEdit.text = dateString
            }
            DatePickerDialog(requireContext(),dateSetListener,cal.get(Calendar.YEAR),cal.get(
                Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH)).show()
        }

        //버킷리스트 등록 체크된 경우 버킷리스트 G 아이템에 add 하기
        binding.checkboxCheckbucketLifecycleDateEdit.setOnCheckedChangeListener { compoundButton, b ->
            if(binding.checkboxCheckbucketLifecycleDateEdit.isChecked){
                G.bucketlistItems.add(BucketlistItem(
                    binding.tvStartDayLifecycleDateEdit.text.toString(),
                    parentFragment?.view?.findViewById<TextView>(R.id.tv_title_main_date_edit)?.text.toString(),
                    R.drawable.ic_flower
                ))
        }
        }

        binding.tvSleeptimeLifecycleDateEdit.setOnClickListener {
            val cal= Calendar.getInstance()
            val timepicker= TimePickerDialog(requireContext(),
                TimePickerDialog.OnTimeSetListener { timePicker, i, i2 ->
                    var hour= timePicker.hour.toString()
                    var minute= timePicker.minute.toString()
                    timeString= hour+ " 시 "+minute+" 분"
                    binding.tvSleeptimeLifecycleDateEdit.text = timeString
                },
                cal.get(Calendar.HOUR_OF_DAY),cal.get(Calendar.MINUTE),false)
            timepicker.show()
        }
    }
}