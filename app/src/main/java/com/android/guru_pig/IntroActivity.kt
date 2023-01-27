package com.android.guru_pig

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.android.guru_pig.MainActivity

class IntroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        var handler = Handler()
        handler.postDelayed( {
            var intent = Intent( this, MainActivity::class.java)
            startActivity(intent)
        }, 3000)
    }

    override fun onPause() {
        super.onPause()
        finish()
    }
}