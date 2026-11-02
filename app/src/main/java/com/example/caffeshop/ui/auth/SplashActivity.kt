package com.example.caffeshop.ui.auth

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.caffeshop.data.repository.UserManager
import com.example.caffeshop.databinding.ActivitySplashBinding
import com.example.caffeshop.ui.home.HomeActivity

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnStartNow.setOnClickListener {
            val userManager = UserManager.getInstance(this)
            if (userManager.isLoggedIn()) {
                // If already logged in, user can go straight to Home
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                val intent = Intent(this, AuthGatewayActivity::class.java)
                startActivity(intent)
            }
        }
    }
}
