package com.premierdarkcoffee.sales.cronos.navigation.route.settings

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.premierdarkcoffee.sales.cronos.feature.product.presentation.product.view.settings.TermsOfUseView
import com.premierdarkcoffee.sales.cronos.navigation.TermsOfUseRoute

fun NavGraphBuilder.termsOfUseRoute() {

    composable<TermsOfUseRoute> {
        TermsOfUseView()
    }
}
