package com.kemaltalas.fakeshop.presentation.fragments.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.kemaltalas.fakeshop.R
import com.kemaltalas.fakeshop.databinding.FragmentViewPagerBinding
import com.kemaltalas.fakeshop.presentation.fragments.HomeFragmentDirections

class ViewPagerFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val imageslist = listOf(R.drawable.onboardfirst,R.drawable.onboardsecond,R.drawable.onboardthird)
        val textlist = listOf("Shop Securely","Make Easy Payments","Get After Sales Support")
        val desclist = listOf("Buy the product that you want safely among thousands of products on FakeShop.",
            "Shop easily with credit card or cash on delivery options on FakeShop.",
            "You can reach our FakeShop's technical team 24/7 after sales.")

        val visiblelist = listOf(View.GONE,View.GONE,View.VISIBLE)

        val colorlist1 = listOf(R.color.teal_700,R.color.teal_200,R.color.teal_200)
        val colorlist2 = listOf(R.color.teal_200,R.color.teal_700,R.color.teal_200)
        val colorlist3 = listOf(R.color.teal_200,R.color.teal_200,R.color.teal_700)

        val colorslist = listOf(colorlist1,colorlist2,colorlist3)

        val view = inflater.inflate(R.layout.fragment_view_pager,container,false)

        val fragmentList = arrayListOf<Fragment>(
            FirstOnboardFragment(),
            SecondOnboardFragment(),
            ThirdOnboardFragment()
        )
//        val navigateToHome = findNavController().navigate(ViewPagerFragmentDirections.actionViewPagerFragmentToHomeFragment())
//        val adapter = ViewPagerAdapter(fragmentList,requireActivity().supportFragmentManager,lifecycle)
        val adapter = ViewPagerAdapter(imageslist,textlist,desclist,colorslist,visiblelist)
        val binding = FragmentViewPagerBinding.bind(view)

        adapter.setOnItemClickListener {
            val action = ViewPagerFragmentDirections.actionViewPagerFragmentToHomeFragment()
            findNavController().navigate(action)
        }

        binding.viewPager.adapter = adapter

        return view
    }

}