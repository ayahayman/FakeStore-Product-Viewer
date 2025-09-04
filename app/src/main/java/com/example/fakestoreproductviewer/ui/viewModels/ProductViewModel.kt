package com.example.fakestoreproductviewer.ui.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fakestoreproductviewer.data.Product
import com.example.fakestoreproductviewer.networking.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProductViewModel : ViewModel() {
    private val _productsState = MutableStateFlow<List<Product>>(emptyList())
    val productsState: StateFlow<List<Product>> = _productsState.asStateFlow()

    private val apiService = RetrofitClient.apiService

    fun loadProducts() {
        viewModelScope.launch {
            try {
                val products = apiService.getProducts()
                _productsState.value = products
            } catch (e: Exception) {
                // Handle error
                Log.e("ProductViewModel", "Error loading products", e)
            }
        }
    }
}