package com.premierdarkcoffee.sales.cronos.feature.product.presentation.product.view.product.edit.components

//
//  SmartDoubleTextFieldCard.kt
//  Cronos
//
//  Created by José Ruiz on 2/4/25.
//

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun SmartDoubleTextFieldCard(label: String,
                             value: Double,
                             onValueChange: (Double) -> Unit,
                             readOnly: Boolean = false,
                             enabled: Boolean = true,
                             keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                             onClick: (() -> Unit)? = null) {
    // Local text state: initially just show the current Double as a plain string
    var textFieldValue by rememberSaveable { mutableStateOf(value.toString()) }

    // Whenever the externally-supplied `value` changes,
    // we update our local text to match (if it differs).
    LaunchedEffect(value) {
        val newString = value.toString()
        if (textFieldValue != newString) {
            textFieldValue = newString
        }
    }

    ElevatedCard(modifier = Modifier
        .clickable { onClick?.invoke() }
        .fillMaxWidth()
        .padding(vertical = 6.dp, horizontal = 11.dp),
                 shape = MaterialTheme.shapes.medium,
                 elevation = CardDefaults.elevatedCardElevation(4.dp)) {
        TextField(value = textFieldValue,
                  onValueChange = { newInput ->
                      // 1. Replace all commas with dots (or vice versa) so we only track one type.
                      val normalized = newInput.replace(',', '.')

                      // 2. Allow digits and at most one dot.
                      //    We'll remove any second (or third) dot.
                      val filtered = buildString {
                          var dotCount = 0
                          for (char in normalized) {
                              when {
                                  char.isDigit() -> append(char)
                                  char == '.' && dotCount == 0 -> {
                                      append(char)
                                      dotCount++
                                  }
                                  // ignore everything else
                              }
                          }
                      }

                      // Update our local text with the cleaned version
                      textFieldValue = filtered

                      // 3. Convert the sanitized string to a Double.
                      //    If there's no decimal separator, parse as Int → Double
                      val doubleValue = if ('.' in filtered) {
                          filtered.toDoubleOrNull()
                      } else {
                          filtered.toIntOrNull()?.toDouble()
                      } ?: 0.0

                      // 4. Propagate the numeric value upstream
                      onValueChange(doubleValue)
                  },
                  label = { Text(label) },
                  keyboardOptions = keyboardOptions,
                  modifier = Modifier
                      .fillMaxWidth()
                      .semantics { contentDescription = label },
                  readOnly = readOnly,
                  enabled = enabled)
    }
}
