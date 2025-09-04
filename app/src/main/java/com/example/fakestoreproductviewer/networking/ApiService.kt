package com.example.fakestoreproductviewer.networking

import com.example.fakestoreproductviewer.data.Product
import retrofit2.http.GET

interface ApiService {
    @GET("products")
    suspend fun getProducts(): List<Product>
}