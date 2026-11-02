package com.example.caffeshop.ui.profile

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.caffeshop.data.repository.CartManager
import com.example.caffeshop.databinding.ActivityOrderHistoryBinding
import com.example.caffeshop.ui.adapter.OrderHistoryAdapter

class OrderHistoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOrderHistoryBinding
    private lateinit var adapter: OrderHistoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBackHistory.setOnClickListener {
            finish()
        }

        adapter = OrderHistoryAdapter(
            orders = CartManager.getOrderHistory(),
            onReorder = { order ->
                Toast.makeText(this, "Pesanan ${order.orderId} dipilih kembali!", Toast.LENGTH_SHORT).show()
                finish()
            }
        )

        binding.rvOrderHistory.apply {
            layoutManager = LinearLayoutManager(this@OrderHistoryActivity)
            this.adapter = this@OrderHistoryActivity.adapter
        }
    }
}
