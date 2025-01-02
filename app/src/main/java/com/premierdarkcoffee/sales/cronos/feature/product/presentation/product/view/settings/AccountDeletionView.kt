package com.premierdarkcoffee.sales.cronos.feature.product.presentation.product.view.settings

//
//  AccountDeletionView.kt
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountDeletionView(modifier: Modifier = Modifier) {
    Scaffold(topBar = {
        TopAppBar(title = { Text(text = "Account Deletion") })
    }) { paddingValues ->
        LazyColumn(
            modifier = modifier
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Section 1: Deletion Process
            item {
                Text(
                    text = """
                        If you wish to delete your account in Sales, you can do so within the app by navigating to the Settings section. In the Configurations menu, you will find an option to request the deletion of your account.
                    """.trimIndent(), style = MaterialTheme.typography.bodyMedium
                )
            }

            // Section 2: Important Note
            item {
                Text(
                    text = """
                        Please note: Deleting your account is an irreversible action. This process will permanently delete your account, including all store data and products associated with your account. Once deleted, this information cannot be recovered.
                    """.trimIndent(), style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold
                )
            }

            // Section 3: Backup Recommendation
            item {
                Text(
                    text = """
                        Before proceeding with account deletion, we recommend that you ensure you have backed up any necessary data or information, as we will not be able to restore it once the deletion process is completed.
                    """.trimIndent(), style = MaterialTheme.typography.bodyMedium
                )
            }

            // Section 4: Contact Information
            item {
                Text(
                    text = """
                        If you have any questions or need assistance, please contact us at account@premierdarkcoffee.com.
                    """.trimIndent(), style = MaterialTheme.typography.bodyMedium, color = Color.Blue
                )
            }

            // Section 5: Response Time Notice
            item {
                Text(
                    text = """
                        Please note that while we strive to respond to all inquiries as quickly as possible, there may be times when responses take a couple of days to be attended due to operational constraints. We appreciate your patience and understanding.
                    """.trimIndent(), style = MaterialTheme.typography.bodyMedium
                )
            }

            // Footer
            item {
                Text(
                    text = "© 2024 Sales, Premier Dark Coffee. All Rights Reserved.",
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(top = 50.dp)
                )
            }
        }
    }
}
