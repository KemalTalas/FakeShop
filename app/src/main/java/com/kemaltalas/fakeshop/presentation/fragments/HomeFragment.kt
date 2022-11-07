package com.kemaltalas.fakeshop.presentation.fragments

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.core.text.bold
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.facebook.shimmer.ShimmerFrameLayout
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

    private lateinit var shimmer: ShimmerFrameLayout



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentHomeBinding.bind(view)
        fragmentBinding = binding

        shimmer = binding.shimmer

        viewModel.getAllProducts()


        val firstLine = "Welcome Guest,\n"
        val secondLineClick = object : ClickableSpan(){
            override fun onClick(widget: View) {
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToLoginFragment())
            }
        }
        val secondLine = SpannableString("Please login for more enjoyable shopping")
        secondLine.setSpan(secondLineClick,7, 12, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)


        viewModel.user.observe(viewLifecycleOwner) {
            if (isLogged() && it != null) {
                val firstLine = SpannableStringBuilder()
                    .append("Welcome ")
                    .bold { append(it.firstname.replaceFirstChar { it.uppercase() }) }
                val secondLine = SpannableStringBuilder()
                    .append("\nShipping To: ")
                    .bold { append(it.city.replaceFirstChar { it.uppercase() } + "/" + it.country.replaceFirstChar { it.uppercase() }) }

                binding.homeWelcomeTv.setText(firstLine)
                binding.homeWelcomeTv.append(secondLine)

            } else {

                binding.homeWelcomeTv.setText(firstLine, TextView.BufferType.SPANNABLE)
                binding.homeWelcomeTv.append(secondLine)
                binding.homeWelcomeTv.movementMethod = LinkMovementMethod.getInstance()
            }

        }


        viewModel.products.observe(viewLifecycleOwner){
            when(it){
                is Resource.Success -> {
                    viewModel.favorites.observe(viewLifecycleOwner){ favorites ->
                        it.data?.forEach { product ->
                            favorites.forEach { favitem ->
                                if (product == favitem){
                                    product.isFavorited = true
                                }
                            }
                        }
                    }
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
                    Log.i("HomeFragment",it.message.toString())
                    Handler(Looper.myLooper()!!).postDelayed({
                        onDestroy()
                    },3000)
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

    }

    private fun isLogged() : Boolean{
        val sharedprefs = requireActivity().getSharedPreferences("isLoggedIn",Context.MODE_PRIVATE)
        return sharedprefs.getBoolean("isLogged",false)
    }

}