package com.kemaltalas.fakeshop.presentation.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.kemaltalas.fakeshop.R
import com.kemaltalas.fakeshop.databinding.FragmentUserPanelBinding
import com.kemaltalas.fakeshop.presentation.viewmodels.AuthViewModel
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

        viewModel.getPerson.observe(viewLifecycleOwner){
            binding.userpanelFirstlastnameTv.text = it.firstname.replaceFirstChar { it.uppercase() }+" "+it.lastname.replaceFirstChar { it.uppercase() }
            binding.userpanelUsernameTv.text = "@${it.username.lowercase()}"
            binding.root.invalidate()
        }

        val sharedPrefs = requireActivity().getSharedPreferences("isLoggedIn",Context.MODE_PRIVATE)

        binding.upLogout.setOnClickListener {
            findNavController().navigateUp()
            Toast.makeText(requireContext(),"Successfully Logged Out",Toast.LENGTH_SHORT).show()
            sharedPrefs.edit().putBoolean("isLogged",false).apply()
        }

    }

}