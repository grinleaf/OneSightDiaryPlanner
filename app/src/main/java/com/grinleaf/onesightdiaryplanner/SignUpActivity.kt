package com.grinleaf.onesightdiaryplanner

import android.app.Dialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
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
        //패스워드 확인문자 메소드
        checkedPwRe()
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

    fun checkedPwRe(){

    }

    //회원등록 버튼 - firebaseAuth DB에 업로드 --> 이게 email 인증 아래에 있어야함
    fun clickSignupRegist(){
        var inputPw= binding.etSignupPw.text.toString()
        var inputPwRe= binding.etSignupPwRe.text.toString()
        var inputNickname= binding.etSignupNickname.text.toString()
        var inputEmail= binding.etSignupEmail.text.toString()

        val userInfo= HashMap<String, Any>()
        userInfo.put("userPw",inputPw)
        userInfo.put("userNickname",inputNickname)
        userInfo.put("userEmail",inputEmail)

        val firestore= FirebaseFirestore.getInstance()
        firestore.collection("user").document(inputEmail).set(userInfo)

        firebaseAuth.createUserWithEmailAndPassword(inputEmail,inputPw).addOnCompleteListener { task ->
            if(task.isSuccessful){
                if(inputPw==inputPwRe) {
                    firebaseAuth.currentUser!!.sendEmailVerification().addOnCompleteListener {
                        if(task.isSuccessful) {
                            Toast.makeText(this, "계정이 생성되었습니다. 이메일 인증을 확인해주세요.", Toast.LENGTH_SHORT).show()
                            G.userNickname = inputNickname
                            G.userEmail = inputEmail
                            G.userPassword = inputPw
                            Log.i("aaa", G.userNickname + "," + G.userEmail)
                            finish()
                        }
                        else Toast.makeText(this, "인증되지 않은 사용자입니다.", Toast.LENGTH_SHORT).show()
                    }
                }else{
                    Toast.makeText(this,"패스워드와 패스워드 확인 문구가 일치하지 않습니다.",Toast.LENGTH_SHORT).show()
                    Log.i("aaa","비밀번호 불일치")
                }
            }else if(task.exception.toString() == "com.google.firebase.auth.FirebaseAuthUserCollisionException: The email address is already in use by another account."){
                Toast.makeText(this,"이미 사용중인 이메일입니다.",Toast.LENGTH_SHORT).show()
                Log.i("aaa","회원가입 실패!"+task.exception)
                firestore.collection("user").document(inputEmail).delete()
            }else if(task.exception.toString() == "com.google.firebase.auth.FirebaseAuthInvalidCredentialsException: The email address is badly formatted."){
                Toast.makeText(this,"이메일 형식이 바르지 않습니다.",Toast.LENGTH_SHORT).show()
                Log.i("aaa","회원가입 실패!"+task.exception)
                firestore.collection("user").document(inputEmail).delete()
            }else{
                Toast.makeText(this,"알 수 없는 오류",Toast.LENGTH_SHORT).show()
                Log.i("aaa","회원가입 실패!"+task.exception)
                firestore.collection("user").document(inputEmail).delete()
            }
        }
    }
}
