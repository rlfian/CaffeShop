package com.example.caffeshop.ui.order

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.caffeshop.R
import com.example.caffeshop.data.model.OrderModel
import com.example.caffeshop.data.repository.CartManager
import com.example.caffeshop.data.repository.DummyMenuRepository
import com.example.caffeshop.data.repository.UserManager
import com.example.caffeshop.databinding.ActivityOrderBinding
import com.example.caffeshop.util.CurrencyHelper
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class OrderActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOrderBinding
    private lateinit var userManager: UserManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userManager = UserManager.getInstance(this)

        setupPrefilledData()
        setupOrderSummary()
        setupButtons()
    }

    private fun setupPrefilledData() {
        val currentName = userManager.getFullName()
        if (currentName != "Nama") {
            binding.etOrderFullName.setText(currentName)
        }
    }

    private fun setupOrderSummary() {
        val cartItems = CartManager.getItems()
        if (cartItems.isEmpty()) {
            // If user opened Order screen directly without selecting menu, auto-select standard sample
            val defaultItem = DummyMenuRepository.getAllItems()[0] // Kopi Susu Gula Aren
            CartManager.addItem(defaultItem, quantity = 1)
        }

        val itemsText = CartManager.getItems().joinToString("\n") {
            "${it.quantity}x ${it.foodItem.name} (${it.temperature}, ${it.sugarLevel})"
        }
        binding.tvOrderItemsList.text = itemsText
        binding.tvOrderTotalAmount.text = CurrencyHelper.formatRupiah(CartManager.getTotalPayment())
    }

    private fun setupButtons() {
        binding.btnBackOrder.setOnClickListener {
            finish()
        }

        binding.btnOrderAndSend.setOnClickListener {
            val fullName = binding.etOrderFullName.text?.toString()?.trim().orEmpty()
            val address = binding.etOrderAddress.text?.toString()?.trim().orEmpty()
            val addressDetail = binding.etOrderAddressDetail.text?.toString()?.trim().orEmpty()

            if (fullName.isEmpty()) {
                binding.tilOrderFullName.error = "Nama penerima harus diisi"
                return@setOnClickListener
            } else {
                binding.tilOrderFullName.error = null
            }

            if (address.isEmpty()) {
                binding.tilOrderAddress.error = "Alamat pengiriman harus diisi"
                return@setOnClickListener
            } else {
                binding.tilOrderAddress.error = null
            }

            if (addressDetail.isEmpty()) {
                binding.tilOrderAddressDetail.error = "Patokan detail harus diisi"
                return@setOnClickListener
            } else {
                binding.tilOrderAddressDetail.error = null
            }

            // Determine payment method
            val paymentMethod = when (binding.rgPaymentMethod.checkedRadioButtonId) {
                R.id.rbCod -> "Tunai / COD"
                R.id.rbBank -> "Transfer Bank (BCA)"
                else -> "QRIS / GoPay"
            }

            // Create Order record
            val nowStr = SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.forLanguageTag("id-ID")).format(Date())
            val orderModel = OrderModel(
                orderId = "#CS-" + (10000..99999).random(),
                itemsSummary = CartManager.getItems().joinToString(", ") { "${it.quantity}x ${it.foodItem.name}" },
                recipientName = fullName,
                address = address,
                addressDetail = addressDetail,
                paymentMethod = paymentMethod,
                totalAmount = CartManager.getTotalPayment(),
                orderDate = nowStr,
                status = "Sedang Diantar"
            )
            CartManager.addOrderToHistory(orderModel)
            CartManager.clearCart()

            // Navigate to Confirmation (Screen 8)
            val intent = Intent(this, ConfirmationActivity::class.java).apply {
                putExtra(ConfirmationActivity.EXTRA_RECIPIENT_NAME, fullName)
            }
            startActivity(intent)
            finish()
        }
    }
}
