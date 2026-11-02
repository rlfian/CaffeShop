package com.example.caffeshop.ui.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.caffeshop.R
import com.example.caffeshop.data.repository.UserManager
import com.example.caffeshop.databinding.ActivityLoginBinding
import com.example.caffeshop.ui.home.HomeActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBackLogin.setOnClickListener {
            finish()
        }

        binding.btnLoginSubmit.setOnClickListener {
            val username = binding.etUsername.text?.toString()?.trim().orEmpty()
            val password = binding.etPassword.text?.toString()?.trim().orEmpty()

            if (username.isEmpty()) {
                binding.tilUsername.error = "Username tidak boleh kosong"
                return@setOnClickListener
            } else {
                binding.tilUsername.error = null
            }

            if (password.isEmpty()) {
                binding.tilPassword.error = "Password tidak boleh kosong"
                return@setOnClickListener
            } else {
                binding.tilPassword.error = null
            }

            // Save session (if user hasn't set full name, use username as display name)
            val userManager = UserManager.getInstance(this)
            val currentFullName = if (userManager.getFullName() != "Nama") {
                userManager.getFullName()
            } else {
                username.replaceFirstChar { it.uppercase() }
            }
            userManager.saveUser(currentFullName, username)

            Toast.makeText(this, "Login berhasil. Selamat datang!", Toast.LENGTH_SHORT).show()

            val intent = Intent(this, HomeActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            startActivity(intent)
            finish()
        }

        binding.tvToRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
