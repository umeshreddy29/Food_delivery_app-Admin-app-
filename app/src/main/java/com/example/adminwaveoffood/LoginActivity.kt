package com.example.adminwaveoffood

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.adminwaveoffood.databinding.ActivityLoginBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private lateinit var email: String
    private lateinit var password: String
//    private lateinit var googleSignInClient: GoogleSignInClient

    private val binding: ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
//        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//            .requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build()

        // initialize firebase auth
        auth = Firebase.auth
        // initialize firebase database reference
        database = Firebase.database.reference
        // initialize Google Sign In
//        googleSignInClient = GoogleSignIn.getClient(this,googleSignInOptions)
//
//        binding.googleButton.setOnClickListener {
//            val signIntent = googleSignInClient.signInIntent
//            launcher.launch(signIntent)
//        }

        binding.toSignup.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.loginButton.setOnClickListener {

            email = binding.email.text.toString().trim()
            password = binding.password.text.toString().trim()

            if (email.isBlank() || password.isBlank()) {
                Toast.makeText(this, "Please fill all the details ", Toast.LENGTH_SHORT).show()
            } else {
                signInUserAccount(email, password)
            }

        }


    }

    private fun signInUserAccount(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val user = auth.currentUser
                updateUi(user)
            } else {
                Toast.makeText(this, "Please Register here", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, SignUpActivity::class.java))
                finish()
            }
        }
    }

    private fun updateUi(user: FirebaseUser?) {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

//    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
//        result ->
//        if(result.resultCode == Activity.RESULT_OK)
//        {
//            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
//            if (task.isSuccessful)
//            {
//                val account :GoogleSignInAccount = task.result
//                val credential = GoogleAuthProvider.getCredential(account.idToken,null)
//                auth.signInWithCredential(credential).addOnCompleteListener{
//                    authTask ->
//                    if(authTask.isSuccessful)
//                    {
//                        Toast.makeText(this,"Successfull sign in with google" , Toast.LENGTH_SHORT).show()
//                    }
//                    else
//                    {
//                        Toast.makeText(this,"Sign in failed" , Toast.LENGTH_SHORT).show()
//                    }
//                }
//
//            }
//        }
//    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser!=null)
        {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

}