package com.kemaltalas.fakeshop.presentation.fragments

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.kemaltalas.fakeshop.R
import com.kemaltalas.fakeshop.databinding.FragmentBasketBinding
import com.kemaltalas.fakeshop.presentation.adapters.BasketAdapter
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

    private lateinit var textView: TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentBasketBinding.bind(view)
        fragmentBinding = binding

        textView = binding.basketTextView

        binding.basketRecyclerview.adapter = adapter
        binding.basketRecyclerview.layoutManager = LinearLayoutManager(requireContext())
        ItemTouchHelper(swipeCallback).attachToRecyclerView(binding.basketRecyclerview)
        adapter.notifyDataSetChanged()

        viewModel.getCartItems().observe(viewLifecycleOwner){
            adapter.listDiffer.submitList(it)
            viewModel.calculateTotal(it)
        }

        binding.basketCheckoutLayout.visibility = View.VISIBLE

        viewModel.totalItemsPrice.observe(viewLifecycleOwner){
           binding.basketPrice.text = "$${String.format("%.2f",it)}"

            if (it == 0.0){
                binding.basketTextView.visibility = View.VISIBLE
                binding.basketRecyclerview.visibility = View.INVISIBLE
                binding.cartFab.visibility = View.GONE
                binding.basketCheckoutLayout.visibility = View.GONE
            }else{
                binding.basketRecyclerview.visibility = View.VISIBLE
                binding.cartFab.visibility = View.VISIBLE
                binding.basketCheckoutLayout.visibility = View.VISIBLE
                binding.basketTextView.visibility = View.INVISIBLE
            }

            binding.root.invalidate()
        }


        binding.cartFab.setOnClickListener {
            MaterialAlertDialogBuilder(requireContext(),R.style.MaterialAlertDialog_App)
                .setTitle("Warning")
                .setMessage("Are you sure you want to clear the list?")
                .setNeutralButton("Cancel"){dialog,which->
                    dialog.cancel()
                }.setPositiveButton("Clear"){dialog,which->
                    viewModel.clearCart()
                }.show()
        }
    }

    private val swipeCallback = object : ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){
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
        }
    }
}