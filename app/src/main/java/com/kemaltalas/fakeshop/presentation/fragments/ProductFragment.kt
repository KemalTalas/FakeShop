package com.kemaltalas.fakeshop.presentation.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.kemaltalas.fakeshop.R
import com.kemaltalas.fakeshop.data.model.Product
import com.kemaltalas.fakeshop.data.remote.ApiService
import com.kemaltalas.fakeshop.data.util.Constants.BASE_URL
import com.kemaltalas.fakeshop.data.util.Resource
import com.kemaltalas.fakeshop.data.util.hideKeyboards
import com.kemaltalas.fakeshop.databinding.FragmentProductBinding
import com.kemaltalas.fakeshop.presentation.adapters.ProductsAdapter
import com.kemaltalas.fakeshop.presentation.viewmodels.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
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

    lateinit var list : MutableList<Product>

    private var filteredList = mutableListOf<Product>()


    private lateinit var categoryName : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        categoryName = ProductFragmentArgs.fromBundle(requireArguments()).categoryName
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentProductBinding.bind(view)
        fragmentBinding = binding

        if(categoryName=="all"){
            viewModel.getAllProducts()
            binding.productsFilterbutton.visibility = View.VISIBLE
        }else{
            filteredList.clear()
            viewModel.getCategoryProducts(categoryName)
            binding.productsFilterbutton.visibility = View.INVISIBLE
        }


        viewModel.products.observe(viewLifecycleOwner) {
            adapter.recyclerListDiffer.submitList(it.data)
            adapter.notifyDataSetChanged()
            list = adapter.recyclerListDiffer.currentList
        }

        binding.productsRecycler.adapter = adapter
        binding.productsRecycler.layoutManager = GridLayoutManager(requireContext(),2,GridLayoutManager.VERTICAL,false)
        binding.productsRecycler

        adapter.setOnItemClickListener {
            val action = ProductFragmentDirections.actionProductFragmentToDetailScreenFragment(it)
            findNavController().navigate(action)
        }

        //**BACK BUTTON
        binding.productsBackbutton.setOnClickListener {
            findNavController().popBackStack()
            filteredList.clear()
        }

        //** SORT    ***///

        val sortArray = arrayOf("Best Match","Price Asc","Price Desc","Most Liked","Most Commented")
        var adbIndex = 0
        var selectedItem = ""
        binding.productsPage.setOnClickListener { hideKeyboards() }
        binding.productsTop.setOnClickListener { hideKeyboards() }

//        viewModel.getCategoryProducts(categoryName)


        binding.productsSortbutton.setOnClickListener {
            hideKeyboards()
            MaterialAlertDialogBuilder(requireContext())
                .setSingleChoiceItems(sortArray,adbIndex){ dialog,which->
                    adbIndex = which
                    selectedItem = sortArray[which]

                }
                .setPositiveButton("Sort"){dialog,which->

                    when(selectedItem){
                        sortArray[0] ->{
                            if (filteredList.isNotEmpty()){
                                adapter.recyclerListDiffer.submitList(filteredList)
                            }
                            else {
                                adapter.recyclerListDiffer.submitList(list)
                            }
                            binding.productsRecycler.smoothScrollToPosition(0)
                        }
                        sortArray[1] ->{
                            if (filteredList.isNotEmpty()){
                                adapter.recyclerListDiffer.submitList(filteredList.sortedBy { it.price.toDouble() })
                            }
                            else {
                                adapter.recyclerListDiffer.submitList(list.sortedBy { it.price.toDouble() })
                            }
                            binding.productsRecycler.smoothScrollToPosition(0)
                        }
                        sortArray[2] -> {
                            if (filteredList.isNotEmpty()){
                                adapter.recyclerListDiffer.submitList(filteredList.sortedByDescending { it.price.toDouble() })
                            }
                            else{
                                adapter.recyclerListDiffer.submitList(list.sortedByDescending { it.price.toDouble() })
                            }
                            binding.productsRecycler.smoothScrollToPosition(0)

                        }
                        sortArray[3] -> {
                            if (filteredList.isNotEmpty()){
                                adapter.recyclerListDiffer.submitList(filteredList.sortedByDescending { it.rating.rate })
                            }
                            else{
                                adapter.recyclerListDiffer.submitList(list.sortedByDescending { it.rating.rate })
                            }
                            binding.productsRecycler.smoothScrollToPosition(0)

                        }
                        sortArray[4] -> {
                            if (filteredList.isNotEmpty()){
                                adapter.recyclerListDiffer.submitList(filteredList.sortedByDescending { it.rating.count })
                            }
                            else{
                                adapter.recyclerListDiffer.submitList(list.sortedByDescending { it.rating.count})
                            }
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

             MaterialAlertDialogBuilder(requireContext())
                .setMultiChoiceItems(filterArray,filterArrayBool) {dialog,which,isChecked ->
                    filterArrayBool[which] = isChecked
                }
                .setPositiveButton("FILTER"){dialog,which->
                    filteredList.clear()
                    for (i in filterArrayBool.indices){
                        val checked = filterArrayBool[i]
                        if (checked){
                            selectedItemList.add(currentItemList[i].lowercase())
                            }

                        when(selectedItemList.size){
                            1 -> {
                                filteredList.clear()
                                adapter.recyclerListDiffer.submitList(list.filter { it.category == selectedItemList[0] })
                                filteredList.addAll(list.filter { it.category == selectedItemList[0] })

                            }
                            2 -> {
                                filteredList.clear()
                                adapter.recyclerListDiffer.submitList(list.filter { it.category== selectedItemList[0] || it.category == selectedItemList[1] })
                                filteredList.addAll(list.filter { it.category== selectedItemList[0] || it.category == selectedItemList[1] })

                            }

                            3 -> {
                                filteredList.clear()
                                adapter.recyclerListDiffer.submitList(list.filter { it.category== selectedItemList[0] || it.category == selectedItemList[1] || it.category == selectedItemList[2] })
                                filteredList.addAll(list.filter { it.category== selectedItemList[0] || it.category == selectedItemList[2] })

                            }

                            else -> {
                                filteredList.clear()
                                adapter.recyclerListDiffer.submitList(list)
                                filteredList.addAll(list)
                            }

                        }

                    }

                    binding.productsRecycler.smoothScrollToPosition(0)
                    selectedItemList.clear()

                } .setNeutralButton("Show All"){dialog,which ->
                    adapter.recyclerListDiffer.submitList(list)
                     filteredList.addAll(adapter.recyclerListDiffer.currentList)
//                    filterArrayBool.forEach { it == true }
                    binding.productsRecycler.smoothScrollToPosition(0)

                }.show()
            }


        binding.productsSearchView.setOnQueryTextListener( object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                hideKeyboards()
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.products.observe(viewLifecycleOwner){
                    var searchedList = it.data?.filter { it.title.contains(newText.toString(),ignoreCase = true)}
                    adapter.recyclerListDiffer.submitList(searchedList)
                    if (searchedList.isNullOrEmpty() || searchedList.size > 19){
                        binding.productsResultTv.visibility = View.GONE
                    }else if(searchedList.size >0){
                        binding.productsResultTv.visibility = View.VISIBLE
                        binding.productsResultTv.text = "Found ${searchedList.size} results"
                    }else{
                        binding.productsResultTv.visibility = View.GONE
                    }
                }

                return false
            }

        })

        }

    }




