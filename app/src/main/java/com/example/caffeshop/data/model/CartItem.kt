package com.example.caffeshop.data.model

import java.io.Serializable

data class CartItem(
    val foodItem: FoodItem,
    var quantity: Int = 1,
    var temperature: String = "Iced",
    var sugarLevel: String = "Normal",
    var size: String = "Regular",
    val extraPricePerUnit: Int = 0
) : Serializable {
    val unitPrice: Int
        get() = foodItem.price + extraPricePerUnit

    val subtotal: Int
        get() = unitPrice * quantity
}
