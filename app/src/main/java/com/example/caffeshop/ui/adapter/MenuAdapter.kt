package com.example.caffeshop.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.caffeshop.R
import com.example.caffeshop.data.model.FoodItem
import com.example.caffeshop.databinding.ItemFoodCardBinding
import com.example.caffeshop.util.CurrencyHelper

class MenuAdapter(
    private var items: List<FoodItem>,
    private val onItemClick: (FoodItem) -> Unit,
    private val onQuickAdd: (FoodItem) -> Unit
) : RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {

    fun updateData(newItems: List<FoodItem>) {
        this.items = newItems
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val binding = ItemFoodCardBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return MenuViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    inner class MenuViewHolder(private val binding: ItemFoodCardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: FoodItem) {
            binding.ivFoodImage.setImageResource(item.imageRes)
            binding.tvFoodName.text = item.name
            binding.tvFoodDescription.text = item.description
            binding.tvFoodPrice.text = CurrencyHelper.formatRupiah(item.price)

            if (item.badge.isNotBlank()) {
                binding.tvBadge.visibility = View.VISIBLE
                binding.tvBadge.text = item.badge
            } else {
                binding.tvBadge.visibility = View.GONE
            }

            if (item.isFavorite) {
                binding.ivFavorite.alpha = 1.0f
            } else {
                binding.ivFavorite.alpha = 0.35f
            }

            binding.ivFavorite.setOnClickListener {
                item.isFavorite = !item.isFavorite
                notifyItemChanged(bindingAdapterPosition)
            }

            binding.root.setOnClickListener {
                onItemClick(item)
            }

            binding.btnQuickAdd.setOnClickListener {
                onQuickAdd(item)
            }
        }
    }
}
