package com.kemaltalas.fakeshop.presentation.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.kemaltalas.fakeshop.R
import com.kemaltalas.fakeshop.data.model.CartItems
import com.kemaltalas.fakeshop.data.model.Product
import com.kemaltalas.fakeshop.databinding.FragmentDetailScreenBinding
import com.kemaltalas.fakeshop.databinding.FragmentHomeBinding
import com.kemaltalas.fakeshop.presentation.adapters.FavoritesAdapter
import com.kemaltalas.fakeshop.presentation.viewmodels.DetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DetailScreenFragment : Fragment(R.layout.fragment_detail_screen) {

    private var fragmentBinding: FragmentDetailScreenBinding? = null

    private lateinit var product: Product

    private lateinit var cartItems: CartItems

    @Inject
    lateinit var viewModel: DetailViewModel

    private var liked = false

    @Inject
    lateinit var adapter: FavoritesAdapter


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


        binding.detailFav.apply {
            if (product.isFavorited){
                setImageResource(R.drawable.favorited_ic)
            }else{
                setImageResource(R.drawable.not_favorited_ic)
            }
        }

        binding.detailFav.setOnClickListener {
            if (product.isFavorited){
                viewModel.deleteFavorites(product)
                product.isFavorited = false
                viewModel.updateFavoritesItem(product,false)
                binding.detailFav.setImageResource(R.drawable.not_favorited_ic)
                Snackbar.make(binding.detailCheckoutButton,"Removed from wishlist",Snackbar.LENGTH_SHORT).show()
            }else{
                viewModel.addFavorites(product)
                product.isFavorited = true
                viewModel.updateFavoritesItem(product,true)
                binding.detailFav.setImageResource(R.drawable.favorited_ic)
                Snackbar.make(binding.detailCheckoutButton,"Added to wishlist",Snackbar.LENGTH_SHORT).show()
            }
        }

        binding.detailCheckoutButton.setOnClickListener {

            Snackbar.make(binding.detailCheckoutButton,"Added To Cart",Snackbar.LENGTH_SHORT).show()
            cartItems = CartItems(product.id,product.image,product.title,product.price,1)
            viewModel.addToCart(cartItems)

        }



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