package com.kemaltalas.fakeshop.presentation.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.widget.SearchView
import androidx.core.view.size
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerFrameLayout
import com.kemaltalas.fakeshop.R
import com.kemaltalas.fakeshop.data.model.Product
import com.kemaltalas.fakeshop.data.util.Resource
import com.kemaltalas.fakeshop.databinding.FragmentHomeBinding
import com.kemaltalas.fakeshop.presentation.adapters.HomeAdapter
import com.kemaltalas.fakeshop.presentation.viewmodels.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    @Inject
    lateinit var viewModel: HomeViewModel
    @Inject
    lateinit var adapter: HomeAdapter

    private var fragmentBinding : FragmentHomeBinding? = null

    lateinit var shimmer: ShimmerFrameLayout



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentHomeBinding.bind(view)
        fragmentBinding = binding

        shimmer = binding.shimmer

        viewModel.getAllProducts()

        viewModel.products.observe(viewLifecycleOwner){
            when(it){
                is Resource.Success -> {
                    adapter.recyclerListDiffer.submitList(it.data)
                    shimmer.stopShimmer()
                    binding.shimmer.visibility = View.GONE
                    binding.homeRecycler.visibility = View.VISIBLE
                }
                is Resource.Loading -> {
                    binding.homeRecycler.visibility = View.GONE
                    shimmer.showShimmer(true)
                }
                is Resource.Error -> {
                    Log.i("HomeFragment","Error At Home")
                }
            }
        }


        adapter.setOnItemClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToDetailScreenFragment(it)
            findNavController().navigate(action)
        }

        binding.homeRecycler.adapter = adapter
        binding.homeRecycler.layoutManager = LinearLayoutManager(requireContext())
        binding.homeRecycler.itemAnimator = null


        binding.homeSearchView.clearFocus()
        binding.frameLayoutHome.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToProductFragment("all"))
        }

        binding.homeAllButton.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToProductFragment("all"))
        }
        binding.homeElectronicsButton.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToProductFragment("electronics"))
        }
        binding.homeJeweleryButton.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToProductFragment("jewelery"))
        }
        binding.homeMensclothingButton.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToProductFragment("men's clothing"))
        }
        binding.homeWomensclothingButton.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToProductFragment("women's clothing"))
        }

        binding.toolbarHome.setOnClickListener {
            for (i in adapter.products){
                println(i.isFavorited)
            }
        }


    }

}