package com.premierdarkcoffee.sales.cronos.feature.product.presentation.product.view.product.edit.components

//
//  IntTextFieldCardWithStepper.kt
//  Cronos
//
//  Created by José Ruiz on 2/4/25.
//


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun IntTextFieldCardWithStepper(label: String,
                                value: Int,
                                onValueChange: (Int) -> Unit,
                                valueRange: IntRange = 0..100,
                                readOnly: Boolean = false,
                                enabled: Boolean = true,
                                keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                onClick: (() -> Unit)? = null) {
    ElevatedCard(modifier = Modifier
        .clickable { onClick?.invoke() }
        .fillMaxWidth()
        .padding(vertical = 6.dp, horizontal = 11.dp),
                 shape = MaterialTheme.shapes.medium,
                 elevation = CardDefaults.elevatedCardElevation(4.dp)) {
        Column(modifier = Modifier.fillMaxWidth()) {
            TextField(value = value.toString(),
                      onValueChange = { newText ->
                          // Convert user’s text to Int or fallback to 0
                          onValueChange(newText.toIntOrNull() ?: 0)
                      },
                      label = { Text(label) },
                      keyboardOptions = keyboardOptions,
                      modifier = Modifier
                          .fillMaxWidth()
                          .semantics { contentDescription = label },
                      readOnly = readOnly,
                      enabled = enabled)

            // Stepper Row: (-) button, current value, (+) button
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween) {
                Button(onClick = {
                    if (value > valueRange.first) {
                        onValueChange(value - 1)
                    }
                }, enabled = value > valueRange.first) {
                    Text("-")
                }

                Text(value.toString(), modifier = Modifier.padding(horizontal = 16.dp))

                Button(onClick = {
                    if (value < valueRange.last) {
                        onValueChange(value + 1)
                    }
                }, enabled = value < valueRange.last) {
                    Text("+")
                }
            }
        }
    }
}
