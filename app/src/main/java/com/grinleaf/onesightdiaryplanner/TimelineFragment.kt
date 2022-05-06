package com.grinleaf.onesightdiaryplanner

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.drawable.toDrawable
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.grinleaf.onesightdiaryplanner.databinding.FragmentTimelineBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TimelineFragment:Fragment() {
    val binding by lazy { FragmentTimelineBinding.inflate(layoutInflater) }
    val adapter by lazy { TimelineAdapter(requireContext(),G.timelineItems) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        var dailySize= G.dailyNoteItems.size
        var checkSize= G.checklistItems.size
        var checkSubSize= G.checklistSubItems.size
        var lifeSize= G.lifecycleItems.size

        //DB에 저장된 데이터 로드하기
        val retrofit= RetrofitHelper.getRetrofitInstance()
        val retrofitService= retrofit.create(RetrofitService::class.java)
        val call= retrofitService.getTimelineDownload()
        call.enqueue(object : Callback<ArrayList<TimelineItem>> {
            override fun onResponse(
                call: Call<ArrayList<TimelineItem>>,
                response: Response<ArrayList<TimelineItem>>
            ) {
                val list= response.body()
                if (list != null) {
//                    timelineCallback(list)
                }
            }
            override fun onFailure(call: Call<ArrayList<TimelineItem>>, t: Throwable) {

            }

        })
        //G.timelineItems 에 화면에 띄울 데이터 add 하기
//        if(dailySize!=0|| checkSize!=0||checkSubSize!=0||lifeSize!=0) {
//            G.timelineItems.add(
//                TimelineItem(
//                    ifnullData(dailySize, G.dailyNoteItems.get(dailySize - 1).content),
//                    ifnullData(dailySize, G.dailyNoteItems.get(dailySize - 1).day),
//                    ifnullData(dailySize, G.dailyNoteItems.get(dailySize - 1).categoryImage),
//                    ifnullData(dailySize, G.dailyNoteItems.get(dailySize - 1).detailContent),
//                    ifnullData(dailySize, G.dailyNoteItems.get(dailySize - 1).dayImage),
//
//                    ifnullData(checkSize, G.checklistItems.get(checkSize - 1).content),
//                    ifnullData(checkSize, G.checklistItems.get(checkSize - 1).detailContent),
//                    ifnullData(checkSize, G.checklistItems.get(checkSize - 1).categoryImage),
//                    "",
//
//                    ifnullData(lifeSize, G.lifecycleItems.get(lifeSize - 1).content),
//                    ifnullData(lifeSize, G.lifecycleItems.get(lifeSize - 1).day),
//                    ifnullData(lifeSize, G.lifecycleItems.get(lifeSize - 1).endDay),
//                    ifnullData(lifeSize, G.lifecycleItems.get(lifeSize - 1).repeatCycle),
//                    ifnullData(lifeSize, G.lifecycleItems.get(lifeSize - 1).categoryImage)
//                )
//            )
//        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onResume() {
        super.onResume()
        Log.i("aaa","타임라인아이템 사이즈: "+G.timelineItems.size)

        adapter.notifyDataSetChanged()
    }

    //timelineItem call 객체 콜백처리 함수
    @SuppressLint("NotifyDataSetChanged")
//    fun timelineCallback(list:ArrayList<TimelineItem>){
//        lateinit var noDailyNote:String
//        lateinit var emailDailyNote:String
//        lateinit var titleDailyNote:String
//        lateinit var dayDailyNote:String
//        lateinit var categoryDailyNote:String
//        lateinit var attachImageDailyNote:String
//        lateinit var contentDailyNote:String
//
//        lateinit var noChecklist:String
//        lateinit var emailChecklist:String
//        lateinit var titleCheckList:String
//        lateinit var dayChecklist:String
//        lateinit var categoryCheckList:String
//        lateinit var contentCheckList:String
//        lateinit var subContentCheckList:String
//
//        lateinit var noLifecycle:String
//        lateinit var emailLifecycle:String
//        lateinit var titleLifecycle:String
//        lateinit var startDayLifecycle:String
//        lateinit var endDayLifecycle:String
//        lateinit var repeatCycle:String
//        lateinit var categoryLifecycle:String
//        lateinit var exportOther:String
//
//        lateinit var noBucketlist:String
//        lateinit var emailBucketlist:String
//        lateinit var titleBucketlist:String
//        lateinit var dayBucketlist:String
//        lateinit var categoryBucketlist:String
//        lateinit var contentBucketlist:String
//        //for 문을 각 데이터베이스 레코드 수만큼 차례로 돌리기.
//        //list.indices == 병합한 array 의 사이즈
//        for(i in list.indices){
//            noDailyNote= list.get(i).noDailyNote
//            emailDailyNote= list.get(i).emailDailyNote
//            titleDailyNote= list.get(i).titleDailyNote
//            dayDailyNote= list.get(i).dayDailyNote
//            categoryDailyNote= list.get(i).categoryDailyNote
//            attachImageDailyNote= list.get(i).attachImageDailyNote
//            contentDailyNote= list.get(i).contentDailyNote
//
//            noChecklist= list.get(i).noChecklist
//            emailChecklist= list.get(i).emailChecklist
//            titleCheckList= list.get(i).titleCheckList
//            dayChecklist= list.get(i).dayChecklist
//            categoryCheckList= list.get(i).categoryCheckList
//            contentCheckList= list.get(i).contentCheckList
//            subContentCheckList= list.get(i).subContentCheckList
//
//            noLifecycle= list.get(i).noLifecycle
//            emailLifecycle= list.get(i).emailLifecycle
//            titleLifecycle= list.get(i).titleLifecycle
//            startDayLifecycle= list.get(i).startDayLifecycle
//            endDayLifecycle= list.get(i).endDayLifecycle
//            repeatCycle= list.get(i).repeatCycle
//            categoryLifecycle= list.get(i).categoryLifecycle
//            exportOther= list.get(i).exportOther
//
//            noBucketlist= list.get(i).noBucketlist
//            emailBucketlist= list.get(i).emailBucketlist
//            titleBucketlist= list.get(i).titleBucketlist
//            dayBucketlist= list.get(i).dayBucketlist
//            categoryBucketlist= list.get(i).categoryBucketlist
//            contentBucketlist= list.get(i).contentBucketlist
//        }
//        G.timelineItems.add(
//            TimelineItem(
//                noDailyNote,emailDailyNote,titleDailyNote,dayDailyNote,categoryDailyNote,attachImageDailyNote,contentDailyNote,
//                noChecklist,emailChecklist,titleCheckList,dayChecklist,categoryCheckList,contentCheckList,subContentCheckList,
//                noLifecycle,emailLifecycle,titleLifecycle,startDayLifecycle,endDayLifecycle,repeatCycle,categoryLifecycle,exportOther,
//                noBucketlist,emailBucketlist,titleBucketlist,dayBucketlist,categoryBucketlist,contentBucketlist
//            ))
//        binding.recyclerTimeline.adapter= adapter
//        adapter.notifyDataSetChanged()
//    }

    //데이터가 입력되지 않았으면 빈문자
    private fun ifnullData(con:Int, data:String):String{
        if(con==0) return ""
        else return data
    }
}