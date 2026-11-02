package com.example.caffeshop.ui.home

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.caffeshop.R
import com.example.caffeshop.data.model.FoodItem
import com.example.caffeshop.data.repository.CartManager
import com.example.caffeshop.data.repository.DummyMenuRepository
import com.example.caffeshop.data.repository.UserManager
import com.example.caffeshop.databinding.ActivityHomeBinding
import com.example.caffeshop.ui.adapter.MenuAdapter
import com.example.caffeshop.ui.cart.CartActivity
import com.example.caffeshop.ui.order.OrderActivity
import com.example.caffeshop.ui.profile.ProfileActivity

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var menuAdapter: MenuAdapter
    private var currentCategory: String = "Semua"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupGreeting()
        setupRecyclerView()
        setupSearchAndFilters()
        setupCartButton()
        setupBottomNavigation()
    }

    override fun onResume() {
        super.onResume()
        setupGreeting()
        updateCartBadge()
        binding.bottomNavigationView.selectedItemId = R.id.nav_home
    }

    private fun setupGreeting() {
        val userManager = UserManager.getInstance(this)
        val fullName = userManager.getFullName()
        // Format greeting matches wireframe: "Halo Nama,"
        binding.tvHomeGreeting.text = getString(R.string.greeting_format, fullName)
    }

    private fun setupRecyclerView() {
        menuAdapter = MenuAdapter(
            items = DummyMenuRepository.getAllItems(),
            onItemClick = { item: FoodItem ->
                // Open Detail Menu (Screen 9)
                val intent = Intent(this, DetailMenuActivity::class.java).apply {
                    putExtra(DetailMenuActivity.EXTRA_FOOD_ITEM, item)
                }
                startActivity(intent)
            },
            onQuickAdd = { item: FoodItem ->
                // Quick order directly to OrderActivity (Screen 7)
                CartManager.addItem(item, quantity = 1)
                updateCartBadge()
                Toast.makeText(this, "${item.name} dipilih untuk pesanan", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, OrderActivity::class.java)
                startActivity(intent)
            }
        )

        binding.rvFoodItems.apply {
            layoutManager = LinearLayoutManager(this@HomeActivity)
            adapter = menuAdapter
        }
    }

    private fun setupSearchAndFilters() {
        // Search text watcher
        binding.etSearchMenu.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filterMenu(s.toString(), currentCategory)
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        // Category Chips
        binding.chipAll.setOnClickListener {
            currentCategory = "Semua"
            filterMenu(binding.etSearchMenu.text.toString(), currentCategory)
        }
        binding.chipCoffee.setOnClickListener {
            currentCategory = "Kopi"
            filterMenu(binding.etSearchMenu.text.toString(), currentCategory)
        }
        binding.chipSnack.setOnClickListener {
            currentCategory = "Nyemil"
            filterMenu(binding.etSearchMenu.text.toString(), currentCategory)
        }
        binding.chipNonCoffee.setOnClickListener {
            currentCategory = "Non-Kopi"
            filterMenu(binding.etSearchMenu.text.toString(), currentCategory)
        }
    }

    private fun filterMenu(query: String, category: String) {
        val baseList = DummyMenuRepository.getItemsByCategory(category)
        val filtered = if (query.isBlank()) {
            baseList
        } else {
            baseList.filter {
                it.name.contains(query, ignoreCase = true) ||
                        it.description.contains(query, ignoreCase = true)
            }
        }

        menuAdapter.updateData(filtered)

        if (filtered.isEmpty()) {
            binding.tvEmptySearch.visibility = View.VISIBLE
        } else {
            binding.tvEmptySearch.visibility = View.GONE
        }
    }

    private fun setupCartButton() {
        binding.cardCartHolder.setOnClickListener {
            val intent = Intent(this, CartActivity::class.java)
            startActivity(intent)
        }
        updateCartBadge()
    }

    private fun updateCartBadge() {
        val totalCount = CartManager.getItems().sumOf { it.quantity }
        if (totalCount > 0) {
            binding.tvCartBadge.visibility = View.VISIBLE
            binding.tvCartBadge.text = totalCount.toString()
        } else {
            binding.tvCartBadge.visibility = View.GONE
        }
    }

    private fun setupBottomNavigation() {
        binding.bottomNavigationView.selectedItemId = R.id.nav_home

        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> true
                R.id.nav_order -> {
                    // Navigate to Order (Screen 7)
                    val intent = Intent(this, OrderActivity::class.java)
                    startActivity(intent)
                    false
                }
                R.id.nav_profile -> {
                    // Navigate to Profile (Screen 6)
                    val intent = Intent(this, ProfileActivity::class.java)
                    startActivity(intent)
                    false
                }
                else -> false
            }
        }
    }
}
