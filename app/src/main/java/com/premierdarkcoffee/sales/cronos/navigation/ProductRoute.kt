package com.premierdarkcoffee.sales.cronos.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.google.gson.Gson
import com.premierdarkcoffee.sales.cronos.feature.product.data.remote.dto.ProductDto
import com.premierdarkcoffee.sales.cronos.feature.product.presentation.product.view.product.ProductView
import com.premierdarkcoffee.sales.cronos.feature.product.presentation.product.viewmodel.ProductViewModel
import com.premierdarkcoffee.sales.cronos.util.function.sharedViewModel
import com.premierdarkcoffee.sales.cronos.util.helper.SecurePreferencesHelper

fun NavGraphBuilder.productRoute(navController: NavHostController,
                                 onPopBackStackActionTriggered: () -> Unit,
                                 onAddOrUpdateEditedProductButtonClick: (String) -> Unit) {
    composable<ProductRoute> { backStackEntry ->
        val viewModel = backStackEntry.sharedViewModel<ProductViewModel>(navController = navController)
        val args = backStackEntry.toRoute<ProductRoute>()
        val product = Gson().fromJson(args.product, ProductDto::class.java).toProduct()


        val context = LocalContext.current
        var token: String by remember { mutableStateOf("") }
        LaunchedEffect(Unit) {
            SecurePreferencesHelper.getApiKey(context)?.let {
                token = it
                viewModel.initData(it)
            }
        }

        ProductView(product = product,
                    onPopBackStackActionTriggered = onPopBackStackActionTriggered,
                    onAddOrUpdateEditedProductButtonClick = onAddOrUpdateEditedProductButtonClick)
    }
}
