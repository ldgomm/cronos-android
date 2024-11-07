package com.premierdarkcoffee.sales.cronos.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.google.gson.Gson
import com.premierdarkcoffee.sales.cronos.feature.product.data.remote.dto.ProductDto
import com.premierdarkcoffee.sales.cronos.feature.product.presentation.product.view.product.AddEditProductView
import com.premierdarkcoffee.sales.cronos.feature.product.presentation.product.viewmodel.ProductViewModel
import com.premierdarkcoffee.sales.cronos.util.function.sharedViewModel

fun NavGraphBuilder.addEditProductRoute(
    navController: NavHostController,
    onBackToProductsActionTriggered: () -> Unit
) {
    composable<AddEditProductRoute> { backStackEntry ->
        val viewModel = backStackEntry.sharedViewModel<ProductViewModel>(navController = navController)

        val args = backStackEntry.toRoute<AddEditProductRoute>()

        val addOrUpdateProductState by viewModel.addEditProductState.collectAsState()
        val informationResultStateList by viewModel.informationResultStateList.collectAsState()
//        val categories by viewModel.categories.collectAsState()
        val groups by viewModel.groups.collectAsState()

        val product: ProductDto? = Gson().fromJson(args.product, ProductDto::class.java)

        LaunchedEffect(product) {
            product?.let {
                viewModel.setProduct(product.toProduct())
            }
        }

        AddEditProductView(addEditProductState = addOrUpdateProductState,
                           product = product?.toProduct(),
                           setName = viewModel::setName,
                           setLabel = viewModel::setLabel,
                           setOwner = viewModel::setOwner,
                           setYear = viewModel::setYear,
                           setModel = viewModel::setModel,
                           setDescription = viewModel::setDescription,
                           groups = groups,
                           setCategory = viewModel::setCategory,
                           setPrice = viewModel::setPrice,
                           setStock = viewModel::setStock,
                           setImage = viewModel::setImage,
                           setOrigin = viewModel::setOrigin,
            //overview
                           informationResultStateList = informationResultStateList,
                           addInformationResultState = viewModel::addInformationResultState,
                           addKeyword = viewModel::addKeyword,
                           deleteKeyword = viewModel::deleteKeyword,
            //specifications
            //warranty
                           setLegal = viewModel::setLegal,
                           setWarning = viewModel::setWarning,
                           addProduct = { product ->
                               viewModel.addProduct(
                                   product = product,
                                   onSuccess = onBackToProductsActionTriggered,
                                   onFailure = {})
                           },
                           updateProduct = { product ->
                               viewModel.updateProduct(product, onSuccess = onBackToProductsActionTriggered, onFailure = {})
                           })
    }
}
