package com.premierdarkcoffee.sales.cronos.util.function

//
//  shouldShowNavigationBar.kt
//  Maia
//
//  Created by José Ruiz on 14/7/24.
//

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.premierdarkcoffee.sales.sales.util.constant.Constant.ignore

/**
 * Determines if the navigation bar should be shown based on the current destination.
 *
 * This function checks the current navigation route and returns a boolean indicating whether
 * the navigation bar should be visible. It is used to conditionally display the navigation bar
 * depending on the current screen.
 *
 * @param navController The NavHostController used to manage navigation.
 * @return A boolean value indicating whether the navigation bar should be shown.
 */
@Composable
fun shouldShowNavigationBar(navController: NavHostController): Boolean {
    // Get the current back stack entry and observe changes in the navigation state
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    // Retrieve the current destination route from the back stack entry
    val currentDestination = navBackStackEntry?.destination?.route

    // Log the current destination for debugging purposes
    Log.d(TAG, "MainView | currentDestination: $currentDestination")

    // Return true if the current destination is not in the ignore list, indicating the navigation bar should be shown
    return currentDestination !in ignore
}
