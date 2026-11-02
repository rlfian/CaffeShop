package com.example.caffeshop.ui.help

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.caffeshop.databinding.ActivityHelpBinding

class HelpActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHelpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHelpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBackHelp.setOnClickListener {
            finish()
        }

        binding.btnContactWa.setOnClickListener {
            Toast.makeText(
                this,
                "Menghubungi Customer Care Caffe Shop (+62 812-3456-7890)...",
                Toast.LENGTH_LONG
            ).show()
        }
    }
}
