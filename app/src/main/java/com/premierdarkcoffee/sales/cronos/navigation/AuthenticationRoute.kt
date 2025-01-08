package com.premierdarkcoffee.sales.cronos.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.premierdarkcoffee.sales.cronos.R
import com.premierdarkcoffee.sales.cronos.util.helper.SecurePreferencesHelper


fun NavGraphBuilder.authenticationRoute(onSubmitApiKeyButtonClicked: () -> Unit) {
    composable<AuthenticationRoute> {
        AuthenticationView(onSubmitApiKeyButtonClicked)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthenticationView(onSubmitApiKeyButtonClicked: () -> Unit) {
    var inputText by remember { mutableStateOf(TextFieldValue("")) }
    var isValidKey by remember { mutableStateOf(false) }
    val context = LocalContext.current

    // Function to validate API key format
    fun validateApiKey(apiKey: String): Boolean {
        if (apiKey.length != 32) return false
        val pattern = Regex("^[a-zA-Z0-9]{2}-[a-zA-Z0-9]{3}-[a-zA-Z0-9]{5}-[a-zA-Z0-9]{7}-[a-zA-Z0-9]{11}\$")
        return pattern.matches(apiKey)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp), contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center
        ) {
            // Title
            Text(
                text = stringResource(id = R.string.authenticate_title),
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Text(
                text = stringResource(id = R.string.authenticate_description),
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // API Key Input Field
            OutlinedTextField(
                value = inputText,
                onValueChange = {
                    inputText = it
                    isValidKey = validateApiKey(it.text)
                },
                label = { Text("API Key") },
                placeholder = { Text("XX-XXX-XXXXX-XXXXXXX-XXXXXXXXXXX") },
                isError = !isValidKey && inputText.text.isNotEmpty(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                    errorBorderColor = MaterialTheme.colorScheme.error,
                    errorLabelColor = MaterialTheme.colorScheme.error
                )
            )

            // Error Message
            if (!isValidKey && inputText.text.isNotEmpty()) {
                Text(
                    text = stringResource(id = R.string.invalid_api_key_format),
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }

            // Validate Button
            Button(
                onClick = {
                    SecurePreferencesHelper.saveApiKey(context, inputText.text)
                    onSubmitApiKeyButtonClicked()
                }, enabled = isValidKey, modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp), colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                    disabledContainerColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
                    disabledContentColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
                )
            ) {
                Text(stringResource(R.string.validate))
            }
        }
    }
}

@Preview
@Composable
private fun AuthenticationView_Preview() {
    AuthenticationView(onSubmitApiKeyButtonClicked = {})
}