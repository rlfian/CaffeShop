package com.example.caffeshop.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.caffeshop.data.model.CartItem
import com.example.caffeshop.databinding.ItemCartBinding
import com.example.caffeshop.util.CurrencyHelper

class CartAdapter(
    private var items: List<CartItem>,
    private val onQuantityChanged: (position: Int, newQuantity: Int) -> Unit,
    private val onItemDeleted: (position: Int) -> Unit
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    fun updateData(newItems: List<CartItem>) {
        this.items = newItems
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = ItemCartBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(items[position], position)
    }

    override fun getItemCount(): Int = items.size

    inner class CartViewHolder(private val binding: ItemCartBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: CartItem, position: Int) {
            binding.ivCartFoodImg.setImageResource(item.foodItem.imageRes)
            binding.tvCartFoodName.text = item.foodItem.name
            binding.tvCartOptions.text = "${item.temperature} • ${item.sugarLevel} • ${item.size}"
            binding.tvCartPrice.text = CurrencyHelper.formatRupiah(item.subtotal)
            binding.tvCartQuantity.text = item.quantity.toString()

            binding.btnCartMinus.setOnClickListener {
                onQuantityChanged(position, item.quantity - 1)
            }

            binding.btnCartPlus.setOnClickListener {
                onQuantityChanged(position, item.quantity + 1)
            }

            binding.btnCartDelete.setOnClickListener {
                onItemDeleted(position)
            }
        }
    }
}
