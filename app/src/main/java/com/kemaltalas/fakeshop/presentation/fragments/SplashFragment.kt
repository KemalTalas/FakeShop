package com.kemaltalas.fakeshop.presentation.fragments

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.kemaltalas.fakeshop.R

class SplashFragment : Fragment(R.layout.fragment_splash) {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_splash,container,false)

        Handler(Looper.myLooper()!!).postDelayed({
            if(onBoardingFinished()){
                findNavController().navigate(R.id.action_splashFragment_to_homeFragment)
            }else{
                findNavController().navigate(R.id.action_splashFragment_to_viewPagerFragment)
            }
        }, 2000)

        return view

    }

    private fun onBoardingFinished(): Boolean{
        val sharedPref = requireActivity().getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
        return sharedPref.getBoolean("Finished", false)
    }

}