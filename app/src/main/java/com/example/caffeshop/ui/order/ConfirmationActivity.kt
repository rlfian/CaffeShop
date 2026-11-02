package com.example.caffeshop.ui.order

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.caffeshop.R
import com.example.caffeshop.data.repository.UserManager
import com.example.caffeshop.databinding.ActivityConfirmationBinding
import com.example.caffeshop.ui.home.HomeActivity

class ConfirmationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityConfirmationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConfirmationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val recipientName = intent.getStringExtra(EXTRA_RECIPIENT_NAME)
            ?: UserManager.getInstance(this).getFullName()

        // Greeting matches wireframe: "Halo Nama,"
        binding.tvConfirmGreeting.text = getString(R.string.greeting_format, recipientName)

        // Button "Oke" from wireframe returns to Home cleanly
        binding.btnConfirmOke.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            }
            startActivity(intent)
            finish()
        }

        // Extended feature: Track Courier
        binding.btnTrackOrder.setOnClickListener {
            val intent = Intent(this, OrderTrackingActivity::class.java)
            startActivity(intent)
        }
    }

    companion object {
        const val EXTRA_RECIPIENT_NAME = "extra_recipient_name"
    }
}
