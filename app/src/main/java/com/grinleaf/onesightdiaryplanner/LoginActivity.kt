package com.grinleaf.onesightdiaryplanner

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.grinleaf.onesightdiaryplanner.databinding.ActivityLoginBinding
import kotlin.contracts.ContractBuilder
import kotlin.contracts.contract

class LoginActivity : AppCompatActivity() {

    val binding by lazy { ActivityLoginBinding.inflate(layoutInflater) }
    lateinit var firebaseAuth:FirebaseAuth
    lateinit var resultLauncher:ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        firebaseAuth= FirebaseAuth.getInstance()
        resultLauncher= registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            val intent= it.data
            val task= GoogleSignIn.getSignedInAccountFromIntent(intent)

            val account= task.result
            val userEmail= account.email
            val userId= account.id
            binding.loginTest.text= "$userEmail\n$userId"
        }

        binding.btnLoginDebug.setOnClickListener {
            val intent= Intent(this@LoginActivity,MainActivity::class.java)
            startActivity(intent)
        }

        binding.tvLoginToSignup.setOnClickListener{
            val intent= Intent(this@LoginActivity,SignUpActivity::class.java)
            startActivity(intent)
        }

        binding.btnLoginNaver.setOnClickListener { clickNaverLogin() }
        binding.btnLoginGoogle.setOnClickListener { clickGoogleLogin() }
        binding.btnLoginKakao.setOnClickListener { clickKakaoLogin() }
    }

    fun clickGoogleLogin() {
        val signInOptions= GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("893247241219-3dki19ccp30i8roubfqoid9b8pf0b7l9.apps.googleusercontent.com")
            .requestEmail()
            .requestId()
            .build()

        val intent= GoogleSignIn.getClient(this, signInOptions).signInIntent
        resultLauncher.launch(intent)
    }

    fun clickNaverLogin(){
        Toast.makeText(this, "네이버로 로그인", Toast.LENGTH_SHORT).show()
    }

    fun clickKakaoLogin(){
        Toast.makeText(this, "카카오로 로그인", Toast.LENGTH_SHORT).show()
    }
}