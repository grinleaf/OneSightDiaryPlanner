package com.grinleaf.onesightdiaryplanner

import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.grinleaf.onesightdiaryplanner.databinding.FragmentDailynoteBinding
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
        binding.tvDailynoteDay.text= ""+ LocalDate.now()
        binding.ivDatepickerDailynote.setOnClickListener{
            val cal= Calendar.getInstance()
            val dateSetListener = DatePickerDialog.OnDateSetListener{
                    view, year, month, dayOfMonth -> dateString = "${year}-${month+1}-${dayOfMonth}"
                binding.tvDailynoteDay.text = dateString
            }
            DatePickerDialog(requireContext(),dateSetListener,cal.get(Calendar.YEAR),cal.get(
                Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH)).show()
        }

        dailyItems.add(DailyItem("2022.04.20","Hello",R.drawable.ic_test_icon01_foreground,R.drawable.tutorial_sample01))
        dailyItems.add(DailyItem("2022.04.21","Nice",R.drawable.ic_test_icon01_foreground,R.drawable.tutorial_sample01))
        dailyItems.add(DailyItem("2022.04.30","Kotlin",R.drawable.ic_test_icon01_foreground,R.drawable.tutorial_sample01))

        binding.recyclerDailynote.adapter= DailyNoteAdapter(requireContext(),dailyItems)
    }

    override fun onResume() {
        super.onResume()
    }
}