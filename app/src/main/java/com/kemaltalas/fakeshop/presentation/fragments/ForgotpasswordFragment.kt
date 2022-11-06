package com.kemaltalas.fakeshop.presentation.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.kemaltalas.fakeshop.R
import com.kemaltalas.fakeshop.data.util.hideKeyboards
import com.kemaltalas.fakeshop.databinding.FragmentForgotpasswordBinding
import com.kemaltalas.fakeshop.presentation.viewmodels.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ForgotpasswordFragment : Fragment(R.layout.fragment_forgotpassword) {

    private var fragmentBinding : FragmentForgotpasswordBinding? = null

    @Inject
    lateinit var viewModel: AuthViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentForgotpasswordBinding.bind(view)
        fragmentBinding = binding

        binding.fpRecoverbutton.setOnClickListener {
            viewModel.getPerson.observe(viewLifecycleOwner){ user->
                if (user != null){
                    if (user.username == binding.fpTieUsername.text.toString()){
                        binding.fpNotfound.visibility = View.GONE
                        binding.fpSecureanswerLayout.visibility = View.VISIBLE
                        binding.fpSubmitbutton.setOnClickListener {
                            if (user.secureAnswer == binding.fpTieSecureanswer.text.toString()){
                                binding.fpPasswordtv.text = user.password
                                binding.fpIncorrectans.visibility = View.GONE
                                binding.fpPasswordlayout.visibility = View.VISIBLE
                            }else{
                                binding.fpPasswordlayout.visibility = View.GONE
                                binding.fpIncorrectans.visibility = View.VISIBLE
                                Toast.makeText(requireContext(),"Your answer was: ${user.secureAnswer}",Toast.LENGTH_SHORT).show()
                            }
                        }
                    }else{
                        binding.fpPasswordlayout.visibility = View.GONE
                        binding.fpIncorrectans.visibility = View.GONE
                        binding.fpSecureanswerLayout.visibility = View.GONE
                        binding.fpNotfound.visibility = View.VISIBLE
                    }
                }else{
                    Log.e("Forgot Pasword","User is null")
                }
            }
        }

        binding.fpConstraint.setOnClickListener { hideKeyboards() }

        binding.fpBackbutton.setOnClickListener { findNavController().navigateUp() }

    }

}