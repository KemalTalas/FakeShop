package com.kemaltalas.fakeshop.presentation.fragments

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.view.marginBottom
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.kemaltalas.fakeshop.R
import com.kemaltalas.fakeshop.data.model.UserDetails
import com.kemaltalas.fakeshop.data.util.hideKeyboards
import com.kemaltalas.fakeshop.databinding.FragmentRegisterBinding
import com.kemaltalas.fakeshop.presentation.viewmodels.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class RegisterFragment : Fragment(R.layout.fragment_register) {
    private var fragmentBinding : FragmentRegisterBinding? = null

    @Inject
    lateinit var viewModel: AuthViewModel

    private var _firstname = ""
    private var _lastname = ""
    private var _username = ""
    private var _password = ""
    private var _email = ""
    private var _city = ""
    private var _country = ""
    private var _address = ""
    private var _phone = ""
    private var _secureAnswer = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentRegisterBinding.bind(view)
        fragmentBinding = binding

        binding.registerLinearlayout.setOnClickListener {
            hideKeyboards()
        }

        binding.registerTilSecureanswer.setOnClickListener {
            binding.registerTilSecureanswer.marginBottom
        }

        binding.registerTieEmail.doOnTextChanged { text, start, before, count ->
            if (text?.contains('@')!!){
                binding.registerTilEmail.setEndIconDrawable(R.drawable.check_ic)
                binding.registerTilEmail.setEndIconTintList(ColorStateList.valueOf(Color.GREEN))
            }else{
                binding.registerTilEmail.setEndIconDrawable(R.drawable.check_green_ic)
                binding.registerTilEmail.setEndIconTintList(ColorStateList.valueOf(Color.GRAY))
            }
            if (text.length>40){
                binding.registerTilEmail.error = "Too Long"
            }else{
                binding.registerTilEmail.error = null
            }
        }

        binding.registerTieConfirmemail.doOnTextChanged { text, start, before, count ->
            if (!text!!.contains(binding.registerTieEmail.text.toString()) || text.length != binding.registerTieEmail.text!!.length){
                binding.registerTilConfirmemail.error = "E-mails are not same"
            }else{
                binding.registerTilConfirmemail.error = null
                binding.registerTilConfirmemail.setEndIconDrawable(R.drawable.check_ic)
                binding.registerTilConfirmemail.setEndIconTintList(ColorStateList.valueOf(Color.GREEN))
            }
        }

        binding.registerTieUsername.doOnTextChanged { text, start, before, count ->
            if (text!!.length>20){
                binding.registerTilUsername.error = "Too Long!"
            }else if(text.length<6) {
                binding.registerTilUsername.error = "Too Short!"
            }else{
                binding.registerTilUsername.error = null
            }
        }
        binding.registerTiePassword.doOnTextChanged { text, start, before, count ->
            if (text!!.length>20){
                binding.registerTilPassword.error = "Too Long!"
            }else if(text.length<6) {
                binding.registerTilPassword.error = null
                binding.registerTilPassword.setEndIconDrawable(R.drawable.check_ic)
                binding.registerTilPassword.setEndIconTintList(ColorStateList.valueOf(Color.GRAY))
            }else{
                binding.registerTilPassword.error = null
                binding.registerTilPassword.setEndIconDrawable(R.drawable.check_ic)
                binding.registerTilPassword.setEndIconTintList(ColorStateList.valueOf(Color.GREEN))
            }
        }
        binding.registerTiePhone.doOnTextChanged { text, start, before, count ->
            if (text!!.length>20){
                binding.registerTilPhone.error = "Too Long!"
            }else if(text.length<6) {
                binding.registerTilPhone.error = "Too Short!"
            }else{
                binding.registerTilPhone.error = null
            }
        }
        binding.registerTieCity.doOnTextChanged { text, start, before, count ->
            if (text!!.length>20){
                binding.registerTilCity.error = "Too Long!"
            }else if(text.length<3) {
                binding.registerTilCity.error = "Too Short!"
            }else{
                binding.registerTilCity.error = null
            }
        }
        binding.registerTieCountry.doOnTextChanged { text, start, before, count ->
            if (text!!.length>20){
                binding.registerTilCountry.error = "Too Long!"
            }else if(text.length<3) {
                binding.registerTilCountry.error = "Too Short!"
            }else{
                binding.registerTilCountry.error = null
            }
        }
        binding.registerTieAddressline.doOnTextChanged { text, start, before, count ->
            if (text!!.length>160){
                binding.registerTilAddressline.error = "Too Long!"
            }else{
                binding.registerTilAddressline.error = null
            }
        }
        binding.registerTieSecureanswer.doOnTextChanged { text, start, before, count ->
            if (text!!.length>20){
                binding.registerTilSecureanswer.error = "Too Long!"
            }else if(text.length<3) {
                binding.registerTilSecureanswer.error = "Too Short!"
            }else{
                binding.registerTilSecureanswer.error = null
            }
        }
        binding.registerTieFirstname.doOnTextChanged { text, start, before, count ->
            if (text!!.length>20){
                binding.registerTilFirstname.error = "Too Long!"
            }else if(text.length<3) {
                binding.registerTilFirstname.error = "Too Short!"
            }else{
                binding.registerTilFirstname.error = null
            }
        }
        binding.registerTieLastname.doOnTextChanged { text, start, before, count ->
            if (text!!.length>12){
                binding.registerTilLastname.error = "Too Long!"
            }else if(text.length<3) {
                binding.registerTilLastname.error = "Too Short!"
            }else{
                binding.registerTilLastname.error = null
            }
        }

        binding.registerButton.setOnClickListener {
            binding.apply {
                if (registerTieUsername.text.toString().isNotEmpty()
                    && registerTiePassword.text.toString().isNotEmpty()
                    && registerTieEmail.text.toString().isNotEmpty()
                    && registerTieCity.text.toString().isNotEmpty()
                    && registerTieCountry.text.toString().isNotEmpty()
                    && registerTiePhone.text.toString().isNotEmpty()
                    && registerTieFirstname.text.toString().isNotEmpty()
                    && registerTieLastname.text.toString().isNotEmpty()
                ){
                    _firstname = registerTieFirstname.text.toString()
                    _lastname = registerTieLastname.text.toString()
                    _username = registerTieUsername.text.toString()
                    _password = registerTiePassword.text.toString()
                    _email = registerTieEmail.text.toString()
                    _city = registerTieCity.text.toString()
                    _country = registerTieCountry.text.toString()
                    _address = registerTieAddressline.text.toString()
                    _phone = registerTiePhone.text.toString()
                    _secureAnswer = registerTieSecureanswer.text.toString()
                }
                Log.e("Inputs: ",_username+_password+_email+_city+_country+_firstname+_lastname+_address+_phone+_secureAnswer)
            }
            lifecycleScope.launch{
                val user = UserDetails(_firstname,_lastname,_username,_password,_email,_phone,_city,_country,_address,_secureAnswer)
                viewModel.registerUser(user)
            }
            Toast.makeText(requireContext(),"Successfully registered.",Toast.LENGTH_SHORT).show()
            findNavController().navigateUp()
        }

        binding.registerBackbutton.setOnClickListener {
            findNavController().navigateUp()
        }

    }
}