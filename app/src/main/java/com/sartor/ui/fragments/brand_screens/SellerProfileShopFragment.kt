package com.sartor.ui.fragments.brand_screens

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.android.volley.AuthFailureError
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.sartor.data.api.ApiUtils
import com.sartor.data.api.response.ProductResponse
import com.sartor.data.api.response.ReviewResponse
import com.sartor.data.db.SharedPreference
import com.sartor.data.model.Product
import com.sartor.data.model.myModels.MyProduct
import com.sartor.databinding.FragmentSellerProfileShopBinding
import com.sartor.ui.adapters.ProductsAdapter2
import com.sartor.util.SpacingForRecyclerChild
import com.sartor.util.constants.Constant
import com.sartor.util.default_assets.productsData
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import java.util.*


class SellerProfileShopFragment : Fragment() {

    var binding: FragmentSellerProfileShopBinding? = null
    private lateinit var sharedPreference: SharedPreference

    private var mRequestQueue: RequestQueue? = null
    private var mStringRequest: StringRequest? = null
    private var brandId:String? = null
    lateinit var products: List<ProductResponse>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        sharedPreference= SharedPreference(requireContext())
        // Inflate the layout for this fragment
        binding = FragmentSellerProfileShopBinding.inflate(inflater)
        brandId = sharedPreference.get("brandId")

        //sendAndRequestResponse()
        //LOAD DATA FROM DEFAULT ASSET
        getData()

        return binding?.root
    }

    private fun getData() {
        binding?.progressBar?.visibility = View.VISIBLE
        // GlobalScope.launch {
        val jsonObject = JsonObject()
        jsonObject.addProperty("brands", brandId)
        // val repositoryImpl = ApiUtils().Service(requireContext())
        val fetchApi = ApiUtils().client(requireContext()).brandProduct(jsonObject)
        fetchApi.enqueue(object : Callback<List<ProductResponse>> {
            override fun onResponse(
                call: Call<List<ProductResponse>>,
                response: retrofit2.Response<List<ProductResponse>>
            ) {
                if (response.isSuccessful) {
                    binding?.progressBar?.visibility = View.GONE
                    products = response.body()!!
                }
                Log.e("REVIEW::", response.code().toString())
                setAdapter(products!!)
            }

            override fun onFailure(call: Call<List<ProductResponse>>, t: Throwable) {
                Log.e("REVIEW::", t.toString())
                Toast.makeText(requireContext(), "Fail to get the data..", Toast.LENGTH_SHORT)
                    .show()
            }
        })
        binding?.progressBar?.visibility = View.GONE
        //}
    }
    /*private fun sendAndRequestResponse() {
        val token = sharedPreference.getToken()
        val brandId = sharedPreference.get("brandId")

        val url = Constant.BASE_URL + "api/brands/products/" + brandId
        mRequestQueue = Volley.newRequestQueue(context)
        mStringRequest = object : StringRequest(
            Method.GET, url,
            Response.Listener { response ->


                var obj = JSONObject(response)

                var product = Gson().fromJson(obj.toString(), MyProduct::class.java)

                if(product!=null&&obj.length()>0){

                    val list = ArrayList<Product>()

                list.add(
                    Product(
                        product.name,
                        product.price.toString(),
                        product.store,
                        product.img.img1,
                        product._id
                    )
                )

                setAdapter(list)
                }
                binding?.progressBar?.visibility=View.GONE

            },
            Response.ErrorListener { error ->
                Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()
            }) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headerMap: MutableMap<String, String> = HashMap()
                headerMap["Content-Type"] = "application/json"
                headerMap["Authorization"] = "Bearer $token"
                return headerMap
            }
        }

        mRequestQueue?.add<String>(mStringRequest)
    }*/

    private fun setAdapter(products: List<ProductResponse>) {

        binding?.rvSellerShop?.layoutManager = GridLayoutManager(this.requireContext(), 2)
        binding?.rvSellerShop?.adapter = ProductsAdapter2(products, this.requireContext())
        binding?.rvSellerShop?.addItemDecoration(SpacingForRecyclerChild(16))
        binding?.rvSellerShop?.setHasFixedSize(true)

    }
}