package com.kemaltalas.fakeshop.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kemaltalas.fakeshop.data.model.Product
import com.kemaltalas.fakeshop.databinding.RowHomeItemBinding

class HomeAdapter : RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {

    private val diffUtil = object : DiffUtil.ItemCallback<Product>(){
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }
    }
    val recyclerListDiffer = AsyncListDiffer(this@HomeAdapter,diffUtil)

    var products : List<Product>
        get() = recyclerListDiffer.currentList.sortedByDescending { it.rating.rate }.take(8)
        set(value) = recyclerListDiffer.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val binding = RowHomeItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return HomeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val product = products[position]
        holder.bindItem(product)
    }

    override fun getItemCount(): Int {
        return  products.size
    }

    private var onItemClickListener : ((Product)-> Unit) = {}
    fun setOnItemClickListener(listener: (Product) -> Unit){
        onItemClickListener = listener
    }

    inner class HomeViewHolder(private val binding: RowHomeItemBinding) : RecyclerView.ViewHolder(binding.root){

        fun bindItem(product: Product){
            Glide.with(binding.homeRvImage)
                .load(product.image)
                .into(binding.homeRvImage)

            binding.homeRvItemName.text = product.title
            binding.homeRvItemDesc.text = product.description.replaceFirstChar { it.uppercase() }
            binding.homeRvItemPrice.text = "$${String.format("%.2f",product.price.toDouble())}"
            binding.homeRatingbar.rating = product.rating.rate.toFloat()
            binding.homeComments.text = "(${product.rating.count})"

            binding.homeRowItem.setOnClickListener {
                onItemClickListener(product)
            }
        }
    }
}