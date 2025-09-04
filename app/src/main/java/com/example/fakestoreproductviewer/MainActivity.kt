package com.example.fakestoreproductviewer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.fakestoreproductviewer.data.Result
import com.example.fakestoreproductviewer.ui.screens.HomeScreen
import com.example.fakestoreproductviewer.ui.screens.ProductDetailsScreen
import com.example.fakestoreproductviewer.ui.theme.FakeStoreProductViewerTheme
import com.example.fakestoreproductviewer.ui.viewModels.ProductViewModel

class MainActivity : ComponentActivity() {

    private val viewModel: ProductViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FakeStoreProductViewerTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = "home"
                ) {
                    composable("home") {
                        HomeScreen(
                            productsState = viewModel.productsState,
                            onProductClick = { product ->
                                navController.navigate("details/${product.id}")
                            }
                        )
                    }
                    composable("details/{productId}") { backStackEntry ->
                        val result by viewModel.productsState.collectAsState()
                        val productId = backStackEntry.arguments?.getString("productId")?.toIntOrNull()
                        val product = when (result) {
                            is Result.Success -> {
                                val products = (result as Result.Success).data
                                productId?.let { id -> products.find { it.id == id } }
                            }
                            else -> null
                        }
                        ProductDetailsScreen(product = product)
                    }
                }
            }
        }
        viewModel.loadProducts()
    }
}