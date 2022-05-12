package com.grinleaf.onesightdiaryplanner

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.grinleaf.onesightdiaryplanner.databinding.ActivityDateEditBinding
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

class DateEditActivity:AppCompatActivity() {
    val binding by lazy { ActivityDateEditBinding.inflate(layoutInflater) }
    val fragments: MutableList<Fragment> by lazy { mutableListOf() }
    lateinit var resultLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //선택한 카테고리 이미지를 가져오는 런처
        resultLauncher= registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            Glide.with(this).load(G.selectedCategoryImage).into(binding.ivCategoryMainDateEdit)
        }

        fragments.add(DateEditDailyNoteFragment())
        fragments.add(DateEditCheckListFragment())
        fragments.add(DateEditLifecycleFragment())
        fragments.add(DateEditBucketListFragment())

        supportFragmentManager.beginTransaction().add(R.id.date_edit_container,fragments[0]).commit()

        binding.ivCategoryMainDateEdit.setOnClickListener { clickCategory() }

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
    }

    private fun clickCategory(){
        val intent= Intent(this, CategoryActivity::class.java)
        resultLauncher.launch(intent)
    }

    //dailyNoteFragment 로 데이터 전달하는 함수
    fun clickRegistDailyNoteDate(){
        val email= G.userEmail
        val day= binding.dateEditContainer.findViewById<TextView>(R.id.tv_today_auto_dailynote_date_edit).text.toString()
        val content= binding.tvTitleMainDateEdit.text.toString()
        val categoryImage= G.selectedCategoryImage
        var dayImage= G.selectedattachImage //uri
        var dayImageUri= ""
        val detailContent= binding.dateEditContainer.findViewById<TextView>(R.id.et_content_detail_dailynote_date_edit).text.toString()

        if(binding.tvTitleMainDateEdit.text.isBlank()) {
            Toast.makeText(this, "일정의 제목을 입력하세요.", Toast.LENGTH_SHORT).show()
        }else if(dayImage==""){    //이미지 선택되지 않은 경우
            G.selectedattachImage= ""
            dailyNoteCallback(email,day,content,categoryImage,dayImageUri,detailContent)
        }else{  // 이미지 선택된 경우
            val retrofitDayImage= RetrofitHelper.getRetrofitInstance()
            val retrofitServiceDayImage= retrofitDayImage.create(RetrofitService::class.java)
            //filePart 구성 : 이미지 요소(uri)
            lateinit var filePart: MultipartBody.Part
            val file = File(dayImage)
            val requestBody = RequestBody.create(MediaType.parse("image/*"), file)
            filePart = MultipartBody.Part.createFormData("dayImage", file.name, requestBody)
            //dataPart 구성 : 나머지 문자열 데이터 요소
            val dataPart= HashMap<String,String>()
            dataPart.put("email",email)

            val callDayImage= retrofitServiceDayImage.uploadImage(dataPart,filePart)
            callDayImage.enqueue(object: Callback<String>{
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    val s= response.body()
                    Log.i("aaa", s.toString())
                    dayImageUri= "http://grinleaf.dothome.co.kr/OneSightDiaryPlanner/$s"
                    //콜백처리 메소드
                    dailyNoteCallback(email,day,content,categoryImage,dayImageUri,detailContent)
                }
                override fun onFailure(call: Call<String>, t: Throwable) {
                    Log.i("aaa",t.message.toString())
                }
            })
        }
    }

    fun clickRegistCheckListDate(){
        val email= G.userEmail
        val day= binding.dateEditContainer.findViewById<TextView>(R.id.tv_today_auto_checklist_date_edit).text.toString()
        val content= binding.tvTitleMainDateEdit.text.toString()
        val categoryImage= G.selectedCategoryImage
        //세부항목 내용 데이터 가져오는 코드 영역
        val detailContent= binding.dateEditContainer.findViewById<TextView>(R.id.et_content_detail_dailynote_date_edit).text.toString()
        val isChecked= false.toString()

        if(binding.tvTitleMainDateEdit.text.isBlank()&&binding.dateEditContainer.findViewById<TextView>(R.id.tv_today_auto_checklist_date_edit).text==null) {
            Toast.makeText(this, "일정의 제목을 입력하세요.", Toast.LENGTH_SHORT).show()
        }else{
            val retrofit = RetrofitHelper.getRetrofitInstance()
            val retrofitService = retrofit.create(RetrofitService::class.java)
            val call = retrofitService.getCheckListItem(email,day,content,categoryImage,detailContent,isChecked)
            call.enqueue(object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    val item = response.body()
                    G.selectedCategoryImage = ""
                }
                override fun onFailure(call: Call<String>, t: Throwable) {
                    Log.i("aaa", "error: ${t.message}")
                }
            })
            G.checklistItems.add(ChecklistItem("",email,day, content,categoryImage,detailContent,isChecked))
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
        val email= G.userEmail
        val startday= binding.dateEditContainer.findViewById<TextView>(R.id.tv_start_day_lifecycle_date_edit).text.toString()
        val endday=
            if(binding.dateEditContainer.findViewById<CheckBox>(R.id.checkbox_no_endday_lifecycle).isChecked) {
                binding.dateEditContainer.findViewById<TextView>(R.id.tv_end_day_lifecycle_date_edit).text
            }else{
                binding.dateEditContainer.findViewById<TextView>(R.id.tv_end_day_lifecycle_date_edit).text.toString()
            }.toString()
        val content= binding.tvTitleMainDateEdit.text.toString()
        val cycle= when(binding.dateEditContainer.findViewById<RadioGroup>(R.id.radio_group_lifecycle_date_edit).checkedRadioButtonId){
            R.id.radio_everyday_lifecycle_date_edit-> binding.dateEditContainer.findViewById<RadioButton>(R.id.radio_everyday_lifecycle_date_edit).text
            R.id.radio_everyweek_lifecycle_date_edit-> binding.dateEditContainer.findViewById<RadioButton>(R.id.radio_everyweek_lifecycle_date_edit).text
            R.id.radio_everymonth_lifecycle_date_edit-> binding.dateEditContainer.findViewById<RadioButton>(R.id.radio_everymonth_lifecycle_date_edit).text
            R.id.radio_everyyear_lifecycle_date_edit-> binding.dateEditContainer.findViewById<RadioButton>(R.id.radio_everyyear_lifecycle_date_edit).text
            else-> {}
        }.toString()
        val categoryImage= G.selectedCategoryImage
        val isBucket= when(binding.dateEditContainer.findViewById<CheckBox>(R.id.checkbox_checkbucket_lifecycle_date_edit).isChecked){
                true -> "true"
                false -> "false"
        }
        val isChecked= false.toString()

        if(binding.tvTitleMainDateEdit.text.isBlank()) {
            Toast.makeText(this, "일정의 제목을 입력하세요.", Toast.LENGTH_SHORT).show()
        }else if (binding.dateEditContainer.findViewById<TextView>(R.id.tv_start_day_lifecycle_date_edit).text=="시작일자") {
            Toast.makeText(this, "시작일자를 선택해주세요.", Toast.LENGTH_SHORT).show()
        }else if(binding.dateEditContainer.findViewById<TextView>(R.id.tv_end_day_lifecycle_date_edit).text=="종료일자" && !binding.dateEditContainer.findViewById<CheckBox>(R.id.checkbox_no_endday_lifecycle).isChecked){
            Toast.makeText(this, "종료일자를 선택해주세요.", Toast.LENGTH_SHORT).show()
        }else{
            val retrofit = RetrofitHelper.getRetrofitInstance()
            val retrofitService = retrofit.create(RetrofitService::class.java)
            val call = retrofitService.getLifecycleItem(email,content,startday,endday,cycle,categoryImage,isBucket,isChecked)
            call.enqueue(object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    val item = response.body()
                    //isBucket 이 true 일 때 버킷리스트에 데이터 추가하는 콜백처리 메소드 영역
                    Log.i("aaa","isBucket: "+isBucket)
                    G.selectedCategoryImage = ""
                }
                override fun onFailure(call: Call<String>, t: Throwable) {
                    Log.i("aaa", "error: ${t.message}")
                }
            })
            G.lifecycleItems.add(LifecycleItem("",email,startday, content,categoryImage,cycle,endday,isBucket,isChecked))
            finish()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun clickRegistBucketListDate(){
        val email= G.userEmail

        var now = ""+ LocalDate.now()
        val nowDate: Date = SimpleDateFormat("yyyy-MM-dd", Locale("ko","KR")).parse(now) as Date
        now= SimpleDateFormat("yyyy. MM. dd.", Locale("ko","KR")).format(nowDate)
        val day= now
        val content= binding.tvTitleMainDateEdit.text.toString()
        val categoryImage= G.selectedCategoryImage
        //세부항목 내용 데이터 가져오는 코드 영역
        val detailContent= binding.dateEditContainer.findViewById<TextView>(R.id.et_content_detail_dailynote_date_edit).text.toString() //수정

        if(binding.tvTitleMainDateEdit.text.isBlank()) {
            Toast.makeText(this, "일정의 제목을 입력하세요.", Toast.LENGTH_SHORT).show()
        }else{
            val retrofit = RetrofitHelper.getRetrofitInstance()
            val retrofitService = retrofit.create(RetrofitService::class.java)
            val call = retrofitService.getBucketListItem(email,content,day,categoryImage,detailContent)
            call.enqueue(object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    val item = response.body()
                    G.selectedCategoryImage = ""
                }
                override fun onFailure(call: Call<String>, t: Throwable) {
                    Log.i("aaa", "error: ${t.message}")
                }
            })
            G.bucketlistItems.add(BucketlistItem("",email,day,content, categoryImage,detailContent))
            //세부플랜 추가할 것
//            G.bucketlistItems.add(
//                BucketlistSubItem(binding.dateEditContainer.findViewById<RecyclerView>(R.id.recycler_bucketlist_dateedit_dateedit)
//                    .findViewHolderForAdapterPosition(0).toString())
//            )
            finish()
        }
    }

    fun dailyNoteCallback(email:String,day:String,content:String,categoryImage:String,dayImageUri:String,detailContent:String) {
        G.dailyNoteItems.add(DailyItem("",email,day, content, categoryImage, dayImageUri, detailContent))
        Log.i("aaa", dayImageUri)

        //입력받은 데이터를 서버에 업로드하는 코드
        val retrofit = RetrofitHelper.getRetrofitInstance()
        val retrofitService = retrofit.create(RetrofitService::class.java)
        val call = retrofitService.getDailyNoteItem(email,day,content,categoryImage,dayImageUri,detailContent)
        call.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                val item = response.body()
                G.selectedCategoryImage = ""
                G.selectedattachImage = ""
                finish()
            }
            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.i("aaa", "error: ${t.message}")
            }
        })
    }
}

