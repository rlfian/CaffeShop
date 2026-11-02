package com.example.caffeshop.ui.home

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.caffeshop.R
import com.example.caffeshop.data.model.FoodItem
import com.example.caffeshop.data.repository.CartManager
import com.example.caffeshop.databinding.ActivityDetailMenuBinding
import com.example.caffeshop.ui.order.OrderActivity
import com.example.caffeshop.util.CurrencyHelper

class DetailMenuActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailMenuBinding
    private var foodItem: FoodItem? = null
    private var quantity: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        @Suppress("DEPRECATION")
        foodItem = intent.getSerializableExtra(EXTRA_FOOD_ITEM) as? FoodItem
        if (foodItem == null) {
            Toast.makeText(this, "Data menu tidak ditemukan", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        displayData()
        setupListeners()
    }

    private fun displayData() {
        val item = foodItem ?: return
        binding.ivDetailImage.setImageResource(item.imageRes)
        binding.tvDetailName.text = item.name
        binding.tvDetailPrice.text = CurrencyHelper.formatRupiah(item.price)
        binding.tvDetailDesc.text = item.description
        binding.tvDetailRating.text = "⭐ ${item.rating} (${item.reviewCount})"

        if (item.isFavorite) {
            binding.ivDetailFavorite.alpha = 1.0f
        } else {
            binding.ivDetailFavorite.alpha = 0.35f
        }
    }

    private fun setupListeners() {
        binding.btnBackDetail.setOnClickListener {
            finish()
        }

        binding.ivDetailFavorite.setOnClickListener {
            val item = foodItem ?: return@setOnClickListener
            item.isFavorite = !item.isFavorite
            binding.ivDetailFavorite.alpha = if (item.isFavorite) 1.0f else 0.35f
            val msg = if (item.isFavorite) "Ditambahkan ke menu favorit!" else "Dihapus dari menu favorit"
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
        }

        binding.btnQtyMinus.setOnClickListener {
            if (quantity > 1) {
                quantity--
                binding.tvDetailQuantity.text = quantity.toString()
            }
        }

        binding.btnQtyPlus.setOnClickListener {
            quantity++
            binding.tvDetailQuantity.text = quantity.toString()
        }

        binding.btnAddToCart.setOnClickListener {
            addItemToCart()
            Toast.makeText(this, "${foodItem?.name} berhasil dimasukkan ke keranjang!", Toast.LENGTH_SHORT).show()
            finish()
        }

        binding.btnBuyNow.setOnClickListener {
            addItemToCart()
            val intent = Intent(this, OrderActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun addItemToCart() {
        val item = foodItem ?: return
        val temp = if (binding.rbIced.isChecked) "Iced" else "Hot"
        val sugar = if (binding.rbNormalSugar.isChecked) "Normal Sugar" else "Less Sugar"

        CartManager.addItem(
            foodItem = item,
            quantity = quantity,
            temperature = temp,
            sugarLevel = sugar,
            size = "Regular"
        )
    }

    companion object {
        const val EXTRA_FOOD_ITEM = "extra_food_item"
    }
}
