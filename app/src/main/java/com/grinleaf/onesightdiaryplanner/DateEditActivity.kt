package com.grinleaf.onesightdiaryplanner

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.grinleaf.onesightdiaryplanner.databinding.ActivityDateEditBinding
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

class DateEditActivity:AppCompatActivity() {
    val binding by lazy { ActivityDateEditBinding.inflate(layoutInflater) }
    val fragments: MutableList<Fragment> by lazy { mutableListOf() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        fragments.add(DateEditDailyNoteFragment())
        fragments.add(DateEditCheckListFragment())
        fragments.add(DateEditLifecycleFragment())
        fragments.add(DateEditBucketListFragment())

        supportFragmentManager.beginTransaction().add(R.id.date_edit_container,fragments[0]).commit()

        val spinnerItems= resources.getStringArray(R.array.spinner_array)
        val adapter= ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,spinnerItems)
        binding.spinnerDateEdit.adapter= adapter
        binding.spinnerDateEdit.onItemSelectedListener= object: AdapterView.OnItemSelectedListener{
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                supportFragmentManager.fragments.forEach{
                    supportFragmentManager.beginTransaction().hide(it).commit()
                }
                val tran= supportFragmentManager.beginTransaction()
                when(p2){
                    0 -> {
                        tran.show(fragments[p2])
                        //일정등록 버튼 클릭 --> 입력된 데이터를 해당 프래그먼트로 전달 --> MainActivity 로 화면 전환
                        binding.btnRegistDateedit.setOnClickListener { clickRegistDailyNoteDate() }
                    }
                    1 -> {
                        if(!supportFragmentManager.fragments.contains(fragments[p2])) tran.add(R.id.date_edit_container,fragments[p2])
                        tran.show(fragments[p2])
                        binding.btnRegistDateedit.setOnClickListener { clickRegistCheckListDate() }
                    }
                    2 -> {
                        if(!supportFragmentManager.fragments.contains(fragments[p2])) tran.add(R.id.date_edit_container,fragments[p2])
                        tran.show(fragments[p2])
                        binding.btnRegistDateedit.setOnClickListener { clickRegistLifecycleDate() }
                    }
                    3 -> {
                        if(!supportFragmentManager.fragments.contains(fragments[p2])) tran.add(R.id.date_edit_container,fragments[p2])
                        tran.show(fragments[p2])
                        binding.btnRegistDateedit.setOnClickListener { clickRegistBucketListDate() }
                    }
                }
                tran.commit()
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }
        binding.ivCategoryMainDateEdit.setOnClickListener { clickCategory() }
    }

    private fun clickCategory(){
        binding.ivCategoryMainDateEdit.setImageResource(R.drawable.tutorial_sample04)
        val intent= Intent(this, CategoryActivity::class.java)
        startActivity(intent)
    }

    //dailyNoteFragment 로 데이터 전달하는 함수
    fun clickRegistDailyNoteDate(){
        if(binding.tvTitleMainDateEdit.text==null&&binding.dateEditContainer.findViewById<TextView>(R.id.tv_today_auto_dailynote_date_edit).text==null) {
            Toast.makeText(this, "일정의 제목을 입력하세요.", Toast.LENGTH_SHORT).show()
        }else{
            G.dailyNoteItems.add(
                DailyItem(
                    binding.dateEditContainer.findViewById<TextView>(R.id.tv_today_auto_dailynote_date_edit).text.toString(),
                    binding.tvTitleMainDateEdit.text.toString(),
                    G.selectedCategoryImage,
                    binding.dateEditContainer.findViewById<ImageView>(R.id.iv_attach_image_dailynote_date_edit).drawable,
                    binding.dateEditContainer.findViewById<TextView>(R.id.et_content_detail_dailynote_date_edit).text.toString()
                )
            )
            finish()
        }
    }

    fun clickRegistCheckListDate(){
        if(binding.tvTitleMainDateEdit.text==null&&binding.dateEditContainer.findViewById<TextView>(R.id.tv_today_auto_checklist_date_edit).text==null) {
            Toast.makeText(this, "일정의 제목을 입력하세요.", Toast.LENGTH_SHORT).show()
        }else{
            G.checklistItems.add(
                ChecklistItem(
                    binding.dateEditContainer.findViewById<TextView>(R.id.tv_today_auto_checklist_date_edit).text.toString(),
                    binding.tvTitleMainDateEdit.text.toString(),
                    G.selectedCategoryImage
                )
            )
            G.checklistSubItems.add(ChecklistSubItem("안녕!"))
            G.checklistSubItems.add(ChecklistSubItem("방가웡!"))
            G.checklistSubItems.add(ChecklistSubItem("ㅎㅣ히!"))
            //하위리스트 추가할 것
//            G.checklistSubItems.add(
//                ChecklistSubItem(binding.dateEditContainer.findViewById<RecyclerView>(R.id.recycler_checklist_dateedit)
//                    .findViewHolderForAdapterPosition(0).toString())
//            )
            Log.i("aaa",""+G.checklistItems.size)
            finish()
        }
    }

    fun clickRegistLifecycleDate(){
        if(binding.tvTitleMainDateEdit.text==null) {
            Toast.makeText(this, "일정의 제목을 입력하세요.", Toast.LENGTH_SHORT).show()
        }else if (binding.dateEditContainer.findViewById<TextView>(R.id.tv_start_day_lifecycle_date_edit).text=="시작일자") {
            Toast.makeText(this, "시작일자를 선택해주세요.", Toast.LENGTH_SHORT).show()
        }else if(binding.dateEditContainer.findViewById<TextView>(R.id.tv_end_day_lifecycle_date_edit).text=="종료일자" && !binding.dateEditContainer.findViewById<CheckBox>(R.id.checkbox_no_endday_lifecycle).isChecked){
            Toast.makeText(this, "종료일자를 선택해주세요.", Toast.LENGTH_SHORT).show()
        }else{
            G.lifecycleItems.add(
                LifecycleItem(
                    binding.dateEditContainer.findViewById<TextView>(R.id.tv_start_day_lifecycle_date_edit).text.toString(),
                    binding.tvTitleMainDateEdit.text.toString(),
                    G.selectedCategoryImage,
                    when(binding.dateEditContainer.findViewById<RadioGroup>(R.id.radio_group_lifecycle_date_edit).checkedRadioButtonId){
                        R.id.radio_everyday_lifecycle_date_edit-> binding.dateEditContainer.findViewById<RadioButton>(R.id.radio_everyday_lifecycle_date_edit).text
                        R.id.radio_everyweek_lifecycle_date_edit-> binding.dateEditContainer.findViewById<RadioButton>(R.id.radio_everyweek_lifecycle_date_edit).text
                        R.id.radio_everymonth_lifecycle_date_edit-> binding.dateEditContainer.findViewById<RadioButton>(R.id.radio_everymonth_lifecycle_date_edit).text
                        R.id.radio_everyyear_lifecycle_date_edit-> binding.dateEditContainer.findViewById<RadioButton>(R.id.radio_everyyear_lifecycle_date_edit).text
                        else-> {}
                    }.toString(),
                    if(binding.dateEditContainer.findViewById<CheckBox>(R.id.checkbox_no_endday_lifecycle).isChecked) {
                        "9999-12-31"
                    }else{
                        binding.dateEditContainer.findViewById<TextView>(R.id.tv_end_day_lifecycle_date_edit).text.toString()
                    }.toString()
                ))
            finish()
        }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun clickRegistBucketListDate(){
        if(binding.tvTitleMainDateEdit.text==null) {
            Toast.makeText(this, "일정의 제목을 입력하세요.", Toast.LENGTH_SHORT).show()
        }else{
            var now = ""+ LocalDate.now()
            val nowDate: Date = SimpleDateFormat("yyyy-MM-dd", Locale("ko","KR")).parse(now)
            now= SimpleDateFormat("yyyy. MM. dd.", Locale("ko","KR")).format(nowDate)
            G.bucketlistItems.add(
                BucketlistItem(
                    now,
                    binding.tvTitleMainDateEdit.text.toString(),
                    G.selectedCategoryImage
                ))
            finish()
        }
    }

}

