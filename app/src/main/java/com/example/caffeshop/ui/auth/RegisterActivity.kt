package com.example.caffeshop.ui.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.caffeshop.R
import com.example.caffeshop.data.repository.UserManager
import com.example.caffeshop.databinding.ActivityRegisterBinding
import com.example.caffeshop.ui.home.HomeActivity

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBackRegister.setOnClickListener {
            finish()
        }

        binding.btnRegisterSubmit.setOnClickListener {
            val fullName = binding.etFullName.text?.toString()?.trim().orEmpty()
            val username = binding.etUsernameRegister.text?.toString()?.trim().orEmpty()
            val password = binding.etPasswordRegister.text?.toString()?.trim().orEmpty()

            if (fullName.length < 3) {
                binding.tilFullName.error = getString(R.string.error_short_name)
                return@setOnClickListener
            } else {
                binding.tilFullName.error = null
            }

            if (username.isEmpty()) {
                binding.tilUsernameRegister.error = "Username tidak boleh kosong"
                return@setOnClickListener
            } else {
                binding.tilUsernameRegister.error = null
            }

            if (password.length < 4) {
                binding.tilPasswordRegister.error = getString(R.string.error_short_password)
                return@setOnClickListener
            } else {
                binding.tilPasswordRegister.error = null
            }

            // Save user session
            val userManager = UserManager.getInstance(this)
            userManager.saveUser(fullName, username)

            Toast.makeText(this, "Pendaftaran berhasil! Selamat datang $fullName", Toast.LENGTH_SHORT).show()

            val intent = Intent(this, HomeActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            startActivity(intent)
            finish()
        }

        binding.tvToLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
