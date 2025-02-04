package com.premierdarkcoffee.sales.cronos.navigation.route.settings

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.premierdarkcoffee.sales.cronos.feature.product.presentation.product.view.settings.SettingsView
import com.premierdarkcoffee.sales.cronos.navigation.SettingsRoute

fun NavGraphBuilder.settingsRoute(onNavigateToPrivacyPolicyButtonClicked: () -> Unit,
                                  onNavigateToTermsOfUseButtonClicked: () -> Unit,
                                  onNavigateToAccountDeletionButtonClicked: () -> Unit) {
    composable<SettingsRoute> {

        SettingsView(onNavigateToPrivacyPolicyButtonClicked,
                     onNavigateToTermsOfUseButtonClicked,
                     onNavigateToAccountDeletionButtonClicked)
    }
}
