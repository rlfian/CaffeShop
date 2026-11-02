package com.example.caffeshop.data.repository

import com.example.caffeshop.R
import com.example.caffeshop.data.model.FoodItem

object DummyMenuRepository {

    private val menuList = mutableListOf(
        FoodItem(
            id = "item_1",
            name = "Kopi Susu Gula Aren",
            category = "Kopi",
            description = "Espresso, susu, gula aren",
            price = 22000,
            imageRes = R.drawable.ic_coffee_cup,
            badge = "⭐ Best Seller",
            rating = 4.9,
            reviewCount = 312
        ),
        FoodItem(
            id = "item_2",
            name = "Donat Cokelat",
            category = "Nyemil",
            description = "Donat empuk, glaze cokelat",
            price = 12000,
            imageRes = R.drawable.ic_donut,
            badge = "🍩 Populer",
            rating = 4.8,
            reviewCount = 205
        ),
        FoodItem(
            id = "item_3",
            name = "Sourdough Loaf",
            category = "Nyemil",
            description = "Roti fermentasi alami",
            price = 35000,
            imageRes = R.drawable.ic_bread,
            badge = "🥖 Artisan",
            rating = 4.9,
            reviewCount = 98
        ),
        FoodItem(
            id = "item_4",
            name = "Cappuccino",
            category = "Kopi",
            description = "Espresso, foam susu lembut",
            price = 25000,
            imageRes = R.drawable.ic_coffee_cup,
            badge = "☕ Signature",
            rating = 4.8,
            reviewCount = 184
        ),
        FoodItem(
            id = "item_5",
            name = "Matcha Latte",
            category = "Non-Kopi",
            description = "Matcha jepang, susu segar",
            price = 27000,
            imageRes = R.drawable.ic_matcha,
            badge = "🍵 Favorit",
            rating = 4.9,
            reviewCount = 240
        ),
        FoodItem(
            id = "item_6",
            name = "Caramel Macchiato",
            category = "Kopi",
            description = "Espresso, steamed milk, saus karamel lezat",
            price = 28000,
            imageRes = R.drawable.ic_coffee_cup,
            badge = "⭐ Rekomendasi",
            rating = 4.8,
            reviewCount = 145
        ),
        FoodItem(
            id = "item_7",
            name = "Cold Brew Coffee",
            category = "Kopi",
            description = "Ekstraksi kopi dingin 12 jam, rasa lembut segar",
            price = 24000,
            imageRes = R.drawable.ic_coffee_cup,
            badge = "❄️ Cold Brew",
            rating = 4.7,
            reviewCount = 89
        ),
        FoodItem(
            id = "item_8",
            name = "Chocolate Signature",
            category = "Non-Kopi",
            description = "Cokelat Belgia pekat dengan taburan cocoa",
            price = 26000,
            imageRes = R.drawable.ic_chocolate,
            badge = "🍫 Manis Pas",
            rating = 4.9,
            reviewCount = 178
        ),
        FoodItem(
            id = "item_9",
            name = "Croissant Butter Almond",
            category = "Nyemil",
            description = "Pastry Prancis renyah mentega taburan almond",
            price = 24000,
            imageRes = R.drawable.ic_bread,
            badge = "🥐 Freshly Baked",
            rating = 4.9,
            reviewCount = 164
        ),
        FoodItem(
            id = "item_10",
            name = "Cinnamon Roll Glaze",
            category = "Nyemil",
            description = "Roti kayu manis panggang hangat cream cheese",
            price = 20000,
            imageRes = R.drawable.ic_donut,
            badge = "🧁 Hangat",
            rating = 4.8,
            reviewCount = 112
        )
    )

    fun getAllItems(): List<FoodItem> = menuList

    fun getItemsByCategory(category: String): List<FoodItem> {
        if (category.equals("Semua", ignoreCase = true)) return menuList
        return menuList.filter { it.category.equals(category, ignoreCase = true) }
    }

    fun searchItems(query: String): List<FoodItem> {
        if (query.isBlank()) return menuList
        return menuList.filter {
            it.name.contains(query, ignoreCase = true) ||
                    it.description.contains(query, ignoreCase = true) ||
                    it.category.contains(query, ignoreCase = true)
        }
    }

    fun getItemById(id: String): FoodItem? {
        return menuList.find { it.id == id }
    }
}
