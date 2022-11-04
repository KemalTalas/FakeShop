package com.kemaltalas.fakeshop.presentation.fragments

import android.os.Bundle
import android.text.Html
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.CharacterStyle
import android.text.style.ClickableSpan
import android.text.util.Linkify
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.core.text.HtmlCompat
import androidx.core.text.HtmlCompat.FROM_HTML_MODE_LEGACY
import androidx.core.text.bold
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
import kotlin.math.log

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

        viewModel.user.observe(viewLifecycleOwner){
            try {
                val firstLine = SpannableStringBuilder()
                    .append("Welcome ")
                    .bold { append(it.firstname.replaceFirstChar { it.uppercase() }) }
                val secondLine = SpannableStringBuilder()
                    .append("\nShipping To: ")
                    .bold { append(it.city.replaceFirstChar { it.uppercase() }+"/"+it.country.replaceFirstChar { it.uppercase() }) }

                binding.homeWelcomeTv.setText(firstLine)
                binding.homeWelcomeTv.append(secondLine)
            }catch (e: Exception){
                val firstLine = "Welcome Guest,\n"
                val secondLineClick = object : ClickableSpan(){
                    override fun onClick(widget: View) {
                        findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToLoginFragment())
                    }
                }
                val secondLine = SpannableString("Please login for more enjoyable shopping")
                secondLine.setSpan(secondLineClick,0, secondLine.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                binding.homeWelcomeTv.setText(firstLine,TextView.BufferType.SPANNABLE)
                binding.homeWelcomeTv.append(secondLine)
                binding.homeWelcomeTv.movementMethod = LinkMovementMethod.getInstance()
                Log.e("Error",e.stackTraceToString())
            }
        }


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