package com.example.caffeshop.data.repository

import com.example.caffeshop.data.model.CartItem
import com.example.caffeshop.data.model.FoodItem
import com.example.caffeshop.data.model.OrderModel

object CartManager {

    private val cartItems = mutableListOf<CartItem>()
    private var appliedDiscount: Int = 0
    private var promoCodeApplied: String = ""

    private val orderHistoryList = mutableListOf(
        OrderModel(
            orderId = "#CS-82910",
            itemsSummary = "2x Kopi Susu Gula Aren, 1x Donat Cokelat",
            recipientName = "Robby",
            address = "Jl. Sukapura No. 15, Bandung",
            addressDetail = "Kost Orange, kamar No. 3",
            paymentMethod = "QRIS / GoPay",
            totalAmount = 56000,
            orderDate = "Kemarin, 14:30 WIB",
            status = "Selesai"
        ),
        OrderModel(
            orderId = "#CS-83044",
            itemsSummary = "1x Matcha Latte, 1x Sourdough Loaf",
            recipientName = "Robby",
            address = "Jl. Telekomunikasi No. 1, Bandung",
            addressDetail = "Gedung Tokong Nanas Lt. 2",
            paymentMethod = "Tunai / COD",
            totalAmount = 62000,
            orderDate = "2 hari yang lalu",
            status = "Selesai"
        )
    )

    fun addItem(
        foodItem: FoodItem,
        quantity: Int = 1,
        temperature: String = "Iced",
        sugarLevel: String = "Normal",
        size: String = "Regular",
        extraPricePerUnit: Int = 0
    ) {
        val existing = cartItems.find {
            it.foodItem.id == foodItem.id &&
                    it.temperature == temperature &&
                    it.sugarLevel == sugarLevel &&
                    it.size == size
        }
        if (existing != null) {
            existing.quantity += quantity
        } else {
            cartItems.add(
                CartItem(
                    foodItem = foodItem,
                    quantity = quantity,
                    temperature = temperature,
                    sugarLevel = sugarLevel,
                    size = size,
                    extraPricePerUnit = extraPricePerUnit
                )
            )
        }
    }

    fun getItems(): List<CartItem> = cartItems

    fun updateQuantity(index: Int, newQuantity: Int) {
        if (index in cartItems.indices) {
            if (newQuantity <= 0) {
                cartItems.removeAt(index)
            } else {
                cartItems[index].quantity = newQuantity
            }
        }
    }

    fun removeItem(index: Int) {
        if (index in cartItems.indices) {
            cartItems.removeAt(index)
        }
    }

    fun clearCart() {
        cartItems.clear()
        appliedDiscount = 0
        promoCodeApplied = ""
    }

    fun getSubtotal(): Int {
        return cartItems.sumOf { it.subtotal }
    }

    fun getDeliveryFee(): Int {
        if (cartItems.isEmpty()) return 0
        val subtotal = getSubtotal()
        // Free delivery over Rp 60.000
        return if (subtotal >= 60000) 0 else 10000
    }

    fun applyPromo(code: String): Boolean {
        if (code.trim().equals("DISKONKOPI", ignoreCase = true)) {
            appliedDiscount = 10000
            promoCodeApplied = "DISKONKOPI"
            return true
        }
        return false
    }

    fun getDiscount(): Int = appliedDiscount

    fun getTotalPayment(): Int {
        val raw = getSubtotal() + getDeliveryFee() - appliedDiscount
        return if (raw < 0) 0 else raw
    }

    fun addOrderToHistory(order: OrderModel) {
        orderHistoryList.add(0, order)
    }

    fun getOrderHistory(): List<OrderModel> = orderHistoryList
}
