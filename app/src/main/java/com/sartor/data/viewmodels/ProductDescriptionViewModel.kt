package com.sartor.data.viewmodels

import android.app.Application
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
import com.sartor.data.api.request.AddToCartRequest
import com.sartor.data.api.response.AddCartResponse
import com.sartor.data.api.response.BlogResponse
import com.sartor.data.api.response.CartResponse
import com.sartor.data.api.response.ReviewResponse
import com.sartor.data.repositories.RepositoryImpl
import dagger.hilt.android.internal.Contexts.getApplication

import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class ProductDescriptionViewModel @Inject constructor(private val repositoryImpl: RepositoryImpl) :ViewModel() {


    private val _isSuccessful = MutableLiveData<Boolean>()
    val isSuccessful: LiveData<Boolean> = _isSuccessful

    private val _isViewLoading = MutableLiveData<Boolean>()
    val isViewLoading: LiveData<Boolean> = _isViewLoading

    private val _onMessage = MutableLiveData<Any>()
    val onMessage: LiveData<Any> = _onMessage


    fun addToCart(context: Context,id : String ){

        _isViewLoading.value = true
        val fetchApi = ApiUtils().client(context).addToCart(AddToCartRequest(id,1))
        fetchApi.enqueue(object : Callback<AddCartResponse> {
            override fun onResponse(
                call: Call<AddCartResponse>,
                response: Response<AddCartResponse>
            ) {
                if (response.isSuccessful) {
                    //val data = response.body()!!


                }
                Log.e("ADD_CART::", response.body().toString())
                _onMessage.postValue("Added to cart" )
                _isSuccessful.postValue(true)
            }

            override fun onFailure(call: Call<AddCartResponse>, error: Throwable) {
                Log.e("ERROR_CART::", error.toString())
                _onMessage.postValue(error)
                _isSuccessful.postValue(false)
            }
        })
        _isViewLoading.postValue(false)
       /* CoroutineScope(Dispatchers.IO).launch {

            repositoryImpl.addToCart(AddToCartRequest(id,1), object: OperationCallback<AddCartResponse> {
                override fun onSuccess(data: AddCartResponse?) {
                    _isViewLoading.postValue(false)
                    _onMessage.postValue("Added to cart " )
                    _isSuccessful.postValue(true)
                }

                override fun onError(error: String?) {
                    _onMessage.postValue(error!!)
                    _isViewLoading.postValue(false)
                    _isSuccessful.postValue(false)
                }

            })
        } *///DISABLE BACKEND APP CRASH
    }
}