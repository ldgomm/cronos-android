package com.premierdarkcoffee.sales.cronos.feature.product.presentation.product.view.product

//
//  KeywordBubble.kt
//  Maia
//
//  Created by José Ruiz on 1/8/24.
//

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun KeywordBubble(keyword: String, deleteAction: () -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(4.dp)
            .background(color = MaterialTheme.colorScheme.primary, shape = RoundedCornerShape(10.dp))) {
        Text(text = keyword, color = Color.White, modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp))
        IconButton(onClick = deleteAction, modifier = Modifier.size(24.dp)) {
            Icon(painter = painterResource(id = android.R.drawable.ic_menu_close_clear_cancel),
                 contentDescription = "Delete",
                 tint = Color.White)
        }
    }
}
