package com.prashant.naik.ezcart

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.prashant.naik.ezcart.databinding.FragmentRegistrationBinding

class RegistrationFragment : Fragment() {

    lateinit var binding : FragmentRegistrationBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_registration, container, false)

        binding.registerButton.setOnClickListener {
            findNavController().navigate(RegistrationFragmentDirections.actionRegistrationFragmentToHomeFragment())
        }

        // Inflate the layout for this fragment
        return binding.root
    }
}