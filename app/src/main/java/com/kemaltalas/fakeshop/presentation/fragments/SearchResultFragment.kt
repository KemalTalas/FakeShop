package com.kemaltalas.fakeshop.presentation.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kemaltalas.fakeshop.R
import com.kemaltalas.fakeshop.data.util.Resource
import com.kemaltalas.fakeshop.databinding.FragmentSearchResultBinding
import com.kemaltalas.fakeshop.presentation.adapters.ProductsAdapter
import com.kemaltalas.fakeshop.presentation.viewmodels.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SearchResultFragment : Fragment(R.layout.fragment_search_result) {

    private var fragmentBinding : FragmentSearchResultBinding? = null

    @Inject
    lateinit var viewModel: HomeViewModel

    @Inject
    lateinit var adapter: ProductsAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentSearchResultBinding.bind(view)
        fragmentBinding = binding

        binding.productsBackbutton.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.productsSearchView.onActionViewExpanded()

        viewModel.getAllProducts()

        viewModel.products.observe(viewLifecycleOwner){
            when(it){
                is Resource.Success ->{
                    //adapter.recyclerListDiffer.submitList(it.data?.take(8))
                    adapter.recyclerListDiffer.submitList(it.data?.filter {
                        it.category == "electronics"
                    })
                }
                is Resource.Loading ->{

                }
                is Resource.Error -> {

                }
            }
        }

        binding.searchRecycler.adapter = adapter
        binding.searchRecycler.layoutManager = GridLayoutManager(requireContext(),2,RecyclerView.VERTICAL,true)

    }

}