package com.premierdarkcoffee.sales.cronos.navigation

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.premierdarkcoffee.sales.cronos.feature.product.presentation.product.view.product.ProductsView
import com.premierdarkcoffee.sales.cronos.feature.product.presentation.product.viewmodel.ProductViewModel
import com.premierdarkcoffee.sales.cronos.navigation.ProductsRoute
import com.premierdarkcoffee.sales.cronos.util.function.sharedViewModel

fun NavGraphBuilder.productsRoute(
    navController: NavHostController,
    onNavigateToProductView: (String) -> Unit,
    onAddNewProductButtonClicked: () -> Unit,
    onNavigateToSettingsButtonClicked: () -> Unit
) {
    composable<ProductsRoute> { backStackEntry ->
        val viewModel = backStackEntry.sharedViewModel<ProductViewModel>(navController = navController)
        val productState by viewModel.productsState.collectAsState()
        val searchText by viewModel.searchText.collectAsState()

        ProductsView(
            productsState = productState,
            searchText = searchText,
            onSearchTextChange = viewModel::onSearchTextChange,
            clearSearchText = { viewModel.clearSearchText() },
            onNavigateToProductView = onNavigateToProductView,
            onAddNewProductButtonClicked = onAddNewProductButtonClicked,
            onNavigateToSettingsButtonClicked = onNavigateToSettingsButtonClicked,
            onRefresh = viewModel::onRefresh
        )
    }
}
