package com.example.caffeshop.ui.auth

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.caffeshop.databinding.ActivityAuthGatewayBinding

class AuthGatewayActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthGatewayBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthGatewayBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Register button -> RegisterActivity (Screen 4)
        binding.btnRegisterGateway.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        // Login button -> LoginActivity (Screen 3)
        binding.btnLoginGateway.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}
