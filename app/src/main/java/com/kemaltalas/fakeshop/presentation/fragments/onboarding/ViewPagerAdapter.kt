package com.kemaltalas.fakeshop.presentation.fragments.onboarding

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.kemaltalas.fakeshop.R
import com.kemaltalas.fakeshop.databinding.ItemOnboardBinding


class ViewPagerAdapter(
    val images: List<Int>,
    val textlist : List<String>,
    val descList : List<String>,
    val colorslist : List<List<Int>>,
    val visibleList : List<Int>,
    ) : RecyclerView.Adapter<ViewPagerAdapter.ViewPagerViewHolder>(){

    private var onItemClickListener : ((Button)-> Unit) = {}
    fun setOnItemClickListener(listener: (Button) -> Unit){
        onItemClickListener = listener
    }

    inner class ViewPagerViewHolder(private val binding: ItemOnboardBinding) : RecyclerView.ViewHolder(binding.root){
        fun bindItems(position: Int){
            binding.obTv.text = textlist[adapterPosition]
            binding.obDesctv.text = descList[adapterPosition]
            binding.obDot1.setBackgroundColor(binding.root.resources.getColor(R.color.teal_200))
            binding.obImage.setImageResource(images[position])

            binding.obButton.visibility = visibleList[position]
            binding.obButton.setOnClickListener {
                onItemClickListener(binding.obButton)
            }

                binding.obDot1.setBackgroundResource(colorslist[position][0])
                binding.obDot2.setBackgroundResource(colorslist[position][1])
                binding.obDot3.setBackgroundResource(colorslist[position][2])

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerViewHolder {
        val binding = ItemOnboardBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewPagerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewPagerViewHolder, position: Int) {
        return holder.bindItems(position)
    }

    override fun getItemCount(): Int {
        return images.size
    }

}