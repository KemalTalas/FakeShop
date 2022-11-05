package com.kemaltalas.fakeshop.presentation.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
        ItemTouchHelper(swipeCallBack).attachToRecyclerView(binding.favoritesRecyclerview)


        viewModel.getFavorites().observe(viewLifecycleOwner){
            adapter.recyclerListDiffer.submitList(it)

            if (it.isEmpty()){
                binding.favoritesRecyclerview.visibility = View.INVISIBLE
                binding.favoritesTextview.visibility = View.VISIBLE
            }else{
                binding.favoritesTextview.visibility = View.INVISIBLE
                binding.favoritesRecyclerview.visibility = View.VISIBLE
            }

        }

        adapter.setOnItemClickListener {
            val action = FavoritesFragmentDirections.actionFavoritesFragmentToDetailScreenFragment(it)
            findNavController().navigate(action)
        }

    }

    private val swipeCallBack = object : ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val layoutPos = viewHolder.layoutPosition
            val selectedItem = adapter.recyclerListDiffer.currentList[layoutPos]
            viewModel.deleteFavorites(selectedItem)
        }

    }

}