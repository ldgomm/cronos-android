package com.premierdarkcoffee.sales.cronos.feature.product.presentation.product.view.product.component

//
//  Chip.kt
//  Cronos
//
//  Created by José Ruiz on 18/3/25.
//

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
fun Chip(label: String, isSelected: Boolean, modifier: Modifier = Modifier, onClick: () -> Unit) {
    val backgroundColor = if (isSelected) {
        MaterialTheme.colorScheme.primary
    } else {
        MaterialTheme.colorScheme.surfaceVariant
    }
    val contentColor = if (isSelected) {
        MaterialTheme.colorScheme.onPrimary
    } else {
        MaterialTheme.colorScheme.onSurfaceVariant
    }

    Surface(modifier = modifier
        .padding(horizontal = 4.dp, vertical = 2.dp)
        .clip(RoundedCornerShape(50))
        .clickable { onClick() },
            color = backgroundColor,
            contentColor = contentColor) {
        Text(text = label,
             style = MaterialTheme.typography.bodyMedium,
             modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp))
    }
}
