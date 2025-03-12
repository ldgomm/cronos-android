package com.premierdarkcoffee.sales.cronos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import com.premierdarkcoffee.sales.cronos.feature.product.presentation.product.viewmodel.storeApiKey
import com.premierdarkcoffee.sales.cronos.navigation.AuthenticationRoute
import com.premierdarkcoffee.sales.cronos.navigation.NavigationGraph
import com.premierdarkcoffee.sales.cronos.navigation.ProductsRoute
import com.premierdarkcoffee.sales.cronos.util.helper.SecurePreferencesHelper
import com.premierdarkcoffee.sales.cronos.util.ui.theme.CronosTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var networkMonitor: NetworkMonitor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val apiKey = BuildConfig.API_KEY
        storeApiKey(this, apiKey)

        WindowCompat.setDecorFitsSystemWindows(window, false)
        enableEdgeToEdge()
        networkMonitor = NetworkMonitor(this)

        setContent {
            val context = LocalContext.current
            var bearer: String? by remember { mutableStateOf("") }
            LaunchedEffect(Unit) {
                SecurePreferencesHelper.getApiKey(context).let {
                    bearer = it
                }
            }
            val navController = rememberNavController()
            val startDestination = if (bearer != null && bearer?.isNotEmpty() == true) ProductsRoute else AuthenticationRoute
            val isConnected by networkMonitor.observeAsState(true)

            CronosTheme {
                NavigationGraph(navController, startDestination)
//                when {
//                    !isConnected -> NoInternetView()
//                    isConnected -> NavigationGraph(navController, startDestination)
//                    else -> {
//                        UnstableConnectionView()
//                    }
//                }
            }
        }
    }
}
