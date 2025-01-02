package com.premierdarkcoffee.sales.cronos.navigation

//
//  Routes.kt
//  Sales
//
//  Created by José Ruiz on 18/10/24.
//

import kotlinx.serialization.Serializable

@Serializable
data object AuthenticationRoute

@Serializable
data object ProductsRoute

@Serializable
data class ProductRoute(val product: String)

@Serializable
data class AddEditProductRoute(val product: String?)

@Serializable
data object SettingsRoute

@Serializable
object AccountDeletionRoute

@Serializable
object TermsOfUseRoute

@Serializable
object PrivacyPolicyRoute

