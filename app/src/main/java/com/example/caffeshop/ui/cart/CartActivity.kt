package com.example.caffeshop.ui.cart

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.caffeshop.R
import com.example.caffeshop.data.repository.CartManager
import com.example.caffeshop.databinding.ActivityCartBinding
import com.example.caffeshop.ui.adapter.CartAdapter
import com.example.caffeshop.ui.order.OrderActivity
import com.example.caffeshop.util.CurrencyHelper

class CartActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCartBinding
    private lateinit var cartAdapter: CartAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        setupListeners()
        updateCartUi()
    }

    private fun setupRecyclerView() {
        cartAdapter = CartAdapter(
            items = CartManager.getItems(),
            onQuantityChanged = { pos, newQty ->
                CartManager.updateQuantity(pos, newQty)
                updateCartUi()
            },
            onItemDeleted = { pos ->
                CartManager.removeItem(pos)
                updateCartUi()
                Toast.makeText(this, "Item dihapus dari keranjang", Toast.LENGTH_SHORT).show()
            }
        )

        binding.rvCartItems.apply {
            layoutManager = LinearLayoutManager(this@CartActivity)
            adapter = cartAdapter
        }
    }

    private fun setupListeners() {
        binding.btnBackCart.setOnClickListener {
            finish()
        }

        binding.btnClearCart.setOnClickListener {
            CartManager.clearCart()
            updateCartUi()
            Toast.makeText(this, "Semua item dihapus", Toast.LENGTH_SHORT).show()
        }

        binding.btnExploreFromEmpty.setOnClickListener {
            finish()
        }

        binding.btnApplyPromo.setOnClickListener {
            val code = binding.etPromoCode.text.toString().trim()
            if (CartManager.applyPromo(code)) {
                Toast.makeText(this, getString(R.string.promo_applied_success), Toast.LENGTH_SHORT).show()
                updateCartUi()
            } else {
                Toast.makeText(this, getString(R.string.promo_invalid), Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnCheckoutCart.setOnClickListener {
            if (CartManager.getItems().isEmpty()) {
                Toast.makeText(this, "Keranjang masih kosong!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val intent = Intent(this, OrderActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun updateCartUi() {
        val items = CartManager.getItems()
        cartAdapter.updateData(items.toList())

        if (items.isEmpty()) {
            binding.layoutCartEmpty.visibility = View.VISIBLE
            binding.rvCartItems.visibility = View.GONE
            binding.cardVoucher.visibility = View.GONE
            binding.cardCartSummary.visibility = View.GONE
            binding.cardBottomCheckout.visibility = View.GONE
            binding.btnClearCart.visibility = View.GONE
        } else {
            binding.layoutCartEmpty.visibility = View.GONE
            binding.rvCartItems.visibility = View.VISIBLE
            binding.cardVoucher.visibility = View.VISIBLE
            binding.cardCartSummary.visibility = View.VISIBLE
            binding.cardBottomCheckout.visibility = View.VISIBLE
            binding.btnClearCart.visibility = View.VISIBLE

            binding.tvCartSubtotal.text = CurrencyHelper.formatRupiah(CartManager.getSubtotal())
            binding.tvCartDelivery.text = if (CartManager.getDeliveryFee() == 0) "GRATIS" else CurrencyHelper.formatRupiah(CartManager.getDeliveryFee())

            val discount = CartManager.getDiscount()
            if (discount > 0) {
                binding.layoutDiscountRow.visibility = View.VISIBLE
                binding.tvCartDiscount.text = "- " + CurrencyHelper.formatRupiah(discount)
            } else {
                binding.layoutDiscountRow.visibility = View.GONE
            }

            binding.tvCartTotalPayment.text = CurrencyHelper.formatRupiah(CartManager.getTotalPayment())
        }
    }
}
