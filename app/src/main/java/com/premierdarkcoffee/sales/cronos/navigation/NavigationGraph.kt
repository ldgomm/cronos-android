package com.premierdarkcoffee.sales.cronos.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.premierdarkcoffee.sales.cronos.navigation.route.settings.accountDeletionRoute
import com.premierdarkcoffee.sales.cronos.navigation.route.settings.privacyPolicyView
import com.premierdarkcoffee.sales.cronos.navigation.route.settings.settingsRoute
import com.premierdarkcoffee.sales.cronos.navigation.route.settings.termsOfUseRoute

@Composable
fun NavigationGraph(
    navController: NavHostController,
    startDestination: Any
) {

    NavHost(navController = navController, startDestination = startDestination) {

        authenticationRoute {
            navController.popBackStack()
            navController.navigate(ProductsRoute)
        }

        productsRoute(navController, onNavigateToProductView = { productJson: String ->
            navController.navigate(ProductRoute(productJson))
        }, onAddNewProductButtonClicked = {
            navController.navigate(AddEditProductRoute(product = null))
        }, onNavigateToSettingsButtonClicked = {
            navController.navigate(SettingsRoute)
        })

        productRoute(navController,
                     onPopBackStackActionTriggered = { navController.popBackStack() },
                     onAddOrUpdateEditedProductButtonClick = {
                         navController.navigate(AddEditProductRoute(it))
                     })

        addEditProductRoute(navController = navController, onBackToProductsActionTriggered = {
            navController.navigate(ProductsRoute) {
                popUpTo(ProductsRoute) { inclusive = true }
            }
        })

        settingsRoute(
            onNavigateToPrivacyPolicyButtonClicked = { navController.navigate(PrivacyPolicyRoute) },
            onNavigateToTermsOfUseButtonClicked = { navController.navigate(TermsOfUseRoute) },
            onNavigateToAccountDeletionButtonClicked = { navController.navigate(AccountDeletionRoute) },
        )

        accountDeletionRoute()
        privacyPolicyView()
        termsOfUseRoute()
    }
}
