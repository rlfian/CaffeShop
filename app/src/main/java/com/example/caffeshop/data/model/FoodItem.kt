package com.example.caffeshop.data.model

import java.io.Serializable

data class FoodItem(
    val id: String,
    val name: String,
    val category: String, // "Kopi", "Nyemil", "Non-Kopi"
    val description: String,
    val price: Int,
    val imageRes: Int,
    val badge: String = "",
    val rating: Double = 4.9,
    val reviewCount: Int = 120,
    var isFavorite: Boolean = false
) : Serializable
