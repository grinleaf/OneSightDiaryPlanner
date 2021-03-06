package com.grinleaf.onesightdiaryplanner

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.grinleaf.onesightdiaryplanner.databinding.FragmentDailynoteBinding
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

class DailyNoteFragment:Fragment() {
    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View? {
        return binding.root
    }
    val binding by lazy { FragmentDailynoteBinding.inflate(layoutInflater) }
    val adapter by lazy { DailyNoteAdapter(requireContext(),G.matchDateDailyNoteItem) }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var now = ""+LocalDate.now()
        val nowDate:Date= SimpleDateFormat("yyyy-MM-dd", Locale("ko","KR")).parse(now)
        now= SimpleDateFormat("yyyy-MM-dd", Locale("ko","KR")).format(nowDate)
        binding.tvDailynoteDay.text= now
        G.dayOfDailyNote=now
        binding.ivDatepickerDailynote.setOnClickListener{
            val cal= Calendar.getInstance()
            val dateSetListener = DatePickerDialog.OnDateSetListener{
                    view, year, month, dayOfMonth -> G.dayOfDailyNote = "${year}-${month+1}-${dayOfMonth}"
                val date:Date= SimpleDateFormat("yyyy-MM-dd", Locale("ko","KR")).parse(G.dayOfDailyNote)
                G.dayOfDailyNote= SimpleDateFormat("yyyy-MM-dd", Locale("ko","KR")).format(date)
                binding.tvDailynoteDay.text = G.dayOfDailyNote
                displayReset()
                adapter.notifyDataSetChanged()
            }
            DatePickerDialog(requireContext(),dateSetListener,cal.get(Calendar.YEAR),cal.get(
                Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH)).show()
        }

        displayReset()
        binding.recyclerDailynote.adapter= adapter

        binding.firstAddDateDailynote.setOnClickListener {
            val intent= Intent(requireContext(), DateEditActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        displayReset()
    }

    private fun displayReset(){
        Handler(Looper.getMainLooper()).postDelayed({
            adapter.notifyDataSetChanged()
            G.matchDateDailyNoteItem.clear()
            for(item in G.dailyNoteItems) {
                if (G.dayOfDailyNote == item.day) G.matchDateDailyNoteItem.add(item)
            }
            if(G.matchDateDailyNoteItem.size==0) binding.firstAddDateDailynote.visibility= View.VISIBLE
            else binding.firstAddDateDailynote.visibility= View.GONE
        },100)
    }
}