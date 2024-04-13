package com.sartor.ui.fragments.brand_screens

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.google.gson.Gson
import com.sartor.R
import com.sartor.data.api.response.BrandResponse
import com.sartor.data.api.response.ProductResponse
import com.sartor.data.db.SharedPreference
import com.sartor.data.model.Brands

import com.sartor.databinding.FragmentBrandsShowBinding
import com.sartor.ui.fragments.blog_screens.BlogViewModel
import com.sartor.util.constants.placeImage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BrandShowFragment : Fragment() {


    var binding: FragmentBrandsShowBinding? = null
    private val bViewModel: BlogViewModel by viewModels()

    private val args: BrandShowFragmentArgs by navArgs()

    private lateinit var sharedPreference: SharedPreference
    lateinit var sp: SharedPreference
    lateinit var data: ProductResponse
    var playing = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        sharedPreference = SharedPreference(requireContext())

        // Inflate the layout for this fragment
        binding = FragmentBrandsShowBinding.inflate(inflater)

        setData()
        var follow = false
        binding?.btnFollow?.setOnClickListener {
            if(follow){
                follow = false
                binding?.btnFollow?.setImageResource(R.drawable.ic_follow_adds_white)
            }else{
                follow = true
                binding?.btnFollow?.setImageResource(R.drawable.ic_follow_checks_white)
            }

        }

        binding?.imgBrandShow?.setOnClickListener {
            Navigation.findNavController(requireActivity(), R.id.homeHost).navigateUp()
        }

        sp = context?.let { SharedPreference(it) }!!



        binding?.video?.setOnClickListener {
            Navigation.findNavController(requireActivity(), R.id.homeHost).navigateUp()
           /* if (playing) {
                binding?.video?.pause()
                binding?.playButton?.visibility = View.VISIBLE
                playing = false
            } else {
                binding?.video?.start()
                binding?.playButton?.visibility = View.GONE
                playing = true
            }*/
            binding?.video?.clipToOutline = false
        }
        setVideo()

        return binding?.root
    }
    private fun setVideo() {

        val data = Gson().fromJson(
            sp.get("intent"),
            ProductResponse::class.java
        )
//        val url=data.img.img0;
//        val uri = Uri.parse(Constant.BASE_URL+data.img.img0)
//        val mediaController = MediaController(context)
//        mediaController.setAnchorView(binding?.video)
//        binding?.video?.setMediaController(mediaController)
//        binding?.video?.setVideoURI(Uri.parse(url))
//        binding?.video?.start()
        val uri = Uri.parse("android.resource://" + activity?.packageName )//+ "/" + R.raw.intro)
        binding?.video?.setVideoURI(uri)


        if (playing) {
            binding?.video?.clipToOutline = true
            binding?.video?.start()

        }


    }
    private fun setData() {
        var obj = args.brandID

        val brand = Gson().fromJson(
            obj,
            Brands::class.java
        )
        if (brand.brandID != null) {

            sharedPreference.save("brandId", brand.brandID)
            binding?.brandName?.text = brand.title
            //binding?.tvLikesCounter?.text = brand.likes.size.toString()
            binding?.imgBrandShow?.placeImage(brand.image)
        } else {

            val brands = Gson().fromJson(
                obj,
                BrandResponse::class.java
            )

            sharedPreference.save("brandId", brands.id)
            binding?.brandName?.text = brands.title
            //binding?.tvLikesCounter?.text = brands.likes.size.toString()
            binding?.imgLogoShow?.placeImage(brands.img)

        }
    }


}