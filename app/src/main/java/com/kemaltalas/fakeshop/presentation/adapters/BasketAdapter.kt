package com.kemaltalas.fakeshop.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kemaltalas.fakeshop.data.model.CartItems
import com.kemaltalas.fakeshop.databinding.RowHomeItemBinding

class BasketAdapter : RecyclerView.Adapter<BasketAdapter.BasketViewHolder>() {

    lateinit var price : TextView

    inner class BasketViewHolder(private val binding: RowHomeItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindItems(cartItems: CartItems){
            binding.homeRvItemName.text = cartItems.title
            binding.homeRvItemPrice.text = "$${String.format("%.2f",cartItems.price.toDouble())}"
            binding.homeRvItemDesc.text = cartItems.description.replaceFirstChar { it.uppercase() }
            binding.homeRatingbar.rating = cartItems.rate.rate.toFloat()
            binding.homeComments.text = "(${cartItems.rate.count})"

            Glide.with(binding.homeRvImage)
                .load(cartItems.image)
                .into(binding.homeRvImage)

            var totalprice = 0.00

            listDiffer.currentList.forEach {
                totalprice += it.price.toDouble()*it.quantity
            }

            println(totalprice)

        }

    }

     private val diffUtil = object : DiffUtil.ItemCallback<CartItems>(){
        override fun areItemsTheSame(oldItem: CartItems, newItem: CartItems): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CartItems, newItem: CartItems): Boolean {
            return oldItem.id == newItem.id
        }

    }

    var listDiffer = AsyncListDiffer<CartItems>(this@BasketAdapter,diffUtil)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BasketViewHolder {
        val binding = RowHomeItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return BasketViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BasketViewHolder, position: Int) {
        val product = listDiffer.currentList[position]
        return holder.bindItems(product)
    }

    override fun getItemCount(): Int {
        return listDiffer.currentList.size
    }

}