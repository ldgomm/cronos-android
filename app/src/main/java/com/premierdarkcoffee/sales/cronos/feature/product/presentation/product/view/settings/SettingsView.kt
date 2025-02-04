package com.premierdarkcoffee.sales.cronos.feature.product.presentation.product.view.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.premierdarkcoffee.sales.cronos.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsView(onNavigateToPrivacyPolicyButtonClicked: () -> Unit,
                 onNavigateToTermsOfUseButtonClicked: () -> Unit,
                 onNavigateToAccountDeletionButtonClicked: () -> Unit) {
    Scaffold(topBar = {
        TopAppBar(title = { Text(text = stringResource(id = R.string.settings_title)) })
    }) { paddingValues ->
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(16.dp)) {
            // Section 1: Privacy Policy and Terms and Conditions
            SettingsSection(title = stringResource(id = R.string.general_title),
                            items = listOf(stringResource(id = R.string.privacy_policy) to onNavigateToPrivacyPolicyButtonClicked,
                                           stringResource(id = R.string.terms_of_use) to onNavigateToTermsOfUseButtonClicked,
                                           stringResource(id = R.string.account_deletion) to onNavigateToAccountDeletionButtonClicked))

            Spacer(modifier = Modifier.height(24.dp))

        }
    }
}

@Composable
fun SettingsSection(title: String,
                    items: List<Pair<String, () -> Unit>>,
                    buttonColors: ButtonColors = ButtonDefaults.buttonColors()) {
    Text(text = title, style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(bottom = 8.dp))

    items.forEach { (label, action) ->
        Button(onClick = action, colors = buttonColors, modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)) {
            Text(label, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Start)
        }
    }
}
