package com.premierdarkcoffee.sales.cronos.feature.product.presentation.product.view.settings

//
//  TermsOfUseView.kt
//  Maia
//
//  Created by José Ruiz on 18/10/24.
//

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TermsOfUseView(modifier: Modifier = Modifier) {
    Scaffold(topBar = {
        TopAppBar(title = { Text(text = "Terms of Use") })
    }) { paddingValues ->
        LazyColumn(
            modifier = modifier
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Section 1: Introduction
            item {
                SettingsSectionView(
                    title = "1. Introduction",
                    content = "Welcome to Maia, an application designed for store owners to manage their products, sales, and customer interactions within the Premier Dark Coffee platform. Maia is intended solely for authorized store owners, and access to the app is restricted to approved users."
                )
            }

            // Section 2: User Eligibility
            item {
                SettingsSectionView(
                    title = "2. User Eligibility",
                    content = "The use of Maia is limited to store owners who are registered and approved by Premier Dark Coffee. Unauthorized use of the app is strictly prohibited."
                )
            }

            // Section 3: Authentication
            item {
                SettingsSectionView(
                    title = "3. Authentication", content = "Authentication to Maia is handled through third-party services:"
                )
                BulletPointList(
                    bulletPoints = listOf(
                        "Apple Sign-In (iOS): Users authenticate via their Apple credentials, ensuring secure access to the app without Maia storing login credentials.",
                        "Google Sign-In (Android): Users authenticate via their Google credentials, similarly ensuring secure access without Maia retaining login data."
                    )
                )
            }

            // Section 4: Product Management and Updates
            item {
                SettingsSectionView(
                    title = "4. Product Management and Updates",
                    content = "Store owners can manage and update product information, including images, descriptions, and prices. However, some areas of product information may be updated or modified by the administrator without the store’s explicit permission when necessary to ensure consistency or accuracy."
                )
            }

            // Section 5: Image and Content Guidelines
            item {
                SettingsSectionView(
                    title = "5. Image and Content Guidelines", content = "When uploading product images, stores must ensure that:"
                )
                BulletPointList(
                    bulletPoints = listOf(
                        "Images are 1024x1024 pixels in size.",
                        "No phone numbers, contact information, or other personal details are included outside of what the app provides."
                    )
                )
                Text(
                    text = "Stores should update product information only when strictly necessary, as existing information is considered valid. Improper updates or inclusion of unauthorized details may result in penalties or content removal."
                )
            }

            // Section 6: Communication with Clients
            item {
                SettingsSectionView(
                    title = "6. Communication with Clients", content = "When chatting with clients through the app, stores must:"
                )
                BulletPointList(
                    bulletPoints = listOf(
                        "Be polite and professional at all times.",
                        "Follow the same guidelines for content and image updates.",
                        "Never request personal information from clients, as the system and app are designed to respect the absolute privacy of customers."
                    )
                )
            }

            // Add more sections in the same manner

            // Contact Information
            item {
                Text(
                    text = "If you have any questions or need assistance, please contact us at terms@premierdarkcoffee.com.",
                    color = Color.Blue,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            // Footer
            item {
                Text(
                    text = "© 2024 Premier Dark Coffee. All Rights Reserved.",
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(top = 50.dp)
                )
            }
        }
    }
}
