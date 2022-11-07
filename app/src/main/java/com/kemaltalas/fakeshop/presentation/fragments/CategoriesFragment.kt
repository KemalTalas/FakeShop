package com.kemaltalas.fakeshop.presentation.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.kemaltalas.fakeshop.R
import com.kemaltalas.fakeshop.data.util.Resource
import com.kemaltalas.fakeshop.databinding.FragmentCategoriesBinding
import com.kemaltalas.fakeshop.presentation.adapters.CategoriesAdapter
import com.kemaltalas.fakeshop.presentation.viewmodels.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CategoriesFragment : Fragment(R.layout.fragment_categories) {

    @Inject
    lateinit var viewModel: HomeViewModel
    @Inject
    lateinit var adapter: CategoriesAdapter

    private var fragmentBinding: FragmentCategoriesBinding? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentCategoriesBinding.bind(view)
        fragmentBinding = binding

        viewModel.getAllProducts()

        viewModel.products.observe(viewLifecycleOwner){
            when(it){
                is Resource.Success -> {
                    adapter.recyclerListDiffer.submitList(it.data)
                }
                is Resource.Loading -> {

                }
                is Resource.Error -> {

                }
            }
        }

        binding.categoriesRecyclerview.adapter = adapter
        binding.categoriesRecyclerview.layoutManager = LinearLayoutManager(requireContext())
        binding.categoriesRecyclerview.itemAnimator = null

        adapter.setOnItemClickListener {
            val action = CategoriesFragmentDirections.actionCategoriesFragmentToProductFragment(it.category)
            findNavController().navigate(action)
        }

    }

}