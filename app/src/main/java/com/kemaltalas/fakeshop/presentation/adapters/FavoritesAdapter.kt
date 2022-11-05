package com.kemaltalas.fakeshop.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kemaltalas.fakeshop.data.model.Product
import com.kemaltalas.fakeshop.databinding.RowHomeItemBinding
import com.kemaltalas.fakeshop.databinding.RowListItemBinding
import com.kemaltalas.fakeshop.databinding.RowProductsBinding

class FavoritesAdapter : RecyclerView.Adapter<FavoritesAdapter.FavoritesViewHolder>() {

    inner class FavoritesViewHolder(private val binding: RowHomeItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bindItem(product: Product){
            binding.homeRvItemName.text = product.title
            binding.homeRvItemPrice.text = "$${String.format("%.2f",product.price.toDouble())}"
            binding.homeRvItemDesc.text = product.description.replaceFirstChar { it.uppercase() }
            binding.homeRatingbar.rating = product.rating.rate.toFloat()
            binding.homeComments.text = "(${product.rating.count})"

            Glide.with(binding.homeRvImage)
                .load(product.image)
                .into(binding.homeRvImage)

            binding.homeRowItem.setOnClickListener {
                onItemClickListener(product)
            }
        }
    }
    private var onItemClickListener : ((Product)-> Unit) = {}
    fun setOnItemClickListener(listener: (Product) -> Unit){
        onItemClickListener = listener
    }


    private val diffUtil = object : DiffUtil.ItemCallback<Product>(){
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id==newItem.id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

    }
    val recyclerListDiffer = AsyncListDiffer(this@FavoritesAdapter,diffUtil)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesViewHolder {
        val binding = RowHomeItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return FavoritesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoritesViewHolder, position: Int) {
        val product = recyclerListDiffer.currentList[position]
        holder.bindItem(product)
    }

    override fun getItemCount(): Int {
        return recyclerListDiffer.currentList.size
    }



}