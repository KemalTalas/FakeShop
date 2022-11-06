package com.kemaltalas.fakeshop.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.kemaltalas.fakeshop.data.model.Product
import com.kemaltalas.fakeshop.databinding.RowCategoryBinding
import java.util.*

class CategoriesAdapter : RecyclerView.Adapter<CategoriesAdapter.CategoryViewHolder>() {

    private var onItemClickListener : ((Product)-> Unit) = {}
    fun setOnItemClickListener(listener: (Product) -> Unit){
        onItemClickListener = listener
    }

    private val diffUtil = object : DiffUtil.ItemCallback<Product>(){
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id==newItem.id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id==newItem.id
        }

    }
    val recyclerListDiffer = AsyncListDiffer(this@CategoriesAdapter,diffUtil)

    private var categoryName : List<Product>
        get() = recyclerListDiffer.currentList.distinctBy { it.category }.sortedBy { it.category }
        set(value) = recyclerListDiffer.submitList(value)

    inner class CategoryViewHolder(private val binding : RowCategoryBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product){
            binding.rowCategoryName.text = product.category.replaceFirstChar {
                it.uppercase()
            }

            binding.rowCategoryButton.setOnClickListener {
                onItemClickListener(product)
            }

        }

    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding = RowCategoryBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = categoryName[position]
        holder.bind(category)
    }

    override fun getItemCount(): Int {
        return categoryName.size
    }

}