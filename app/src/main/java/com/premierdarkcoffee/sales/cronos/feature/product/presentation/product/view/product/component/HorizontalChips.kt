package com.premierdarkcoffee.sales.cronos.feature.product.presentation.product.view.product.component

//
//  HorizontalChips.kt
//  Cronos
//
//  Created by José Ruiz on 18/3/25.
//

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HorizontalChips(items: List<String>,
                    selectedItem: String?,
                    onItemSelected: (String?) -> Unit,
                    modifier: Modifier = Modifier) {
    // A ScrollState for horizontal scroll
    val scrollState = rememberScrollState()

    Row(modifier = modifier
        .horizontalScroll(scrollState)
        .padding(horizontal = 12.dp)) {
        // "All" chip
        Chip(label = "All", isSelected = selectedItem == null, onClick = { onItemSelected(null) })

        // One chip for each item
        items.forEach { item ->
            Chip(label = item, isSelected = selectedItem == item, onClick = { onItemSelected(item) })
        }
    }
}
