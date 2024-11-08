package com.premierdarkcoffee.sales.cronos.navigation

import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable


fun NavGraphBuilder.authenticationRoute(onSubmitApiKeyButtonClicked: (apiKey: String) -> Unit) {
    composable<AuthenticationRoute> {
        AuthenticationView(onSubmitApiKeyButtonClicked)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthenticationView(onSubmitApiKeyButtonClicked: (apiKey: String) -> Unit) {
    var inputText by remember { mutableStateOf(TextFieldValue("")) }
    var isValidKey by remember { mutableStateOf(true) }

    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Enter your API key", modifier = Modifier.padding(bottom = 8.dp))

        OutlinedTextField(
            value = inputText,
            onValueChange = {
                inputText = it
                isValidKey = it.text.length == 37 // Validate length
            },
            label = { Text("API Key") },
            placeholder = { Text("Enter your API key") },
            isError = !isValidKey, // Show error if invalid
            modifier = Modifier.padding(bottom = 16.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                errorBorderColor = Color.Red, errorLabelColor = Color.Red
            )
        )

        if (!isValidKey) {
            Text(
                text = "API key must be exactly characters long.", color = Color.Red, modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        Button(
            onClick = {
                if (isValidKey) {
                    onSubmitApiKeyButtonClicked(inputText.text)
                } else {
                    println("Invalid API key length")
                }
            }, enabled = isValidKey // Disable button if not valid
        ) {
            Text("Submit")
        }
    }
}