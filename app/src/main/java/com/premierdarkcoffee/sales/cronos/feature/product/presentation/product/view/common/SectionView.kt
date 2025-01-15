package com.premierdarkcoffee.sales.cronos.feature.product.presentation.product.view.common

//
//  SectionView.kt
//  Maia
//
//  Created by José Ruiz on 9/10/24.
//

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp

@Composable
fun SectionView(title: String, content: @Composable () -> Unit) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 8.dp)
        .semantics {
            contentDescription = "Section titled $title"
        }) {
        Text(text = title,
             style = MaterialTheme.typography.labelLarge,
             modifier = Modifier
                 .padding(horizontal = 16.dp)
                 .semantics { heading() } // Marking title as a heading for screen readers
        )
        Box(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp)
            .background(MaterialTheme.colorScheme.primary.copy(0.1f), shape = RoundedCornerShape(10.dp))
            .padding(16.dp)
            .semantics(mergeDescendants = true) {} // Merging content into a single focus point
        ) {
            content()
        }
    }
}
