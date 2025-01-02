package com.premierdarkcoffee.sales.sales.feature.product.presentation.product.view.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.google.gson.Gson
import com.premierdarkcoffee.sales.cronos.feature.product.domain.model.product.Product

@Composable
fun ProductItemView(product: Product, onNavigateToProductView: (String) -> Unit) {
    ElevatedCard(onClick = { onNavigateToProductView(Gson().toJson(product)) },
                 modifier = Modifier
                     .fillMaxWidth()
                     .padding(vertical = 8.dp, horizontal = 16.dp),
                 shape = RoundedCornerShape(16.dp),
                 elevation = CardDefaults.elevatedCardElevation(8.dp)) {
        Row(modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            AsyncImage(model = product.image.url,
                       contentDescription = product.name,
                       modifier = Modifier
                           .size(64.dp)
                           .clip(RoundedCornerShape(8.dp))
                           .background(MaterialTheme.colorScheme.surfaceVariant),
                       contentScale = ContentScale.Crop)
            Spacer(modifier = Modifier.width(16.dp))
            Column(verticalArrangement = Arrangement.Center, modifier = Modifier.weight(1f)) {
                Text(text = product.name,
                     style = MaterialTheme.typography.titleMedium,
                     fontWeight = FontWeight.Bold,
                     color = MaterialTheme.colorScheme.onSurface,
                     modifier = Modifier.padding(bottom = 4.dp))
                Text(product.model,
                     style = MaterialTheme.typography.bodyMedium,
                     fontWeight = FontWeight.Light,
                     color = MaterialTheme.colorScheme.onSurface,
                     modifier = Modifier.padding(bottom = 4.dp))
            }
        }
    }
}
