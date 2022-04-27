package com.grinleaf.onesightdiaryplanner

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.marginLeft
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.grinleaf.onesightdiaryplanner.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    val binding by lazy { ActivityLoginBinding.inflate(layoutInflater) }
    lateinit var firebaseAuth:FirebaseAuth
    lateinit var resultLauncherGoogle:ActivityResultLauncher<Intent>
    var userNickname= ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        firebaseAuth= FirebaseAuth.getInstance()
        //구글로그인 런처
        resultLauncherGoogle= registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            val intent = it.data
            val task = GoogleSignIn.getSignedInAccountFromIntent(intent)
            val account = task.result
            val userEmail = account.email
            val userId = account.id
            val userPw = ""  //구글 로그인은 패스워드 지원 x
            val userNicknameEdit = EditText(this)
            val userInfo = HashMap<String, Any>()
            AlertDialog.Builder(this@LoginActivity)
                .setTitle("사용하실 닉네임을 입력해주세요.\n\n")
                .setView(userNicknameEdit)
                .setPositiveButton("완료", DialogInterface.OnClickListener { dialogInterface, i ->
                    userNickname = userNicknameEdit.text.toString()
                    userInfo.put("userId", userId!!)
                    userInfo.put("userPw", userPw)
                    userInfo.put("userNickname", userNickname)
                    userInfo.put("userEmail", userEmail!!)
                    val firestore = FirebaseFirestore.getInstance()
                    firestore.collection("user").document(userId!!).set(userInfo)
                    val intent= Intent(this@LoginActivity,MainActivity::class.java)
                    startActivity(intent)
                }).show()
            binding.loginTest.text = "$userEmail\n$userId"
        }

        //로그인 버튼
        binding.btnLoginSignin.setOnClickListener { clickLogin() }
        binding.btnLoginDebug.setOnClickListener {
            val intent= Intent(this@LoginActivity,MainActivity::class.java)
            startActivity(intent)
        }
        //앱이 처음이신가요? Text 클릭 시
        binding.tvLoginToSignup.setOnClickListener{
            val intent= Intent(this@LoginActivity,SignUpActivity::class.java)
            startActivity(intent)
        }
        binding.btnLoginNaver.setOnClickListener { clickNaverLogin() }
        binding.btnLoginGoogle.setOnClickListener { clickGoogleLogin() }
        binding.btnLoginKakao.setOnClickListener { clickKakaoLogin() }
    }

//    override fun onStart() {
//        super.onStart()
//        val currentUser= firebaseAuth.currentUser
//        if(currentUser!=null) reload()  //활동 초기화 - 비밀번호 기반 로그인
//    }

    fun clickLogin(){
        val inputId= binding.etLoginId.text.toString()
        val inputPw= binding.etLoginPw.text.toString()
        firebaseAuth.signInWithEmailAndPassword(inputId,inputPw)
            .addOnCompleteListener(this){
                    task-> if(task.isSuccessful){
//                val user= firebaseAuth.currentUser.id
                val intent= Intent(this@LoginActivity,MainActivity::class.java)
                startActivity(intent)
                finish()
                Toast.makeText(this, "로그인 성공!", Toast.LENGTH_SHORT).show()
                Toast.makeText(this, "$inputId 님, 환영합니다.", Toast.LENGTH_SHORT).show()
            }else{
                AlertDialog.Builder(this)
                    .setTitle("아이디와 비밀번호를 다시 확인해주세요")
                    .setPositiveButton("확인",DialogInterface.OnClickListener { dialogInterface, i -> {} })
            }
            }
    }

    fun clickGoogleLogin() {
        val signInOptions= GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(resources.getString(R.string.server_client_id))
            .requestEmail()
            .requestId()
            .build()

        val intent= GoogleSignIn.getClient(this, signInOptions).signInIntent
        resultLauncherGoogle.launch(intent)
    }

    fun clickNaverLogin(){
        Toast.makeText(this, "네이버로 로그인", Toast.LENGTH_SHORT).show()
    }

    fun clickKakaoLogin(){
        Toast.makeText(this, "카카오로 로그인", Toast.LENGTH_SHORT).show()
    }
}