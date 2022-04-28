package com.grinleaf.onesightdiaryplanner

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.bumptech.glide.Glide
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.grinleaf.onesightdiaryplanner.databinding.ActivityMyInfoBinding

class MyInfoActivity : AppCompatActivity() {
    val binding by lazy { ActivityMyInfoBinding.inflate(layoutInflater) }
    lateinit var firebaseAuth: FirebaseAuth
    lateinit var firebaseFirestore: FirebaseFirestore
    lateinit var resultLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        resultLauncher= registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            if(it.resultCode!= RESULT_CANCELED){
                val uri= it.data?.data
                Glide.with(baseContext).load(uri).into(binding.ivUserprofileMyinfo)
            }
        }

        binding.ivUserprofileMyinfo.setOnClickListener{
            val intent= Intent().setAction(Intent.ACTION_PICK).setType("image/*")
            resultLauncher.launch(intent)
        }

        binding.btnLogoutMyinfo.setOnClickListener { clickLogout() }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    fun clickLogout(){
        G.userId= ""
        G.userEmail= ""
        G.userNickname= ""
        G.userPassword= ""

        val pref: SharedPreferences = getSharedPreferences("user", MODE_PRIVATE)
        val editor= pref.edit()
        editor.putString("userId",G.userId)
        editor.putString("userEmail",G.userEmail)
        editor.putString("userNickname",G.userNickname)
        editor.putString("userPw",G.userPassword)
        Log.i("aaa",G.userId+","+G.userEmail+","+G.userNickname+","+G.userPassword)
        editor.commit()

        firebaseAuth= FirebaseAuth.getInstance()
        firebaseAuth.signOut()
        val intent= Intent(this@MyInfoActivity, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}