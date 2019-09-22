package com.fiap.notepad.ui.splash

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.fiap.notepad.R
import com.fiap.notepad.ui.login.LoginActivity

class SplashActivity : AppCompatActivity() {

    private val SPLASHSCREEN_DURATION_TIME = 3000L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

       val preferences = getSharedPreferences("user_preferences", Context.MODE_PRIVATE)

       val isFirstOpen = preferences.getBoolean("open_first", true)

       if (isFirstOpen) {
         markAppAlreadyOpen(preferences)
         showSplash()
       } else {
           showLogin()
       }
    }

    private fun showSplash(){
        Handler().postDelayed({
            startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
            finish()
        }, SPLASHSCREEN_DURATION_TIME)
    }

    private fun markAppAlreadyOpen(preferences: SharedPreferences){
        val editor = preferences.edit()
        editor.putBoolean("open_first", false)
        editor.apply()
    }

    private fun showLogin(){
        startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
        finish()
    }
}
