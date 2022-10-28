package com.kemaltalas.fakeshop.presentation.fragments

import android.app.ActionBar
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.ActionBarContainer
import androidx.appcompat.widget.ActionBarOverlayLayout
import androidx.fragment.app.Fragment
import androidx.navigation.ui.NavigationUI
import com.kemaltalas.fakeshop.R
import com.kemaltalas.fakeshop.databinding.FragmentHomeBinding

class HomeFragment : Fragment(R.layout.fragment_home) {

private var fragmentBinding : FragmentHomeBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentHomeBinding.bind(view)
        fragmentBinding = binding




    }

}