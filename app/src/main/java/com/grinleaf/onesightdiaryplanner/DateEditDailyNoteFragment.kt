package com.grinleaf.onesightdiaryplanner

import android.app.Activity.RESULT_CANCELED
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.grinleaf.onesightdiaryplanner.databinding.FragmentDateeditDailynoteBinding
import java.net.URI
import java.time.LocalDate
import java.time.MonthDay.now
import java.time.Year.now
import java.util.*

class DateEditDailyNoteFragment:Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }
    val binding by lazy { FragmentDateeditDailynoteBinding.inflate(layoutInflater) }
    var dateString= ""
    lateinit var resultLauncher:ActivityResultLauncher<Intent>

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvTodayAutoDailynoteDateEdit.text= ""+LocalDate.now()
        binding.tvTodayAutoDailynoteDateEdit.setOnClickListener {
            val cal= Calendar.getInstance()
            val dateSetListener = DatePickerDialog.OnDateSetListener{
                    view, year, month, dayOfMonth -> dateString = "${year}-${month+1}-${dayOfMonth}"
                binding.tvTodayAutoDailynoteDateEdit.text = dateString
            }
            DatePickerDialog(requireContext(),dateSetListener,cal.get(Calendar.YEAR),cal.get(
                Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH)).show()
        }
        resultLauncher= registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            if(it.resultCode!=RESULT_CANCELED){
                val uri= it.data?.data
                Glide.with(requireContext()).load(uri).into(binding.ivAttachImageDailynoteDateEdit)
            }
        }

        binding.ivAttachImageDailynoteDateEdit.setOnClickListener{
            val intent= Intent().setAction(Intent.ACTION_PICK).setType("image/*")
            resultLauncher.launch(intent)
        }
    }
}