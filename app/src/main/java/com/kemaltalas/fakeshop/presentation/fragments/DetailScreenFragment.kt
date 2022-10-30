package com.kemaltalas.fakeshop.presentation.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.kemaltalas.fakeshop.R
import com.kemaltalas.fakeshop.data.model.Product
import com.kemaltalas.fakeshop.databinding.FragmentDetailScreenBinding
import com.kemaltalas.fakeshop.databinding.FragmentHomeBinding

class DetailScreenFragment : Fragment(R.layout.fragment_detail_screen) {

    private var fragmentBinding: FragmentDetailScreenBinding? = null

    private lateinit var product: Product


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        product = DetailScreenFragmentArgs.fromBundle(requireArguments()).product
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentDetailScreenBinding.bind(view)
        fragmentBinding = binding


        binding.detailBackbutton.setOnClickListener {
            findNavController().popBackStack()
        }

        loadViews()

    }

    private fun loadViews(){

        fragmentBinding?.let {
            it.detailItemTitle.text = product.title
            it.detailItemDesc.text = product.description
            it.detailPrice.text = "$${product.price}"

            Glide.with(it.detailItemImage)
                .load(product.image)
                .into(it.detailItemImage)
        }

    }

}