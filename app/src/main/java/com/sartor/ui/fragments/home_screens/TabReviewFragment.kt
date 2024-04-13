package com.sartor.ui.fragments.home_screens

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.PermissionChecker.checkSelfPermission
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.JsonObject
import com.sartor.data.api.ApiUtils
import com.sartor.data.api.request.ReviewRequest
import com.sartor.data.api.response.*
import com.sartor.data.db.SharedPreference
import com.sartor.databinding.FragmentTabReviewBinding
import com.sartor.ui.adapters.ReviewAdapter
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File


class TabReviewFragment : Fragment() {

    lateinit var sp: SharedPreference
    var binding: FragmentTabReviewBinding? = null
    lateinit var pId: String

    var r1 = 0
    var r2 = 0
    var r3 = 0
    var r4 = 0
    var r5 = 0
    lateinit var reviews: List<ReviewResponse>

    //image pick code
    private val IMAGE_PICK_CODE = 1000

    //Permission code
    private val PERMISSION_CODE = 1001
    private var imageData: Uri? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        sp = context?.let { SharedPreference(it) }!!

        pId = sp.get("_pid")!!

        binding = FragmentTabReviewBinding.inflate(inflater)!!

        getReview()
        binding?.takePic?.setOnClickListener() {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(
                        requireContext(),
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    ) ==
                    PackageManager.PERMISSION_DENIED
                ) {
                    //permission denied
                    val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                    //show popup to request runtime permission
                    requestPermissions(permissions, PERMISSION_CODE);
                } else {
                    //permission already granted
                    pickImageFromGallery()
                }
            } else {
                //system OS is < Marshmallow
                pickImageFromGallery()
            }
        }
        binding?.addReview?.setOnClickListener() {

            addReview(binding?.reviewInput?.text.toString())
        }

        return binding?.root
    }

    fun pickImageFromGallery() {
        //Intent to pick image
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSION_CODE -> {
                if (grantResults.size > 0 && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED
                ) {
                    //permission from popup granted
                    pickImageFromGallery()
                } else {
                    //permission from popup denied
                    Toast.makeText(requireContext(), "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            imageData = data?.data!!

        }
    }


    private fun getReview() {
        binding?.progressBar?.visibility = View.VISIBLE
        // GlobalScope.launch {
        val jsonObject = JsonObject()
        jsonObject.addProperty("product", pId)
        // val repositoryImpl = ApiUtils().Service(requireContext())
        val fetchApi = ApiUtils().client(requireContext()).searchReview(jsonObject)
        fetchApi.enqueue(object : Callback<List<ReviewResponse>> {
            override fun onResponse(
                call: Call<List<ReviewResponse>>,
                response: Response<List<ReviewResponse>>
            ) {
                if (response.isSuccessful) {
                    binding?.progressBar?.visibility = View.GONE
                    reviews = response.body()!!
                }
                Log.e("REVIEW::", response.code().toString())
                setAdapter(reviews!!)
            }

            override fun onFailure(call: Call<List<ReviewResponse>>, t: Throwable) {
                Log.e("REVIEW::", t.toString())
                Toast.makeText(requireContext(), "Fail to get the data..", Toast.LENGTH_SHORT)
                    .show()
            }
        })
        binding?.progressBar?.visibility = View.GONE
        //}
    }

    private fun addReview(review: String) {

        //GlobalScope.launch {
        if(imageData == null){
            return Toast.makeText(requireContext(), "Add Image..", Toast.LENGTH_SHORT)
                .show()
        }
        binding?.progressBar?.visibility = View.VISIBLE
        // create RequestBody instance from file
        val file: File = File(imageData.toString())
        val multipartBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("product", pId)
            .addFormDataPart("review", review)
            .addFormDataPart("rate", "4")
            .addFormDataPart("img", file.name, file.path.toRequestBody("image/*".toMediaTypeOrNull()))
            .build()
        val reqFile: RequestBody = RequestBody.create("image/*".toMediaTypeOrNull(), file)
        // create RequestBody instance from file
        val imageFile: MultipartBody.Part  = MultipartBody.Part.createFormData("image", file.name, reqFile)

            val postData = ReviewRequest(pId, review, "4", imageFile)

            // val repositoryImpl = ApiUtils().Service(requireContext())
            val fetchApi = ApiUtils().client(requireContext()).addReview(multipartBody)
            fetchApi.enqueue(object : Callback<ReviewResponse> {
                override fun onResponse(
                    call: Call<ReviewResponse>,
                    response: Response<ReviewResponse>
                ) {
                    if (response.isSuccessful) {
                        binding?.progressBar?.visibility = View.GONE
                        Toast.makeText(requireContext(), "Success Add Review..", Toast.LENGTH_SHORT)
                            .show()
                    }
                    Log.e("ADD REVIEW::", response.code().toString())
                    getReview()
                    // setAdapter(reviews!!)
                }

                override fun onFailure(call: Call<ReviewResponse>, t: Throwable) {
                    Log.e("ADD REVIEW::", t.toString())
                    Toast.makeText(requireContext(), "Fail to Add Review..", Toast.LENGTH_SHORT)
                        .show()
                }
            })
        binding?.progressBar?.visibility = View.GONE
        //}
    }

    private fun setAdapter(list: List<ReviewResponse>) {

        binding?.rvReview?.layoutManager = LinearLayoutManager(this.requireContext())!!
        binding?.rvReview?.adapter = ReviewAdapter(list, this.requireContext())
        binding?.rvReview?.setHasFixedSize(true)

        if (list == null) {
            binding?.tvNoReviews?.visibility = View.VISIBLE
        }

    }


    private fun setProgressbars() {


        binding?.tv1?.text = r1.toString()
        binding?.tv2?.text = r2.toString()
        binding?.tv3?.text = r3.toString()
        binding?.tv4?.text = r4.toString()
        binding?.tv5?.text = r5.toString()


        var total = (r1 + r2 + r3 + r4 + r5).toDouble()


        var a = 1.0
        var b = 2.0

        var p = (r3.toDouble() / total.toDouble()) * 100



        Log.d("sdjlask", "$r3--$total---$p")

        binding?.progressBar1?.progress = ((r1 / total) * 100).toInt()!!
        binding?.progressBar2?.progress = ((r2 / total) * 100).toInt()
        binding?.progressBar3?.progress = ((r3 / total) * 100).toInt()
        binding?.progressBar4?.progress = ((r4 / total) * 100).toInt()
        binding?.progressBar5?.progress = ((r5 / total) * 100).toInt()

    }

}