package com.premierdarkcoffee.sales.cronos.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.premierdarkcoffee.sales.cronos.feature.authentication.presentation.view.AuthenticationView

fun NavGraphBuilder.authenticationRoute(onSubmitApiKeyButtonClicked: () -> Unit) {
    composable<AuthenticationRoute> {
        AuthenticationView(onSubmitApiKeyButtonClicked)
    }
}
