package com.prashant.naik.ezcart.ui.help

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.prashant.naik.ezcart.R
import com.prashant.naik.ezcart.databinding.FragmentHelpBinding

class HelpFragment : Fragment() {

    companion object {
        const val HELP_URL = "https://developer.android.com/guide/webapps/webview"
    }

    lateinit var binding : FragmentHelpBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_help, container, false)

        binding.webview.loadUrl(HELP_URL)

        // Inflate the layout for this fragment
        return binding.root
    }
}