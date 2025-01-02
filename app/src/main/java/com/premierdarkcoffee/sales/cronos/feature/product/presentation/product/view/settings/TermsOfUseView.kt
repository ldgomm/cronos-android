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
                .padding(horizontal = 16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Section 1: Introduction
            item {
                SettingsSectionView(
                    title = "1. Introduction",
                    content = "Welcome to Cronos, an application designed for administrators to manage enterprise tasks and operations within the Premier Dark Coffee platform. Cronos is intended solely for authorized administrators, and access to the app is restricted to approved users."
                )
            }

            // Section 2: User Eligibility
            item {
                SettingsSectionView(
                    title = "2. User Eligibility",
                    content = "The use of Cronos is limited to authorized administrators who are granted access by Premier Dark Coffee. Unauthorized use of the app is strictly prohibited."
                )
            }

            // Section 3: Authentication
            item {
                SettingsSectionView(
                    title = "3. Authentication",
                    content = "Authentication to Cronos is handled through secure API key verification. Administrators use their API key to access the app, and no personal information is collected or stored within Cronos."
                )
            }

            // Section 4: Administrative Management and Updates
            item {
                SettingsSectionView(
                    title = "4. Administrative Management and Updates",
                    content = "Administrators can manage and update system settings and operational data. However, sensitive operations should be performed with caution to ensure data consistency and accuracy within the platform."
                )
            }

            // Section 5: Data Security
            item {
                SettingsSectionView(
                    title = "5. Data Security",
                    content = "All interactions within Cronos are secured using industry-standard encryption protocols (such as HTTPS). Administrators are responsible for safeguarding their API keys and ensuring they access the app in a secure environment. Sharing API keys with unauthorized parties is strictly prohibited."
                )
            }

            // Section 6: Prohibited Activities
            item {
                SettingsSectionView(
                    title = "6. Prohibited Activities", content = "Administrators of Cronos are prohibited from engaging in the following activities:"
                )
                BulletPointList(
                    bulletPoints = listOf(
                        "Attempting to bypass security measures or gain unauthorized access to data.",
                        "Using the app for illegal activities or purposes not authorized by Premier Dark Coffee.",
                        "Tampering with or reverse-engineering the Cronos app or its components."
                    )
                )
            }

            // Section 7: Termination
            item {
                SettingsSectionView(
                    title = "7. Termination",
                    content = "Premier Dark Coffee reserves the right to revoke access to Cronos at any time without prior notice in the event of misuse or unauthorized access. Access will also be terminated if the administrator’s authorization is revoked."
                )
            }

            // Section 8: Limitation of Liability
            item {
                SettingsSectionView(
                    title = "8. Limitation of Liability",
                    content = "Cronos is provided \"as-is\" without any warranties. Premier Dark Coffee is not liable for any data loss, security breaches, or other damages arising from the use or inability to use the app, except where required by law."
                )
            }

            // Section 9: Updates to Terms
            item {
                SettingsSectionView(
                    title = "9. Updates to Terms",
                    content = "Premier Dark Coffee may update these Terms of Use from time to time. Any changes will take effect once a new version of the app is published, ensuring administrators can review the updated terms within the app. Continued use of the app after updates signifies acceptance of the revised terms."
                )
            }

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
                    text = "© 2024 Cronos, Premier Dark Coffee. All Rights Reserved.",
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(top = 50.dp)
                )
            }
        }
    }
}

