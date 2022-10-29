package com.kemaltalas.fakeshop.presentation.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.kemaltalas.fakeshop.R
import com.kemaltalas.fakeshop.data.util.Resource
import com.kemaltalas.fakeshop.databinding.FragmentHomeBinding
import com.kemaltalas.fakeshop.presentation.adapters.HomeAdapter
import com.kemaltalas.fakeshop.presentation.viewmodels.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    @Inject
    lateinit var viewModel: HomeViewModel
    @Inject
    lateinit var adapter: HomeAdapter

    private var fragmentBinding : FragmentHomeBinding? = null



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentHomeBinding.bind(view)
        fragmentBinding = binding

        viewModel.getAllProducts()

        viewModel.products.observe(viewLifecycleOwner){
            when(it){
                is Resource.Success -> {
                    adapter.recyclerListDiffer.submitList(it.data)
                    binding.homeRecycler.visibility = View.VISIBLE
                }
                is Resource.Loading -> {

                }
                is Resource.Error -> {
                    Log.i("HomeFragment","Error At Home")
                }
            }
        }

        binding.homeRecycler.adapter = adapter
        binding.homeRecycler.layoutManager = LinearLayoutManager(requireContext())


    }

}