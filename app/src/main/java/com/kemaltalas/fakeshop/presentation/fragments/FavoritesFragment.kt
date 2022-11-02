package com.kemaltalas.fakeshop.presentation.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.kemaltalas.fakeshop.R
import com.kemaltalas.fakeshop.databinding.FragmentFavoritesBinding
import com.kemaltalas.fakeshop.presentation.adapters.BasketAdapter
import com.kemaltalas.fakeshop.presentation.adapters.FavoritesAdapter
import com.kemaltalas.fakeshop.presentation.viewmodels.FavoritesViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FavoritesFragment : Fragment(R.layout.fragment_favorites) {

    private var fragmentBinding : FragmentFavoritesBinding? = null

    @Inject
    lateinit var viewModel: FavoritesViewModel

    @Inject
    lateinit var adapter: FavoritesAdapter

    @Inject
    lateinit var cartAdapter: BasketAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentFavoritesBinding.bind(view)
        fragmentBinding = binding


        binding.favoritesRecyclerview.adapter = adapter
        binding.favoritesRecyclerview.layoutManager = LinearLayoutManager(requireContext())


        viewModel.getFavorites().observe(viewLifecycleOwner){
            adapter.recyclerListDiffer.submitList(it)
        }

        adapter.setOnItemClickListener {
            val action = FavoritesFragmentDirections.actionFavoritesFragmentToDetailScreenFragment(it)
            findNavController().navigate(action)
        }



    }

}