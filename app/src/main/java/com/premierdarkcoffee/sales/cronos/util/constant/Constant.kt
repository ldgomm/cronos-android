package com.premierdarkcoffee.sales.sales.util.constant

import androidx.compose.ui.unit.dp

object Constant {
    const val clientId: String = "495878670818-bud1damggt69ge5hv1eo8gqdg4c1efph.apps.googleusercontent.com"

    val four = 4.dp
    val six = 6.dp
    val eleven = 11.dp

    private const val productRouteDestination = "com.premierdarkcoffee.sales.maia.root.navigation.ProductRoute/{product}"
    private const val addEditProductRouteDestination =
        "com.premierdarkcoffee.sales.maia.root.navigation.AddEditProductRoute/{product}"
    private const val conversationRouteDestination =
        "com.premierdarkcoffee.sales.maia.root.navigation.ConversationRoute?clientId={clientId}"

    val ignore = listOf(productRouteDestination, addEditProductRouteDestination, conversationRouteDestination)
}
