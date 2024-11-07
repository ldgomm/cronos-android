package com.premierdarkcoffee.sales.cronos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import com.premierdarkcoffee.sales.cronos.feature.product.presentation.product.viewmodel.storeApiKey
import com.premierdarkcoffee.sales.cronos.navigation.NavigationGraph
import com.premierdarkcoffee.sales.cronos.navigation.ProductsRoute
import com.premierdarkcoffee.sales.cronos.util.ui.theme.CronosTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val apiKey = BuildConfig.API_KEY
        storeApiKey(this, apiKey)

        enableEdgeToEdge()
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            val navController = rememberNavController()
            val startDestination = ProductsRoute
            CronosTheme {
                NavigationGraph(navController, startDestination)
            }
        }
    }
}
