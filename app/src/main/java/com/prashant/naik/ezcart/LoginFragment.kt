package com.prashant.naik.ezcart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.prashant.naik.ezcart.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    lateinit var binding : FragmentLoginBinding

    // Uncomment to make the toolbar invisible
    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
    }

    override fun onStop() {
        super.onStop()
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        binding.signUpTextView.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToRegistrationFragment())
        }

        binding.loginButton.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToHomeFragment())
        }

        // Inflate the layout for this fragment
        return binding.root
    }

}