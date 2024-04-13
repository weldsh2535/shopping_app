package com.sartor.ui.fragments.other

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.sartor.R
import com.sartor.databinding.FragmentMessageBinding
import com.sartor.databinding.FragmentNotificationBinding

class MessageFragment : Fragment() {

    var binding: FragmentMessageBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMessageBinding.inflate(inflater)

        binding?.ivBack?.setOnClickListener {
            Navigation.findNavController(requireActivity(), R.id.homeHost).navigateUp()
        }

        binding?.tvBack?.setOnClickListener {
            Navigation.findNavController(requireActivity(), R.id.homeHost).navigateUp()
        }

        return binding?.root
    }
}