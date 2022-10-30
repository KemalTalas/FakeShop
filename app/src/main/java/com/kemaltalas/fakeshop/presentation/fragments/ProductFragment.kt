package com.kemaltalas.fakeshop.presentation.fragments

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder

import com.kemaltalas.fakeshop.R
import com.kemaltalas.fakeshop.data.model.Product
import com.kemaltalas.fakeshop.data.util.hideKeyboards
import com.kemaltalas.fakeshop.databinding.FragmentProductBinding
import com.kemaltalas.fakeshop.presentation.adapters.ProductsAdapter
import com.kemaltalas.fakeshop.presentation.viewmodels.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

@AndroidEntryPoint
class ProductFragment : Fragment(R.layout.fragment_product) {

    private var fragmentBinding : FragmentProductBinding? = null

    @Inject
    lateinit var viewModel: HomeViewModel
    @Inject
    lateinit var adapter: ProductsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentProductBinding.bind(view)
        fragmentBinding = binding

        viewModel.getAllProducts()

        viewModel.products.observe(viewLifecycleOwner){
            adapter.recyclerListDiffer.submitList(it.data)
        }

        binding.productsRecycler.adapter = adapter
        binding.productsRecycler.layoutManager = StaggeredGridLayoutManager(2,RecyclerView.VERTICAL)

        adapter.setOnItemClickListener {
            val action = ProductFragmentDirections.actionProductFragmentToDetailScreenFragment(it)
            findNavController().navigate(action)
        }

        //**BACK BUTTON
        binding.productsBackbutton.setOnClickListener {
            findNavController().popBackStack()
        }
        //** SORT    ***///

        val sortArray = arrayOf("Best Match","Price Asc","Price Desc")
        var adbIndex = 0
        var selectedItem = ""
        binding.productsSortbutton.setOnClickListener {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle("Sort")
                .setSingleChoiceItems(sortArray,adbIndex){ dialog,which->
                    adbIndex = which
                    selectedItem = sortArray[which]

                }
                .setPositiveButton("Sort"){dialog,which->
                    if(selectedItem == "Price Asc"){
                        viewModel.products.observe(viewLifecycleOwner){
                            adapter.recyclerListDiffer.submitList(it.data?.sortedBy { it.price.toDouble() })
                            binding.productsRecycler.smoothScrollToPosition(0)
                        }
                    }
                    if(selectedItem == "Price Desc"){
                        viewModel.products.observe(viewLifecycleOwner){
                            adapter.recyclerListDiffer.submitList(it.data?.sortedByDescending { it.price.toDouble() })
                            binding.productsRecycler.smoothScrollToPosition(0)
                        }
                    }
                    if(selectedItem == "Best Match"){
                        viewModel.products.observe(viewLifecycleOwner){
                            adapter.recyclerListDiffer.submitList(it.data)
                            binding.productsRecycler.smoothScrollToPosition(0)
                        }
                    }
                }
                .show()
        }

        //FILTER
        val filterArray = arrayOf("Electronics","Jewelery","Men's Clothing","Women's Clothing")
        var filterArrayBool : BooleanArray = booleanArrayOf(false,false,false,false)
        val currentItemList = Arrays.asList(*filterArray)
        val selectedItemList = ArrayList<String>()


        binding.productsFilterbutton.setOnClickListener {
            selectedItemList.forEach {
                Toast.makeText(requireContext(),it,Toast.LENGTH_SHORT).show()
            }

            MaterialAlertDialogBuilder(requireContext())
                .setTitle("Filter")
                .setMultiChoiceItems(filterArray,filterArrayBool) {dialog,which,isChecked ->
                    filterArrayBool[which] = isChecked
                }
                .setPositiveButton("Filter"){dialog,which->
                    for (i in filterArrayBool.indices){
                        val checked = filterArrayBool[i]
                        if (checked){
                            selectedItemList.add(currentItemList[i].lowercase())
                        }
                        selectedItemList.forEach { selected->
                            viewModel.products.observe(viewLifecycleOwner){
                                adapter.recyclerListDiffer.submitList(it.data?.filter { it.category== selected })
                            }
                        }
                        if (selectedItemList.size == 1){

                        }
                        when(selectedItemList.size){
                            1 -> {
                                viewModel.products.observe(viewLifecycleOwner){
                                    adapter.recyclerListDiffer.submitList(it.data?.filter { it.category== selectedItemList.get(0) })
                                }
                            }
                            2 -> {
                                viewModel.products.observe(viewLifecycleOwner){
                                    adapter.recyclerListDiffer.submitList(it.data?.filter { it.category== selectedItemList.get(0) || it.category == selectedItemList.get(1) })
                                }
                            }
                            3 -> {
                                viewModel.products.observe(viewLifecycleOwner){
                                    adapter.recyclerListDiffer.submitList(it.data?.filter { it.category== selectedItemList.get(0) || it.category == selectedItemList.get(1) || it.category == selectedItemList.get(2)  })
                                }
                            }
                            4 -> {
                                viewModel.products.observe(viewLifecycleOwner){
                                    adapter.recyclerListDiffer.submitList(it.data?.filter { it.category== selectedItemList.get(0) || it.category == selectedItemList.get(2) || it.category == selectedItemList.get(3) })
                                }
                            } else ->
                            viewModel.products.observe(viewLifecycleOwner){
                                adapter.recyclerListDiffer.submitList(it.data)
                            }
                        }



                    }
                    binding.productsRecycler.smoothScrollToPosition(0)
                    selectedItemList.clear()
                } .setNeutralButton("ShowAll"){dialog,which ->
                    viewModel.products.observe(viewLifecycleOwner){
                        adapter.recyclerListDiffer.submitList(it.data) }
                    filterArrayBool.forEach { it==false }
                    binding.productsRecycler.smoothScrollToPosition(0)
                }
                .show()

            }

        }








    }




