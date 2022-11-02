package com.kemaltalas.fakeshop.presentation.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kemaltalas.fakeshop.R
import com.kemaltalas.fakeshop.databinding.FragmentBasketBinding
import com.kemaltalas.fakeshop.databinding.RowHomeItemBinding
import com.kemaltalas.fakeshop.presentation.adapters.BasketAdapter
import com.kemaltalas.fakeshop.presentation.adapters.FavoritesAdapter
import com.kemaltalas.fakeshop.presentation.viewmodels.FavoritesViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class BasketFragment : Fragment(R.layout.fragment_basket) {

    private var fragmentBinding : FragmentBasketBinding? = null

    @Inject
    lateinit var viewModel: FavoritesViewModel

    @Inject
    lateinit var adapter: BasketAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val binding = FragmentBasketBinding.bind(view)
        fragmentBinding = binding

        binding.basketRecyclerview.adapter = adapter
        binding.basketRecyclerview.layoutManager = LinearLayoutManager(requireContext())
        ItemTouchHelper(swipeCallback).attachToRecyclerView(binding.basketRecyclerview)
        adapter.notifyDataSetChanged()

        viewModel.getCartItems().observe(viewLifecycleOwner){
            adapter.listDiffer.submitList(it)
        }

        binding.basketCheckoutLayout.visibility = View.VISIBLE

        var total : Double = 0.00
        adapter.listDiffer.currentList.forEach { total += it.price.toDouble()*it.quantity
        println(it.price)
        }
        binding.basketPrice.text = total.toString()
        adapter.notifyDataSetChanged()

    }

    private val swipeCallback = object : ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT){
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val layoutPos = viewHolder.layoutPosition
            val selectedItem = adapter.listDiffer.currentList[layoutPos]
            viewModel.deleteFromCart(selectedItem)
            adapter.notifyDataSetChanged()
        }

    }


}