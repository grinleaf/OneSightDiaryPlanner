package com.grinleaf.onesightdiaryplanner

import android.app.Dialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.grinleaf.onesightdiaryplanner.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {
    val binding by lazy { ActivitySignUpBinding.inflate(layoutInflater) }
    val firebaseAuth by lazy { FirebaseAuth.getInstance() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnSignupIdCheck.setOnClickListener { clickIdCheck() }
        binding.btnSignupRegist.setOnClickListener { clickSignupRegist() }
        binding.backpressSignup.setOnClickListener{
           AlertDialog.Builder(this)
                .setTitle("회원가입을 취소하시겠습니까?")
                .setPositiveButton("확인",DialogInterface.OnClickListener { dialogInterface, i -> finish() })
                .setNegativeButton("취소", DialogInterface.OnClickListener { dialogInterface, i -> }).show()
        }

    }

    override fun onBackPressed() {
        AlertDialog.Builder(this)
            .setTitle("회원가입을 취소하시겠습니까?")
            .setPositiveButton("확인",DialogInterface.OnClickListener { dialogInterface, i -> finish() })
            .setNegativeButton("취소", DialogInterface.OnClickListener { dialogInterface, i -> }).show()
    }

    //아이디 중복체크
    fun clickIdCheck(){

    }

    //이메일 인증 : 예제에서는 인증확인 하자마자 회원등록이 됨
    fun clickEmailAuth(){
        val inputEmail= binding.etSignupEmail.text.toString()
        val inputPw= binding.etSignupPw.text.toString()
        val user= firebaseAuth.currentUser
//        user.sendEmailVerification().
    }

    //회원등록 버튼 - firebaseAuth DB에 업로드
    fun clickSignupRegist(){
        var inputId= binding.etSignupId.text.toString()
        var inputPw= binding.etSignupPw.text.toString()
        var inputNickname= binding.etSignupNickname.text.toString()
        var inputEmail= binding.etSignupEmail.text.toString()

        val userInfo= HashMap<String, Any>()
        userInfo.put("userId",inputId)
        userInfo.put("userPw",inputPw)
        userInfo.put("userNickname",inputNickname)
        userInfo.put("userEmail",inputEmail)

        val firestore= FirebaseFirestore.getInstance()
        firestore.collection("user").document(inputEmail).set(userInfo)

        firebaseAuth.createUserWithEmailAndPassword(inputEmail,inputPw).addOnCompleteListener { task ->
            if(task.isSuccessful){
                firebaseAuth.currentUser!!.sendEmailVerification().addOnCompleteListener {
                    if(task.isSuccessful) Toast.makeText(this, "이메일을 확인하시고 인증하세요", Toast.LENGTH_SHORT).show()
                    else Toast.makeText(this, "인증되지 않은 사용자입니다.", Toast.LENGTH_SHORT).show()
                }
//                G.userId = inputId
//                G.userNickname = inputNickname
//                G.userEmail = inputEmail
//                G.userPassword = inputPw
                Log.i("aaa", G.userId + "," + G.userNickname + "," + G.userEmail)
                Toast.makeText(this, "회원가입이 완료되었습니다.", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this, "회원가입 실패!", Toast.LENGTH_SHORT).show()
            }
        }
        finish()
    }
}
