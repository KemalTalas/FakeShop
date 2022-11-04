package com.kemaltalas.fakeshop.presentation.fragments

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.kemaltalas.fakeshop.R
import com.kemaltalas.fakeshop.data.util.Constants
import com.kemaltalas.fakeshop.data.util.SharedPrefs
import com.kemaltalas.fakeshop.databinding.FragmentUserPanelBinding
import com.kemaltalas.fakeshop.presentation.viewmodels.AuthViewModel
import com.kemaltalas.fakeshop.presentation.viewmodels.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class UserPanelFragment : Fragment(R.layout.fragment_user_panel) {

    private var fragmentBinding : FragmentUserPanelBinding? = null

    @Inject
    lateinit var viewModel: AuthViewModel

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentUserPanelBinding.bind(view)
        fragmentBinding = binding

        var text = ""
        viewModel.getPerson.observe(viewLifecycleOwner){
            binding.userpanelUsernameTv.text = it.firstname.replaceFirstChar { it.uppercase() }+" "+it.lastname.replaceFirstChar { it.uppercase() }
            binding.root.invalidate()
        }


    }

}