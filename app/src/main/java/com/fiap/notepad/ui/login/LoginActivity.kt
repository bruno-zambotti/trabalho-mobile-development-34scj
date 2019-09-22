package com.fiap.notepad.ui.login

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.fiap.notepad.R
import com.fiap.notepad.ui.form.FormActivity
import com.fiap.notepad.ui.signup.SignupActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mAuth = FirebaseAuth.getInstance()

        if(mAuth.currentUser != null){
            goToHome()
        }

        btLogin.setOnClickListener{
            val email:String = inputLoginEmail.text.toString()
            val password:String = inputLoginPassword.text.toString()

            if (email.isNullOrEmpty() || password.isNullOrEmpty()) {
                Toast.makeText(this@LoginActivity, R.string.activity_login_required_fields,
                    Toast.LENGTH_SHORT).show()
            } else {
                mAuth.signInWithEmailAndPassword(
                    email, password
                ).addOnCompleteListener {
                    if (it.isSuccessful){
                        goToHome()
                    } else {
                        Toast.makeText(this@LoginActivity, it.exception?.message,
                            Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        btSignup.setOnClickListener{
            startActivity(Intent(this, SignupActivity::class.java))
        }
    }

    private fun goToHome(){
        val intent = Intent(this, FormActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
        finish()
    }
}
