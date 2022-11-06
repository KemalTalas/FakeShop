package com.kemaltalas.fakeshop.presentation.fragments.onboarding

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.kemaltalas.fakeshop.R
import com.kemaltalas.fakeshop.databinding.FragmentThirdOnboardBinding
import com.kemaltalas.fakeshop.databinding.FragmentViewPagerBinding

class ThirdOnboardFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_third_onboard, container, false)

        val viewPager =  activity?.findViewById<ViewPager2>(R.id.view_pager)

        val button = activity?.findViewById<Button>(R.id.thirdob_explorebutton)

        val binding = FragmentThirdOnboardBinding.bind(view)

        binding.thirdobExplorebutton.setOnClickListener {
            findNavController().navigate(ViewPagerFragmentDirections.actionViewPagerFragmentToHomeFragment())
            Toast.makeText(requireContext(),"Clicked",Toast.LENGTH_SHORT).show()
        }


        //findnavto home and onboardingfinished on explorebutton.setonclicklistener

        return view
    }

    private fun onBoardingFinished(){
        val sharedPref = requireActivity().getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putBoolean("Finished", true)
        editor.apply()
    }

}