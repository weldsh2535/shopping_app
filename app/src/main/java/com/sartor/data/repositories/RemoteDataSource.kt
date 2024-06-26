package com.sartor.data.repositories

import com.google.gson.JsonObject
import com.sartor.data.api.OperationCallback
import com.sartor.data.api.request.*
import com.sartor.data.api.response.*
import com.sartor.data.model.Cart

interface RemoteDataSource {
    suspend fun loginUser(loginRequest: LoginRequest, callback: OperationCallback<LoginResponse>)
    suspend fun registerUser(signUpRequest: SignUpRequest, callback: OperationCallback<SignUpResponse>)
    suspend fun getAllBlogs( callback: OperationCallback<List<BlogResponse>>)
    suspend fun getBlog(id : String , callback: OperationCallback<BlogResponse>)
    suspend fun getProducts( callback: OperationCallback<List<ProductResponse>>)
    suspend fun getProduct(id : String , callback: OperationCallback<ProductResponse>)

   // suspend fun addToCart(addToCartRequest: AddToCartRequest, callback: OperationCallback<AddCartResponse> )
    suspend fun getCart( callback: OperationCallback<CartResponse> )
    //suspend fun getBrands( callback: OperationCallback<List<BrandResponse>> )
    suspend fun makePayment(checkoutRequest: CheckoutRequest, callback: OperationCallback<CheckoutResponse> )

    suspend fun logOut( )

}