package com.grinleaf.onesightdiaryplanner

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


class CheckListFragment:Fragment() {
    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View? {
        return binding.root
    }
    val binding by lazy { FragmentChecklistBinding.inflate(layoutInflater) }
    var checkListItems= mutableListOf<ChecklistItem>()

    @RequiresApi(Build.VERSION_CODES.O)
    private fun mainCheckListItems(): List<ChecklistItem> {
        val mainList: MutableList<ChecklistItem> = mutableListOf()
        for (i in 0..9) {
            val item = ChecklistItem(""+LocalDate.now(), subCheckListItems())
            mainList.add(item)
        }
        return mainList
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val rvItem:RecyclerView= requireActivity().findViewById(R.id.recycler_checklist)
        val layoutManager= LinearLayoutManager(requireContext())
        val mainAdapterMain?= MainCheckListAdapter(mainCheckListItems())

    }

    override fun onResume() {
        super.onResume()
    }
}