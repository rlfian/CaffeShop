package com.example.caffeshop.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.caffeshop.data.model.OrderModel
import com.example.caffeshop.databinding.ItemOrderHistoryBinding
import com.example.caffeshop.util.CurrencyHelper

class OrderHistoryAdapter(
    private var orders: List<OrderModel>,
    private val onReorder: (OrderModel) -> Unit
) : RecyclerView.Adapter<OrderHistoryAdapter.HistoryViewHolder>() {

    fun updateData(newOrders: List<OrderModel>) {
        this.orders = newOrders
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val binding = ItemOrderHistoryBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return HistoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.bind(orders[position])
    }

    override fun getItemCount(): Int = orders.size

    inner class HistoryViewHolder(private val binding: ItemOrderHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(order: OrderModel) {
            binding.tvHistoryOrderId.text = order.orderId
            binding.tvHistoryDate.text = order.orderDate
            binding.tvHistoryStatus.text = order.status
            binding.tvHistoryItems.text = order.itemsSummary
            binding.tvHistoryTotal.text = "Total: " + CurrencyHelper.formatRupiah(order.totalAmount)

            binding.btnHistoryReorder.setOnClickListener {
                onReorder(order)
            }
        }
    }
}
