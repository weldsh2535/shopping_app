package com.sartor.ui.fragments.user_profile_screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.sartor.R
import com.sartor.data.model.Currency
import com.sartor.databinding.FragmentCountryBinding


class CountryFragment : Fragment() {

    var binding: FragmentCountryBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentCountryBinding.inflate(inflater)

        binding?.ivBack?.setOnClickListener {
            Navigation.findNavController(requireActivity(), R.id.homeHost).navigateUp()
        }

        binding?.tvBack?.setOnClickListener {
            Navigation.findNavController(requireActivity(), R.id.homeHost).navigateUp()
        }

//        var arrayList = arrayListOf<Currency>(
//            Currency("$","Device","Default"),
//            Currency("AU$","Australian","AUD"),
//            Currency("CA$","Canada","CAD"),
//            Currency("US$","United State","USD"),
//            Currency("Rp","Indonesia","IDR"),
//        )
//
//        var adapter: CurrencyAdapter = CurrencyAdapter(context, arrayList)

            //binding?.listCountry?.adapter = adapter

        return binding?.root
    }


}