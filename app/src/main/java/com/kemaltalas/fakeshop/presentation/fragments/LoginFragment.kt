package com.kemaltalas.fakeshop.presentation.fragments

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.kemaltalas.fakeshop.R
import com.kemaltalas.fakeshop.data.model.User
import com.kemaltalas.fakeshop.data.model.UserDetails
import com.kemaltalas.fakeshop.databinding.FragmentLoginBinding
import com.kemaltalas.fakeshop.databinding.FragmentUserPanelBinding
import com.kemaltalas.fakeshop.presentation.viewmodels.AuthViewModel
import com.kemaltalas.fakeshop.presentation.viewmodels.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.fragment_login) {

    @Inject
    lateinit var viewModel: AuthViewModel

    private var fragmentBinding : FragmentLoginBinding? = null

    @Inject
    lateinit var sharedPreferences : SharedPreferences



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentLoginBinding.bind(view)
        fragmentBinding = binding

        viewModel.getPerson.observe(viewLifecycleOwner){
            if (it != null){
                findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToUserPanelFragment())
            }
        }

        binding.loginSigninBtn.setOnClickListener {
           viewModel.getPerson.observe(viewLifecycleOwner){ user->

            if (user.username==binding.loginTieUsername.text.toString() && user.password == binding.loginTiePassword.text.toString()){
                findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToUserPanelFragment())
                Toast.makeText(requireContext(),user.firstname,Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(requireContext(),"Olmadı...",Toast.LENGTH_SHORT).show()
            }
           }
        }
        binding.loginSignupBtn.setOnClickListener {
            lifecycleScope.launch {
                viewModel.deleteAllUsers()
            }
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
        }


    }

}