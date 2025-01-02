package com.premierdarkcoffee.sales.cronos.navigation.route.settings

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.premierdarkcoffee.sales.cronos.feature.product.presentation.product.view.settings.AccountDeletionView
import com.premierdarkcoffee.sales.cronos.navigation.AccountDeletionRoute

fun NavGraphBuilder.accountDeletionRoute() {

    composable<AccountDeletionRoute> {
        AccountDeletionView()
    }
}
