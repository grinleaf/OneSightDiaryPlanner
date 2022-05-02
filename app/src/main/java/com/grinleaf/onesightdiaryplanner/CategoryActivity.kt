package com.grinleaf.onesightdiaryplanner

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.grinleaf.onesightdiaryplanner.databinding.ActivityCategoryBinding
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import java.nio.ByteBuffer
import java.util.*


class CategoryActivity : AppCompatActivity() {
    val binding by lazy { ActivityCategoryBinding.inflate(layoutInflater) }
    val categoryImages= mutableListOf<Int>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        categoryImages.add(R.drawable.ic_car)
        categoryImages.add(R.drawable.ic_friends)
        categoryImages.add(R.drawable.ic_dog_02)
        categoryImages.add(R.drawable.ic_flower)
        categoryImages.add(R.drawable.ic_leaf)
        categoryImages.add(R.drawable.ic_bff)
        categoryImages.add(R.drawable.ic_award_crown_02)
        categoryImages.add(R.drawable.ic_car)
        categoryImages.add(R.drawable.ic_friends)
        categoryImages.add(R.drawable.ic_dog_02)
        categoryImages.add(R.drawable.ic_flower)
        categoryImages.add(R.drawable.ic_leaf)
        categoryImages.add(R.drawable.ic_bff)
        categoryImages.add(R.drawable.ic_award_crown_02)

        saveImage()
    }

    fun saveImage(){

        binding.ivInterestCatecory01.setOnClickListener { clickSomeCategoryImage(binding.ivInterestCatecory01,categoryImages[0]) }
        binding.ivInterestCatecory02.setOnClickListener { clickSomeCategoryImage(binding.ivInterestCatecory02,categoryImages[1]) }
        binding.ivInterestCatecory03.setOnClickListener { clickSomeCategoryImage(binding.ivInterestCatecory03,categoryImages[2]) }
        binding.ivInterestCatecory04.setOnClickListener { clickSomeCategoryImage(binding.ivInterestCatecory04,categoryImages[3]) }
        binding.ivInterestCatecory05.setOnClickListener { clickSomeCategoryImage(binding.ivInterestCatecory05,categoryImages[4]) }
        binding.ivInterestCatecory06.setOnClickListener { clickSomeCategoryImage(binding.ivInterestCatecory06,categoryImages[5]) }
        binding.ivInterestCatecory07.setOnClickListener { clickSomeCategoryImage(binding.ivInterestCatecory07,categoryImages[6]) }
        binding.ivAchieveCatecory01.setOnClickListener { clickSomeCategoryImage(binding.ivAchieveCatecory01,categoryImages[7]) }
        binding.ivAchieveCatecory02.setOnClickListener { clickSomeCategoryImage(binding.ivAchieveCatecory02,categoryImages[8]) }
        binding.ivAchieveCatecory03.setOnClickListener { clickSomeCategoryImage(binding.ivAchieveCatecory03,categoryImages[9]) }
        binding.ivAchieveCatecory04.setOnClickListener { clickSomeCategoryImage(binding.ivAchieveCatecory04,categoryImages[10]) }
        binding.ivAchieveCatecory05.setOnClickListener { clickSomeCategoryImage(binding.ivAchieveCatecory05,categoryImages[11]) }
        binding.ivAchieveCatecory06.setOnClickListener { clickSomeCategoryImage(binding.ivAchieveCatecory06,categoryImages[12]) }
        binding.ivAchieveCatecory07.setOnClickListener { clickSomeCategoryImage(binding.ivAchieveCatecory07,categoryImages[13]) }

    }
//
    //특정 카테고리 이미지 클릭 시 해당 이미지(drawable)를 저장
    fun clickSomeCategoryImage(iv:ImageView,d:Int){
//        val bitmap01= drawableToBitmap(iv.drawable)
//        val draw= resources.getDrawable(d,null)
//        val toBitmap= drawableToBitmap(draw)
        G.selectedCategoryImage= d

        val intent= intent
        intent.putExtra("selectedCategoryImage",d)
        finish()
    }

//    //이미지비교 (1) : 이미지 --> 비트맵 변환 메소드
//    fun drawableToBitmap(drawable: Drawable): Bitmap? {
//        if (drawable is BitmapDrawable) {
//            return drawable.bitmap
//        }
//        val bitmap = Bitmap.createBitmap(
//            drawable.intrinsicWidth,
//            drawable.intrinsicHeight,
//            Bitmap.Config.ARGB_8888
//        )
//        val canvas = Canvas(bitmap)
//        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight())
//        drawable.draw(canvas)
//        return bitmap
//    }
//
//    //이미지비교 (2) : 비트맵 비교 메소드
//    private fun sameAs(bitmap1: Bitmap, bitmap2: Bitmap): Boolean {
//        val buffer1: ByteBuffer = ByteBuffer.allocate(bitmap1.height * bitmap1.rowBytes)
//        bitmap1.copyPixelsToBuffer(buffer1)
//        val buffer2: ByteBuffer = ByteBuffer.allocate(bitmap2.height * bitmap2.rowBytes)
//        bitmap2.copyPixelsToBuffer(buffer2)
//        return Arrays.equals(buffer1.array(), buffer2.array())
//    }
//
//    //비트맵 --> drawable 로 변환
//    @Throws(IOException::class)
//    private fun drawableFromUrl(url: String): Drawable? {
//        val x: Bitmap
//        val connection: HttpURLConnection = URL(url).openConnection() as HttpURLConnection
//        connection.connect()
//        val input: InputStream = connection.getInputStream()
//        x = BitmapFactory.decodeStream(input)
//        return BitmapDrawable(x)
//    }
}