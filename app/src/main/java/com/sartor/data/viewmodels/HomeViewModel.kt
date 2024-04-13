package com.sartor.data.viewmodels

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sartor.data.api.ApiUtils
import com.sartor.data.api.OperationCallback
import com.sartor.data.api.response.BlogResponse
import com.sartor.data.api.response.BrandResponse
import com.sartor.data.api.response.ProductResponse
import com.sartor.data.api.response.ReviewResponse
import com.sartor.data.repositories.RepositoryImpl

import com.sartor.util.default_assets.productsData
import com.sartor.util.default_assets.reviews
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repositoryImpl: RepositoryImpl) : ViewModel() {


    private val _isViewLoading = MutableLiveData<Boolean>()
    val isViewLoading: LiveData<Boolean> = _isViewLoading

    private val _onMessageError = MutableLiveData<Any>()
    val onMessageError: LiveData<Any> = _onMessageError

    private val _listOfProducts = MutableLiveData<List<ProductResponse>>()
    val listOfProducts : LiveData<List<ProductResponse>> = _listOfProducts

    private val _listOfBrands = MutableLiveData<List<BrandResponse>>()
    val listOfBrands : LiveData<List<BrandResponse>> = _listOfBrands

    private val _product = MutableLiveData<ProductResponse>()
    val product : LiveData<ProductResponse> = _product



    fun getProductList(){
        _isViewLoading.value = true
        //LOAD DATA FROM DEFAULT ASSET
        /*_isViewLoading.postValue(false)
        _listOfProducts.postValue(products)*/

        //Online API
        CoroutineScope(Dispatchers.IO).launch {
            
            repositoryImpl.getProducts(object : OperationCallback<List<ProductResponse>> {
                override fun onSuccess(data: List<ProductResponse>?) {

                    print("LIST RESPONSE::"+data);
                    _listOfProducts.postValue(data!!)
                    _isViewLoading.postValue(false)
                }
                override fun onError(error: String?) {
                    _onMessageError.postValue(error!!)
                    _isViewLoading.postValue(false)
                }
            })
        } //DISABLE BACKEND APP CRASH
    }
    fun getBrandList(context:Context){
        _isViewLoading.value = true
        //LOAD DATA FROM DEFAULT ASSET
       // _isViewLoading.postValue(false)
       // _listOfProducts.postValue(products)*/

        //Online API
        val fetchApi = ApiUtils().client(context).getAllBrands()
        fetchApi.enqueue(object : Callback<List<BrandResponse>> {
            override fun onResponse(
                call: Call<List<BrandResponse>>,
                response: Response<List<BrandResponse>>
            ) {
                if (response.isSuccessful) {
                    val listData = response.body()

                    _listOfBrands.postValue(listData?.toMutableList())
                    _isViewLoading.postValue(false)
                }


            }

            override fun onFailure(call: Call<List<BrandResponse>>, error: Throwable) {
                Log.e("REVIEW::", error.toString())
                _onMessageError.postValue(error)
                _isViewLoading.postValue(false)
            }
        })
    }

    fun getProduct(id : String ){
        _isViewLoading.value = true

        CoroutineScope(Dispatchers.IO).launch {

            repositoryImpl.getProduct(id, object: OperationCallback<ProductResponse>{
                override fun onSuccess(data: ProductResponse?) {
                    _isViewLoading.postValue(false)
                    _product.postValue(data!! )
                }

                override fun onError(error: String?) {
                    _onMessageError.postValue(error!!)
                    _isViewLoading.postValue(false)
                }

            })
        } //DISABLE BACKEND MAKING CRASH
    }
}