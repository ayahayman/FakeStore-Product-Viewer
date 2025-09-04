package com.example.fakestoreproductviewer.ui.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fakestoreproductviewer.data.Product
import com.example.fakestoreproductviewer.data.Result
import com.example.fakestoreproductviewer.networking.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProductViewModel : ViewModel() {
    private val _productsState = MutableStateFlow<Result<List<Product>>>(Result.Loading)
    val productsState: StateFlow<Result<List<Product>>> = _productsState.asStateFlow()

    private val apiService = RetrofitClient.apiService

    fun loadProducts() {
        viewModelScope.launch {
            _productsState.value = Result.Loading
            try {
                val products = apiService.getProducts()
                _productsState.value = Result.Success(products)
            } catch (e: Exception) {
                _productsState.value = Result.Error(e)
                Log.e("ProductViewModel", "Error loading products", e)
            }
        }
    }
}