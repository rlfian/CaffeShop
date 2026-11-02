package com.example.caffeshop.ui.profile

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.caffeshop.R
import com.example.caffeshop.data.repository.UserManager
import com.example.caffeshop.databinding.ActivityProfileBinding
import com.example.caffeshop.ui.auth.SplashActivity
import com.example.caffeshop.ui.help.HelpActivity
import com.example.caffeshop.ui.home.HomeActivity
import com.example.caffeshop.ui.order.OrderActivity

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    private lateinit var userManager: UserManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userManager = UserManager.getInstance(this)

        displayUserData()
        setupSaveButton()
        setupExtraButtons()
        setupBottomNavigation()
    }

    override fun onResume() {
        super.onResume()
        displayUserData()
        binding.bottomNavigationViewProfile.selectedItemId = R.id.nav_profile
    }

    private fun displayUserData() {
        val currentName = userManager.getFullName()
        binding.tvProfileGreeting.text = getString(R.string.greeting_format, currentName)
        binding.tvCardMemberName.text = currentName
        binding.etProfileName.setText(currentName)
    }

    private fun setupSaveButton() {
        binding.btnSaveProfile.setOnClickListener {
            val newName = binding.etProfileName.text?.toString()?.trim().orEmpty()
            if (newName.length < 3) {
                binding.tilProfileName.error = getString(R.string.error_short_name)
                return@setOnClickListener
            }

            binding.tilProfileName.error = null
            userManager.updateFullName(newName)

            // Update local display immediately
            displayUserData()

            Toast.makeText(this, getString(R.string.profile_saved_success), Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupExtraButtons() {
        // Riwayat Pesanan
        binding.btnGoToHistory.setOnClickListener {
            val intent = Intent(this, OrderHistoryActivity::class.java)
            startActivity(intent)
        }

        // Bantuan & Info Kafe
        binding.btnGoToHelp.setOnClickListener {
            val intent = Intent(this, HelpActivity::class.java)
            startActivity(intent)
        }

        // Logout
        binding.btnLogout.setOnClickListener {
            userManager.logout()
            Toast.makeText(this, "Anda berhasil logout", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, SplashActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            startActivity(intent)
            finish()
        }
    }

    private fun setupBottomNavigation() {
        binding.bottomNavigationViewProfile.selectedItemId = R.id.nav_profile

        binding.bottomNavigationViewProfile.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    val intent = Intent(this, HomeActivity::class.java)
                    startActivity(intent)
                    finish()
                    true
                }
                R.id.nav_order -> {
                    val intent = Intent(this, OrderActivity::class.java)
                    startActivity(intent)
                    finish()
                    true
                }
                R.id.nav_profile -> true
                else -> false
            }
        }
    }
}
