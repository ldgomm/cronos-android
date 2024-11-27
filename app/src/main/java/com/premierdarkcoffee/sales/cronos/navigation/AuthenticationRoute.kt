package com.premierdarkcoffee.sales.cronos.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
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
            Text(text = "Enter your API key", modifier = Modifier.padding(bottom = 8.dp))

            OutlinedTextField(
                value = inputText,
                onValueChange = {
                    inputText = it
                    isValidKey = validateApiKey(it.text)
                },
                label = { Text("API Key") },
                placeholder = { Text("Enter your API key") },
                isError = !isValidKey,
                modifier = Modifier.padding(bottom = 16.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    errorBorderColor = Color.Red, errorLabelColor = Color.Red
                )
            )

            if (isValidKey) {
                Button(
                    onClick = {
                        if (isValidKey) {
                            SecurePreferencesHelper.saveApiKey(context, inputText.text)
                            onSubmitApiKeyButtonClicked()
                        } else {
                            println("Invalid API key format")
                        }
                    }, enabled = isValidKey
                ) {
                    Text("Validate")
                }
            }
        }
    }
}
