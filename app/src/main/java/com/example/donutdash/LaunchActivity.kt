package com.example.donutdash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.donutdash.ui.main.MainActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

class LaunchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // temporarily blocks UI thread to display splashscreen for 1 second after cold boot
        runBlocking {
            delay(1000L)
        }
        // starts intent to MainActivity
        startActivity(Intent(this, MainActivity::class.java))
        // closes this activity
        finish()
    }
}