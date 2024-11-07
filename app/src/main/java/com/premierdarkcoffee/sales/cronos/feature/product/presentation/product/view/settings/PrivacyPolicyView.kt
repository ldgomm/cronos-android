package com.premierdarkcoffee.sales.cronos.feature.product.presentation.product.view.settings

//
//  PrivacyPolicyView.kt
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
fun PrivacyPolicyView(modifier: Modifier = Modifier) {
    Scaffold(topBar = {
        TopAppBar(title = { Text(text = "Privacy Policy") })
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
                    title = "1. Introduction", content = """
                    Welcome to the Store Application. We are committed to protecting the data related to your store and products. This Privacy Policy explains how we collect, use, and safeguard your information when you use our platform. Please review it carefully, and contact us if you have any questions or concerns.
                    """.trimIndent()
                )
            }

            // Section 2: Information We Collect
            item {
                SettingsSectionView(
                    title = "2. Information We Collect",
                    content = "We collect the following types of information to provide and improve our services:"
                )
                BulletPointList(
                    bulletPoints = listOf(
                        "Store Information: When you manage your store through the application, we collect details such as store name, location, contact information, and operating hours.",
                        "Product Information: We collect information about the products you upload to your store, including product names, descriptions, images, prices, and availability.",
                        "Communication Data: We collect data from conversations between store owners and customers conducted through the app for customer service purposes."
                    )
                )
            }

            // Section 3: Authentication
            item {
                SettingsSectionView(
                    title = "3. Authentication", content = "Authentication for the Store Application is handled securely through:"
                )
                BulletPointList(
                    bulletPoints = listOf(
                        "Apple Sign-In (iOS): Administrators and store owners authenticate using their Apple credentials. No login information is stored by the app itself.",
                        "Google Sign-In (Android): Administrators and store owners authenticate using their Google credentials, ensuring secure access without storing user login information."
                    )
                )
            }

            // Section 4: How We Use Your Information
            item {
                SettingsSectionView(
                    title = "4. How We Use Your Information", content = "We use your store and product information to:"
                )
                BulletPointList(
                    bulletPoints = listOf(
                        "Provide access to the app's features for managing your store and products.",
                        "Display relevant product information to customers on the platform.",
                        "Facilitate communication between store owners and customers in real-time."
                    )
                )
            }

            // Section 5: Sharing of Your Information
            item {
                SettingsSectionView(
                    title = "5. Sharing of Your Information",
                    content = "We do not sell or share your store information with third parties, except in the following cases:"
                )
                BulletPointList(
                    bulletPoints = listOf(
                        "Legal Requirements: If required by law, we may disclose your information to comply with legal obligations.",
                        "Service Providers: We may share your information with service providers who help maintain and improve the platform. These providers are contractually obligated to protect your data.",
                        "Business Transfers: If the platform is involved in a merger, acquisition, or sale, your information may be transferred as part of the transaction."
                    )
                )
            }

            // Section 6: Data Security
            item {
                SettingsSectionView(
                    title = "6. Data Security", content = """
                    We use industry-standard security measures to protect your store and product data. All data transmitted between your device and our servers is encrypted using HTTPS, ensuring secure transmission. We also use security protocols, such as JWT (JSON Web Tokens), for authentication management. While we make every effort to protect your data, no system is completely secure, and we are continuously improving our security measures.
                    """.trimIndent()
                )
            }

            // Section 7: Data Retention
            item {
                SettingsSectionView(
                    title = "7. Data Retention", content = """
                    We will retain your store and product information for as long as necessary to provide our services or as required by law. If you decide to delete your store or remove your data, all information will be permanently deleted unless we are required by law to retain it.
                    """.trimIndent()
                )
            }

            // Section 8: Your Privacy Rights
            item {
                SettingsSectionView(
                    title = "8. Your Privacy Rights", content = "As a store owner, you have the following rights:"
                )
                BulletPointList(
                    bulletPoints = listOf(
                        "Access: You can request access to the data we have collected about your store and products.",
                        "Correction: If any of your store or product information is incorrect, you can update it directly within the app.",
                        "Deletion: You can request that we delete your store and product data from our systems."
                    )
                )
            }

            // Section 9: Changes to This Policy
            item {
                SettingsSectionView(
                    title = "9. Changes to This Policy", content = """
                    We may update this Privacy Policy from time to time to reflect changes in the application or legal requirements. You will be notified of any significant changes through the app or email. Continued use of the platform following any updates signifies your acceptance of the revised policy.
                    """.trimIndent()
                )
            }

            // Section 11: Update Visibility
            item {
                SettingsSectionView(
                    title = "11. Changes to This Policy", content = """
                    Any changes to this policy will only take effect once a new update of the Maia app is published. This ensures that all users will be able to view the updated policy natively within the application. We encourage you to keep your app updated to stay informed of any changes.
                    """.trimIndent()
                )
            }

            // Section 12: Response Time
            item {
                SettingsSectionView(
                    title = "12. Response Time", content = """
                    Please note that while we strive to respond to all inquiries as quickly as possible, there may be times when responses take a couple of days to be attended due to operational constraints. We appreciate your patience and understanding.
                    """.trimIndent()
                )
            }

            // Contact Information
            item {
                Text(
                    text = "If you have any questions or need assistance, please contact us at privacy@premierdarkcoffee.com.",
                    color = Color.Blue,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            // Footer
            item {
                Text(
                    text = "© 2024 Maia, Premier Dark Coffee. All Rights Reserved.",
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(top = 50.dp)
                )
            }
        }
    }
}