package com.example.caffeshop.data.model

import java.io.Serializable

data class OrderModel(
    val orderId: String,
    val itemsSummary: String,
    val recipientName: String,
    val address: String,
    val addressDetail: String,
    val paymentMethod: String,
    val totalAmount: Int,
    val orderDate: String,
    val status: String = "Sedang Diantar"
) : Serializable
