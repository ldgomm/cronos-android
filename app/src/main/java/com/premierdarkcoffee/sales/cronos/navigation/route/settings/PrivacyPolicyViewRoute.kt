package com.premierdarkcoffee.sales.cronos.navigation.route.settings

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.premierdarkcoffee.sales.cronos.feature.product.presentation.product.view.settings.PrivacyPolicyView
import com.premierdarkcoffee.sales.cronos.navigation.PrivacyPolicyRoute

fun NavGraphBuilder.privacyPolicyView() {

    composable<PrivacyPolicyRoute> {
        PrivacyPolicyView()
    }
}
