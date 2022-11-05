package com.kemaltalas.fakeshop.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.kemaltalas.fakeshop.data.model.Product
import com.kemaltalas.fakeshop.databinding.RowProductsBinding

class ProductsAdapter : RecyclerView.Adapter<ProductsAdapter.ProductsViewHolder>() {

    inner class ProductsViewHolder(private val binding: RowProductsBinding) : RecyclerView.ViewHolder(binding.root){
        fun bindItem(product: Product){
            binding.rowProductTitle.text = product.title
            binding.rowProductPrice.text = "$${String.format("%.2f",product.price.toDouble())}"
            binding.rowProductDesc.text = product.description.replaceFirstChar { it.uppercase() }
            binding.rowProductComments.text = "(${product.rating.count})"
            binding.rowProductRating.rating = product.rating.rate.toFloat()

            Glide.with(binding.rowProductImage)
                .load(product.image)
                .into(binding.rowProductImage)

            binding.productRowItem.setOnClickListener {
                onItemClickListener(product)
                notifyDataSetChanged()
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
    val recyclerListDiffer = AsyncListDiffer(this@ProductsAdapter,diffUtil)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewHolder {
        val binding = RowProductsBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ProductsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) {
        val product = recyclerListDiffer.currentList[position]
        holder.bindItem(product)
    }

    override fun getItemCount(): Int {
        return recyclerListDiffer.currentList.size
    }

}