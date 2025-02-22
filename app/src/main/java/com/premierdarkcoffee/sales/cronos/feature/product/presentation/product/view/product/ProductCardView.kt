package com.premierdarkcoffee.sales.cronos.feature.product.presentation.product.view.product

//
//  ProductCardView.kt
//  Maia
//
//  Created by José Ruiz on 30/7/24.
//

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.google.gson.Gson
import com.premierdarkcoffee.sales.cronos.feature.product.domain.model.product.Product
import com.premierdarkcoffee.sales.cronos.util.extension.formatDate
import java.text.NumberFormat
import java.util.Currency

@Composable
fun ProductCardView(product: Product, onNavigateToProductView: (String) -> Unit) {

    val numberFormat = NumberFormat.getCurrencyInstance().apply {
        currency = Currency.getInstance(product.price.currency)
    }
    val originalPrice = product.price.amount / (1 - product.price.offer.discount / 100.0)

    ElevatedCard(onClick = { onNavigateToProductView(Gson().toJson(product)) },
                 modifier = Modifier
                     .fillMaxWidth()
                     .padding(vertical = 8.dp, horizontal = 16.dp)
                     .semantics {
                         contentDescription = "Product card for ${product.name}. Tap to view details."
                     },
                 shape = RoundedCornerShape(12.dp),
                 elevation = CardDefaults.elevatedCardElevation(8.dp)) {
        Row(modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .semantics(mergeDescendants = true) {}, // Group content as a single unit
            verticalAlignment = Alignment.CenterVertically) {
            AsyncImage(model = product.image.url,
                       contentDescription = "Image of ${product.name}",
                       modifier = Modifier
                           .size(72.dp)
                           .clip(RoundedCornerShape(8.dp))
                           .background(MaterialTheme.colorScheme.surfaceVariant),
                       contentScale = ContentScale.Crop)
            Spacer(modifier = Modifier.width(16.dp))
            Column(verticalArrangement = Arrangement.Center, modifier = Modifier.weight(1f)) {
                Text(text = product.name,
                     maxLines = 1,
                     style = MaterialTheme.typography.titleMedium,
                     fontWeight = FontWeight.Bold,
                     color = MaterialTheme.colorScheme.onSurface,
                     modifier = Modifier.padding(bottom = 4.dp))
                product.label?.let {
                    Text(text = it,
                         style = MaterialTheme.typography.bodySmall,
                         fontWeight = FontWeight.Bold,
                         color = MaterialTheme.colorScheme.onSurface,
                         modifier = Modifier
                             .padding(bottom = 4.dp)
                             .semantics { contentDescription = "Label: $it" })
                }
                Text(text = product.description,
                     style = MaterialTheme.typography.bodySmall,
                     color = MaterialTheme.colorScheme.onSurface,
                     maxLines = 2,
                     overflow = TextOverflow.Ellipsis,
                     modifier = Modifier
                         .padding(end = 4.dp)
                         .semantics { contentDescription = "Description: ${product.description}" })
                Text(text = product.date.formatDate(),
                     style = MaterialTheme.typography.labelSmall,
                     color = MaterialTheme.colorScheme.onSurfaceVariant,
                     modifier = Modifier
                         .padding(top = 4.dp)
                         .semantics { contentDescription = "Date: ${product.date.formatDate()}" })
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                if (product.price.offer.isActive) {
                    Text(text = "${product.price.offer.discount}% OFF",
                         style = TextStyle(fontSize = 10.sp),
                         color = Color.White,
                         fontWeight = FontWeight.Bold,
                         modifier = Modifier
                             .clip(RoundedCornerShape(4.dp))
                             .background(Color.Red.copy(alpha = 0.7f))
                             .padding(horizontal = 4.dp, vertical = 2.dp)
                             .semantics { contentDescription = "${product.price.offer.discount} percent off" })
                }
                Text(text = numberFormat.format(product.price.amount),
                     style = TextStyle(fontSize = 11.sp),
                     color = MaterialTheme.colorScheme.primary,
                     fontWeight = FontWeight.SemiBold,
                     modifier = Modifier
                         .padding(top = 4.dp)
                         .semantics { contentDescription = "Price: ${numberFormat.format(product.price.amount)}" })
                Text(text = numberFormat.format(originalPrice),
                     style = TextStyle(fontSize = 8.sp).copy(textDecoration = TextDecoration.LineThrough),
                     color = MaterialTheme.colorScheme.onSurfaceVariant,
                     modifier = Modifier
                         .padding(top = 2.dp)
                         .semantics { contentDescription = "Original price: ${numberFormat.format(originalPrice)}" })
            }
        }
    }
}
