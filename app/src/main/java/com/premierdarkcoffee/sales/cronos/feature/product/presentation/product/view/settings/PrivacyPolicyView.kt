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
                    title = "1. Introduction",
                    content = """
                        Welcome to Cronos, the Administrator App. This application is designed exclusively for managing enterprise-related tasks and operations within the Premier Dark Coffee ecosystem. Cronos ensures a secure and private environment for administrators, as no personal data is collected or stored.
                    """.trimIndent()
                )
            }

            // Section 2: No Collection of Personal Data
            item {
                SettingsSectionView(
                    title = "2. No Collection of Personal Data",
                    content = """
                        Our application does not collect or store any personal information from administrators. Access to the app is securely handled using API key authentication, ensuring that no names, emails, or other identifiable information are processed or retained.
                    """.trimIndent()
                )
            }

            // Section 3: Authentication
            item {
                SettingsSectionView(
                    title = "3. Authentication",
                    content = """
                        Authentication in Cronos is managed through API keys. Administrators are issued unique API keys to securely access the application. These keys are:
                    """.trimIndent()
                )
                BulletPointList(
                    bulletPoints = listOf(
                        "Encrypted: API keys are securely encrypted and stored locally on the administrator’s device.",
                        "Non-expiring: Keys do not expire automatically but can be manually revoked or reset if necessary.",
                        "Secure: Once an administrator logs out, the API key is removed from the device to ensure security."
                    )
                )
            }

            // Section 4: Data Usage and Security
            item {
                SettingsSectionView(
                    title = "4. Data Usage and Security",
                    content = """
                        While Cronos does not collect personal information, it ensures secure interactions by using industry-standard encryption protocols such as HTTPS. This guarantees that all communication between the app and the backend systems is encrypted and protected. Only authorized administrators with valid API keys can access enterprise-related data.
                    """.trimIndent()
                )
            }

            // Section 5: Sharing of Information
            item {
                SettingsSectionView(
                    title = "5. Sharing of Information",
                    content = """
                        Cronos does not share any data with third parties. As no personal data is collected or processed, there is no information to disclose. The API keys remain secure and are only used to validate access to the app’s functionality.
                    """.trimIndent()
                )
            }

            // Section 6: Data Security
            item {
                SettingsSectionView(
                    title = "6. Data Security",
                    content = """
                        Although Cronos does not store personal data, we adhere to best security practices to protect all app interactions. Encrypted communication channels and local storage for API keys ensure that only authorized administrators have access to enterprise systems and information.
                    """.trimIndent()
                )
            }

            // Section 7: Data Retention
            item {
                SettingsSectionView(
                    title = "7. Data Retention",
                    content = """
                        Cronos does not retain user sessions or store personal information. API keys are stored only during active use and are deleted when the administrator logs out or revokes access manually.
                    """.trimIndent()
                )
            }

            // Section 8: Privacy Rights
            item {
                SettingsSectionView(
                    title = "8. Privacy Rights",
                    content = """
                        As Cronos does not collect or store personal data, no data management rights are applicable to administrators. For concerns related to authentication security or API key management, please contact the enterprise support team at Premier Dark Coffee.
                    """.trimIndent()
                )
            }

            // Section 9: Updates to This Policy
            item {
                SettingsSectionView(
                    title = "9. Updates to This Policy",
                    content = """
                        This Privacy Policy may be updated periodically to reflect changes in the application or legal requirements. Updates will be communicated through internal enterprise channels. Continued use of Cronos after any updates indicates your acceptance of the revised policy.
                    """.trimIndent()
                )
            }

            // Section 10: Contact Us
            item {
                SettingsSectionView(
                    title = "10. Contact Us",
                    content = """
                        If you have any questions regarding this Privacy Policy, please contact us at support@premierdarkcoffee.com.
                    """.trimIndent()
                )
            }

            // Section 11: Changes to This Policy
            item {
                SettingsSectionView(
                    title = "11. Changes to This Policy",
                    content = """
                        Any changes to this policy will take effect only when a new version of Cronos is published. This ensures that all administrators can view the updated policy natively within the app. We encourage you to keep your app updated to stay informed of any changes.
                    """.trimIndent()
                )
            }

            // Section 12: Response Time
            item {
                SettingsSectionView(
                    title = "12. Response Time",
                    content = """
                        Please note that while we strive to respond to all inquiries as quickly as possible, there may be times when responses take a couple of days due to operational constraints. We appreciate your patience and understanding.
                    """.trimIndent()
                )
            }

            // Footer
            item {
                Text(
                    text = "© 2024 Cronos, Premier Dark Coffee. All Rights Reserved.",
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(top = 50.dp)
                )
            }
        }
    }
}
