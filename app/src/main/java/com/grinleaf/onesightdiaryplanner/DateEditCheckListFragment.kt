package com.grinleaf.onesightdiaryplanner

import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.grinleaf.onesightdiaryplanner.databinding.FragmentDateeditChecklistBinding
import com.grinleaf.onesightdiaryplanner.databinding.FragmentDateeditDailynoteBinding
import java.time.LocalDate
import java.util.*

class DateEditCheckListFragment:Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }
    val binding by lazy { FragmentDateeditChecklistBinding.inflate(layoutInflater) }
    var dateString= ""

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvTodayAutoChecklistDateEdit.text= ""+LocalDate.now()
        binding.tvTodayAutoChecklistDateEdit.setOnClickListener {
            val cal= Calendar.getInstance()
            val dateSetListener = DatePickerDialog.OnDateSetListener{
                    view, year, month, dayOfMonth -> dateString = "${year}-${month+1}-${dayOfMonth}"
                binding.tvTodayAutoChecklistDateEdit.text = dateString
            }
            DatePickerDialog(requireContext(),dateSetListener,cal.get(Calendar.YEAR),cal.get(
                Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH)).show()
        }
    }
}