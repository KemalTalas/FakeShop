package com.kemaltalas.fakeshop.presentation.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.kemaltalas.fakeshop.R
import com.kemaltalas.fakeshop.data.util.hideKeyboards
import com.kemaltalas.fakeshop.databinding.FragmentLoginBinding
import com.kemaltalas.fakeshop.presentation.viewmodels.AuthViewModel
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


        if (isLogged()){
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToUserPanelFragment())
        }

        binding.loginConstraint.setOnClickListener {
            hideKeyboards()
        }

        binding.loginForgotpassword.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToForgotpasswordFragment())
        }

        binding.loginSigninBtn.setOnClickListener {
           viewModel.getPerson.observe(viewLifecycleOwner){ user->
            if (user != null){
                if (user.username==binding.loginTieUsername.text.toString() && user.password == binding.loginTiePassword.text.toString()){
                    findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToUserPanelFragment())
                    val sharedpref = requireActivity().getSharedPreferences("isLoggedIn",Context.MODE_PRIVATE).edit()
                    sharedpref.putBoolean("isLogged",true).apply()
                    Toast.makeText(requireContext(),"Successfully logged in.",Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(requireContext(),"Your username or password is incorrect",Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(requireContext(),"Please create a new account",Toast.LENGTH_SHORT).show()
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
    private fun isLogged() : Boolean{
        val sharedprefs = requireActivity().getSharedPreferences("isLoggedIn",Context.MODE_PRIVATE)
        return sharedprefs.getBoolean("isLogged",false)
    }

}