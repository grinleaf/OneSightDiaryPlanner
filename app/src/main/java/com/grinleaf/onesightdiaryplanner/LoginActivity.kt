package com.grinleaf.onesightdiaryplanner

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
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
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.grinleaf.onesightdiaryplanner.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    val binding by lazy { ActivityLoginBinding.inflate(layoutInflater) }
    lateinit var firebaseAuth:FirebaseAuth
    lateinit var firebaseFirestore: FirebaseFirestore
    lateinit var resultLauncherGoogle:ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //앱 재실행 시 로그인 이력 확인 (로그아웃을 누르지 않은 상태)
        val pref = getSharedPreferences("user", MODE_PRIVATE)
        G.userId = pref.getString("userId", "").toString()
        G.userEmail = pref.getString("userEmail", "").toString()
        G.userNickname = pref.getString("userNickname", "").toString()
        G.userPassword = pref.getString("userPw", "").toString()
        Log.i("aaa", G.userId + "," + G.userEmail + "," + G.userNickname + "," + G.userPassword)
        if (G.userEmail != "") {
            G.isLogin= true
            val intent = Intent(this@LoginActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
            Toast.makeText(this, "${G.userNickname} 님, 환영합니다.", Toast.LENGTH_SHORT).show()
        }
        firebaseAuth= FirebaseAuth.getInstance()
        //구글로그인 런처
        resultLauncherGoogle= registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            val intent = it.data
            val task = GoogleSignIn.getSignedInAccountFromIntent(intent)
            if(task.isSuccessful) {
                val account = task.result
                Log.i("aaa", "account.email : ${account.email}")
                G.userEmail = account.email.toString()
                G.userId = account.id!!
                G.userPassword = ""  //구글 로그인은 패스워드 지원 x
                if (G.userNickname == "") {
                    val userNicknameEdit = EditText(this)
                    val userInfo = HashMap<String, Any>()


                    AlertDialog.Builder(this@LoginActivity)
                        .setTitle("사용하실 닉네임을 입력해주세요.\n\n")
                        .setView(userNicknameEdit)
                        .setPositiveButton(
                            "완료",
                            DialogInterface.OnClickListener { dialogInterface, i ->
                                G.userNickname = userNicknameEdit.text.toString()
                                userInfo.put("userId", G.userId)
                                userInfo.put("userPw", G.userPassword)
                                userInfo.put("userNickname", G.userNickname)
                                userInfo.put("userEmail", G.userEmail)
                                G.isLogin = true
                                val firestore = FirebaseFirestore.getInstance()
                                firestore.collection("user").document(G.userEmail).set(userInfo)

                                val pref = getSharedPreferences("user", MODE_PRIVATE)
                                var editor = pref.edit()
                                editor.putString("userId", G.userId)
                                editor.putString("userEmail", G.userEmail)
                                editor.putString("userNickname", G.userNickname)
                                editor.putString("userPw", G.userPassword)
                                editor.commit()

                                val intent =
                                    Intent(this@LoginActivity, TutorialActivity::class.java)
                                startActivity(intent)
                                finish()
                                Toast.makeText(
                                    this,
                                    "${G.userNickname} 님, 환영합니다.",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                            }).show()
                }
            }
        }

        //로그인 버튼
        binding.btnLoginSignin.setOnClickListener { clickLogin() }

        //앱이 처음이신가요? Text 클릭 시 회원가입 화면 전환
        binding.tvLoginToSignup.setOnClickListener{
            val intent= Intent(this@LoginActivity,SignUpActivity::class.java)
            startActivity(intent)
        }
        binding.btnLoginNaver.setOnClickListener { clickNaverLogin() }
        binding.btnLoginGoogle.setOnClickListener { clickGoogleLogin() }
        binding.btnLoginKakao.setOnClickListener { clickKakaoLogin() }
    }

    fun clickLogin(){
        val inputEmail= binding.etLoginEmail.text.toString()
        val inputPw= binding.etLoginPw.text.toString()
        if(inputEmail.isEmpty()||inputPw.isEmpty())
            Toast.makeText(this, "이메일과 비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show()
        //Firestore 에서 검색하여 해당 userEmail 을 가져오기(G 클래스에 저장)
        firebaseFirestore= FirebaseFirestore.getInstance()
        val userRef= firebaseFirestore.collection("user")
        val task= userRef.get()
        task.addOnCompleteListener {
            if(it.isSuccessful){
                lateinit var userEmail:String
                lateinit var userPw:String
                lateinit var userNickname:String
                val snapshots= it.result
                for(snapshot in snapshots) {
                    val user = snapshot.data
                    userEmail = user.get("userEmail").toString()
                    userPw = user.get("userPw").toString()
                    userNickname = user.get("userNickname").toString()
                    if(userEmail.equals(inputEmail)&&userPw.equals(inputPw)) {
                        //DB에서 userEmail 을 파라미터로 넣어 Auth 에서 검증
                        firebaseAuth.signInWithEmailAndPassword(userEmail,inputPw)
                            .addOnCompleteListener(this){
                                    task-> if(task.isSuccessful){
                                G.userEmail= userEmail
                                G.userPassword= userPw
                                G.userNickname= userNickname
                                G.isLogin= true

                                val pref:SharedPreferences= getSharedPreferences("user", MODE_PRIVATE)
                                val editor= pref.edit()
                                editor.putString("userEmail",G.userEmail)
                                editor.putString("userNickname",G.userNickname)
                                editor.putString("userPw",G.userPassword)
                                Log.i("aaa",G.userEmail+","+G.userNickname+","+G.userPassword)
                                editor.commit()

                                val intent= Intent(this@LoginActivity,TutorialActivity::class.java)
                                startActivity(intent)
                                finish()
                                Toast.makeText(this, "$userNickname 님, 환영합니다.", Toast.LENGTH_SHORT).show()
                            }else{
                                Toast.makeText(this, "로그인 에러", Toast.LENGTH_SHORT).show()
                            }
                            }
                    }else{
                        AlertDialog.Builder(this)
                            .setTitle("이메일과 비밀번호를 다시 확인해주세요")
                            .setPositiveButton("확인",
                                DialogInterface.OnClickListener { dialogInterface, i ->  })
                    }
                }
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