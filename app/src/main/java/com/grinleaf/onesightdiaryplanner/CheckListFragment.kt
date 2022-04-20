package com.grinleaf.onesightdiaryplanner

import android.app.DatePickerDialog
import android.content.ClipData.Item
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.grinleaf.onesightdiaryplanner.databinding.FragmentChecklistBinding
import java.time.LocalDate
import java.util.*


class CheckListFragment:Fragment() {
    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View? {
        return binding.root
    }
    val binding by lazy { FragmentChecklistBinding.inflate(layoutInflater) }
    var checkListItems= mutableListOf<ChecklistItem>()
    var dateString = ""

//    @RequiresApi(Build.VERSION_CODES.O)
//    private fun mainCheckListItems(): List<ChecklistItem> {
//        val mainList: MutableList<ChecklistItem> = mutableListOf()
//        for (i in 0..9) {
//            val item = ChecklistItem(""+LocalDate.now(), subCheckListItems())
//            mainList.add(item)
//        }
//        return mainList
//    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvDayChecklistTitle.text= ""+LocalDate.now()

        binding.ivDatepickerChecklist.setOnClickListener{
            val cal= Calendar.getInstance()
            val dateSetListener = DatePickerDialog.OnDateSetListener{
                    view, year, month, dayOfMonth -> dateString = "${year}-${month+1}-${dayOfMonth}"
                    binding.tvDayChecklistTitle.text = dateString
            }
            DatePickerDialog(requireContext(),dateSetListener,cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH)).show()
        }

        checkListItems.add(ChecklistItem("","Hello",R.drawable.ic_test_icon01_foreground))
        checkListItems.add(ChecklistItem("","Hi",R.drawable.ic_test_icon01_foreground))
        checkListItems.add(ChecklistItem("","안녕!",R.drawable.ic_test_icon01_foreground))

        binding.recyclerChecklist.adapter= MainCheckListAdapter(requireContext(), checkListItems)


//        val rvItem:RecyclerView= requireActivity().findViewById(R.id.recycler_checklist)
//        val layoutManager= LinearLayoutManager(requireContext())
//        val mainAdapterMain?= MainCheckListAdapter(mainCheckListItems())

    }

    override fun onResume() {
        super.onResume()
    }
}