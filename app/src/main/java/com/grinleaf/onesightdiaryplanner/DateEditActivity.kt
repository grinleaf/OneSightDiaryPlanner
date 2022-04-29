package com.grinleaf.onesightdiaryplanner

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.grinleaf.onesightdiaryplanner.databinding.ActivityDateEditBinding

class DateEditActivity:AppCompatActivity() {
    val binding by lazy { ActivityDateEditBinding.inflate(layoutInflater) }
    val fragments: MutableList<Fragment> by lazy { mutableListOf() }
    lateinit var resultLauncher:ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        resultLauncher= registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            val selectedImage= it.data?.data
            binding.ivCategoryMainDateEdit.setImageResource(R.drawable.tutorial_sample04)   //수정요 : 카테고리 액티비티에서 선택된 이미지를 가져와 설정하기
        }

        fragments.add(DateEditDailyNoteFragment())
        fragments.add(DateEditCheckListFragment())
        fragments.add(DateEditLifecycleFragment())
        fragments.add(DateEditBucketListFragment())

        supportFragmentManager.beginTransaction().add(R.id.date_edit_container,fragments[0]).commit()

        val spinnerItems= resources.getStringArray(R.array.spinner_array)
        val adapter= ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,spinnerItems)
        binding.spinnerDateEdit.adapter= adapter
        binding.spinnerDateEdit.onItemSelectedListener= object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                supportFragmentManager.fragments.forEach{
                    supportFragmentManager.beginTransaction().hide(it).commit()
                }
                var tran= supportFragmentManager.beginTransaction()
                when(p2){
                    0 -> {
                        tran.show(fragments[p2])
                        //일정등록 버튼 클릭 --> 입력된 데이터를 해당 프래그먼트로 전달 --> MainActivity 로 화면 전환
                        val dailylNoteFragment= DailyNoteFragment()
                        binding.btnRegistDateedit.setOnClickListener { clickRegistDailyNoteDate(dailylNoteFragment) }
                    }
                    1 -> {
                        if(!supportFragmentManager.fragments.contains(fragments[p2])) tran.add(R.id.date_edit_container,fragments[p2])
                        tran.show(fragments[p2])
                        binding.btnRegistDateedit.setOnClickListener {
                            Toast.makeText(baseContext,"checklist",Toast.LENGTH_SHORT).show()
                        }
                    }
                    2 -> {
                        if(!supportFragmentManager.fragments.contains(fragments[p2])) tran.add(R.id.date_edit_container,fragments[p2])
                        tran.show(fragments[p2])
                        binding.btnRegistDateedit.setOnClickListener {
                            Toast.makeText(baseContext,"lifecycle",Toast.LENGTH_SHORT).show()
                        }
                    }
                    3 -> {
                        if(!supportFragmentManager.fragments.contains(fragments[p2])) tran.add(R.id.date_edit_container,fragments[p2])
                        tran.show(fragments[p2])
                        binding.btnRegistDateedit.setOnClickListener {
                            Toast.makeText(baseContext,"bucketlist",Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                tran.commit()
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }
        binding.ivCategoryMainDateEdit.setOnClickListener {
            val intent= Intent(this@DateEditActivity, CategoryActivity::class.java)
            resultLauncher.launch(intent)
        }

    }

    //dailyNoteFragment 로 데이터 전달하는 함수
    fun clickRegistDailyNoteDate(fragment:Fragment){
        val bundle= Bundle()   //파라미터 : 전달할 값의 개수
        bundle.putString("dateTitle",binding.tvTitleMainDateEdit.text.toString())
        bundle.putInt("categoryImage",binding.ivCategoryMainDateEdit.id)
        bundle.putString("todayAuto",binding.dateEditContainer.findViewById<TextView>(R.id.tv_today_auto_dailynote_date_edit).text.toString())
        bundle.putString("attachImage",binding.dateEditContainer.findViewById<ImageView>(R.id.iv_attach_image_dailynote_date_edit).drawable.toString())
        bundle.putString("detailContent",binding.dateEditContainer.findViewById<TextView>(R.id.et_content_detail_dailynote_date_edit).text.toString())
        val intent= intent.putExtra("dailyDatas", bundle)
        setResult(RESULT_OK,intent)

        Log.i("aaa",bundle.toString()+"일정등록버튼")
        Toast.makeText(this, "일정이 등록되었습니다.", Toast.LENGTH_SHORT).show()

        finish()

        //mainAc 의 resultLauncher로 intent 결과를 받아가야 MainAc 이 새로 안만들어짐!

//        val intent= Intent(this@DateEditActivity, MainActivity::class.java)
//        intent.putExtra("dailynoteBundle",bundle)
//        startActivity(intent)
//        finish()
    }

}