package com.example.jaloliddinhaykaligavertualsayohat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.jaloliddinhaykaligavertualsayohat.databinding.ActivitySplashScreenBinding

private lateinit var binding:ActivitySplashScreenBinding
class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivitySplashScreenBinding.inflate(layoutInflater)
        binding.animname.animate().translationY(0F).setDuration(2700).startDelay = 0
        binding.lottie.animate().translationX(0F).setDuration(2000).startDelay = 0
        binding.apply {
            val handler=Handler()
            handler.postDelayed({
                val intent= Intent(applicationContext,MainActivity::class.java)
                startActivity(intent)
            },3000)
        }
        setContentView(binding.root)
    }
}