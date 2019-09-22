package com.fiap.notepad.ui.signup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.fiap.notepad.R
import com.fiap.notepad.model.User
import com.fiap.notepad.ui.form.FormActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_sign_up.*


class SignupActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        mAuth = FirebaseAuth.getInstance()

        btCreate.setOnClickListener{
            val email:String = inputEmail.text.toString()
            val password:String = inputPassword.text.toString()
            val name:String = inputName.text.toString()

            if (email.isNullOrEmpty() || password.isNullOrEmpty() || name.isNullOrEmpty()) {
                Toast.makeText(this@SignupActivity, R.string.activity_sign_up_all_fields_are_required,
                    Toast.LENGTH_SHORT).show()
            } else {
                mAuth.createUserWithEmailAndPassword(
                    email, password
                ).addOnCompleteListener {
                    if (it.isSuccessful) {
                        saveInRealTimeDatabase()
                        goToHome()
                    } else {
                        Toast.makeText(
                            this@SignupActivity, it.exception?.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    private fun goToHome() {
        mAuth.signInWithEmailAndPassword(
            inputEmail.text.toString(),
            inputPassword.text.toString()
        ).addOnCompleteListener {
            if (it.isSuccessful) {
                val intent = Intent(this, FormActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(intent)
            } else {
                Toast.makeText(
                    this@SignupActivity, it.exception?.message,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun saveInRealTimeDatabase(){
        val user = User(inputName.text.toString(), inputEmail.text.toString())

        FirebaseDatabase.getInstance().getReference("Users")
        .child(FirebaseAuth.getInstance().currentUser!!.uid)
        .setValue(user)
        .addOnCompleteListener{
            if (it.isSuccessful){
                Toast.makeText(this, R.string.activity_sign_up_user_created,
                    Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, R.string.activity_sign_up_error_create_user,
                    Toast.LENGTH_SHORT).show()
            }
        }
    }
}
