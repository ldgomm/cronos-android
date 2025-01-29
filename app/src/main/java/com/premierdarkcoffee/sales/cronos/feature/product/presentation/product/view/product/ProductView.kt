package com.premierdarkcoffee.sales.cronos.feature.product.presentation.product.view.product

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Edit
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.google.gson.Gson
import com.premierdarkcoffee.sales.cronos.R
import com.premierdarkcoffee.sales.cronos.feature.product.domain.model.product.Product
import com.premierdarkcoffee.sales.cronos.feature.product.presentation.product.view.common.SectionView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductView(product: Product,
                onPopBackStackActionTriggered: () -> Unit,
                onAddOrUpdateEditedProductButtonClick: (String) -> Unit) {
    val scrollState = rememberScrollState()

    Scaffold(topBar = {
        TopAppBar(title = {
            Text(text = product.name, fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 8.dp))
        }, modifier = Modifier, navigationIcon = {
            IconButton(onClick = onPopBackStackActionTriggered) {
                Icon(ImageVector.vectorResource(R.drawable.arrow_back), contentDescription = "Back")
            }
        })
    }, floatingActionButton = {
        FloatingActionButton(onClick = {
            onAddOrUpdateEditedProductButtonClick(Gson().toJson(product))
        }, containerColor = MaterialTheme.colorScheme.secondary, contentColor = Color.White) {
            Icon(imageVector = Icons.Sharp.Edit, contentDescription = "Edit")
        }
    }, floatingActionButtonPosition = FabPosition.End, modifier = Modifier.fillMaxHeight()) { paddingValues ->
        Column(modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(paddingValues)) {
            // Image
            product.image.url.let {
                Box(contentAlignment = Alignment.BottomEnd, modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp)) {
                    AsyncImage(model = it,
                               contentDescription = product.name,
                               modifier = Modifier
                                   .fillMaxWidth()
                                   .height(400.dp),
                               contentScale = ContentScale.Fit)

                    Text(text = product.category.subclass,
                         style = MaterialTheme.typography.labelSmall,
                         color = Color.White,
                         modifier = Modifier
                             .padding(end = 16.dp, bottom = 12.dp)
                             .background(Color.Gray.copy(alpha = 0.7f), RoundedCornerShape(10.dp))
                             .padding(horizontal = 10.dp, vertical = 6.dp)
                             .align(Alignment.BottomEnd),
                         textAlign = TextAlign.End)
                }
            }

            SectionView(title = stringResource(id = R.string.label_label)) {
                Text(text = product.label ?: "",
                     style = MaterialTheme.typography.bodySmall,
                     color = MaterialTheme.colorScheme.onSecondaryContainer)
            }

            if (product.year != null) {
                SectionView(title = stringResource(id = R.string.owner_label)) {
                    Text(text = "${product.owner}, ${product.year}",
                         style = MaterialTheme.typography.bodySmall,
                         color = MaterialTheme.colorScheme.onSecondaryContainer)
                }
            } else {
                SectionView(title = stringResource(id = R.string.owner_label)) {
                    Text(text = product.owner ?: "",
                         style = MaterialTheme.typography.bodySmall,
                         color = MaterialTheme.colorScheme.onSecondaryContainer)
                }
            }

            if (product.model.length > 3) {
                SectionView(title = stringResource(id = R.string.model_label)) {
                    Text(text = product.model,
                         style = MaterialTheme.typography.bodySmall,
                         color = MaterialTheme.colorScheme.onSecondaryContainer)
                }
            }

// Description
            SectionView(title = stringResource(id = R.string.description_label)) {
                Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    Text(text = product.description,
                         style = MaterialTheme.typography.bodySmall,
                         color = MaterialTheme.colorScheme.onSecondaryContainer)
                    Spacer(modifier = Modifier.weight(1f))
                }
            }


            product.overview.let { overviewList ->
                if (overviewList.isNotEmpty()) {
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState())
                        .padding(horizontal = 16.dp)) {
                        overviewList.forEach { info ->
                            ElevatedCard(modifier = Modifier
                                .width(300.dp) // Set a fixed width for each card to make them more consistent in size
                                .padding(end = 16.dp),
                                         shape = MaterialTheme.shapes.medium,
                                         elevation = CardDefaults.elevatedCardElevation(4.dp)) {
                                Column(modifier = Modifier.padding(16.dp)) {
                                    AsyncImage(model = info.image.url,
                                               contentDescription = info.title,
                                               modifier = Modifier
                                                   .fillMaxWidth()
                                                   .height(180.dp)
                                                   .clip(MaterialTheme.shapes.medium)
                                                   .padding(bottom = 8.dp), // Reduced padding for better content alignment
                                               contentScale = ContentScale.Crop)
                                    Text(text = info.title,
                                         style = MaterialTheme.typography.titleMedium,
                                         fontWeight = FontWeight.Bold,
                                         modifier = Modifier.padding(bottom = 4.dp) // Reduced padding for a more compact look
                                    )
                                    Text(text = info.subtitle,
                                         style = MaterialTheme.typography.bodyMedium,
                                         modifier = Modifier.padding(bottom = 4.dp) // Reduced padding for a more compact look
                                    )
                                    Text(text = info.description,
                                         style = MaterialTheme.typography.bodySmall,
                                         maxLines = 10, // Limited lines to make the card more compact
                                         overflow = TextOverflow.Ellipsis // Handle overflow with ellipsis
                                    )
                                }
                            }
                        }
                    }
                }
            }

            SectionView(title = stringResource(id = R.string.details_label)) {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
                    ProductDetailRow(label = stringResource(id = R.string.price_label),
                                     value = "${product.price.amount} ${product.price.currency}")
                    ProductDetailRow(label = stringResource(id = R.string.stock_label), value = "${product.stock}")
                    ProductDetailRow(label = stringResource(id = R.string.origin_label), value = product.origin)
                }
            }

            product.specifications?.let { specifications ->
                SectionView(title = stringResource(id = R.string.specifications_label)) {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
                        ProductDetailRow(label = stringResource(id = R.string.colours_label),
                                         value = specifications.colours.joinToString(", "))
                        specifications.finished?.let { finished ->
                            ProductDetailRow(label = stringResource(id = R.string.finished_label), value = finished)
                        }
                        specifications.inBox?.let { inBox ->
                            ProductDetailRow(label = stringResource(id = R.string.in_box_label), value = inBox.joinToString(", "))
                        }
                        specifications.size?.let { size ->
                            ProductDetailRow(label = stringResource(id = R.string.size_label),
                                             value = "${size.width}x${size.height}x${size.depth} ${size.unit}")
                        }
                        specifications.weight?.let { weight ->
                            ProductDetailRow(label = stringResource(id = R.string.weight_label),
                                             value = "${weight.weight} ${weight.unit}")
                        }
                    }
                }
            }

            product.warranty?.let { warranty ->
                SectionView(title = stringResource(id = R.string.warranty_information_label)) {
                    Text(text = warranty,
                         style = MaterialTheme.typography.bodySmall,
                         color = MaterialTheme.colorScheme.onSecondaryContainer)
                }
            }

            product.legal?.let { legal ->
                SectionView(title = stringResource(id = R.string.legal_label)) {
                    Text(text = legal,
                         style = MaterialTheme.typography.bodySmall,
                         color = MaterialTheme.colorScheme.onSecondaryContainer)
                }
            }

            product.warning?.let { warning ->
                SectionView(title = stringResource(id = R.string.warning_label)) {
                    Text(text = warning,
                         style = MaterialTheme.typography.bodySmall,
                         color = MaterialTheme.colorScheme.onSecondaryContainer)
                }
            }
        }
    }
}

@Composable
fun SpecificationItem(title: String, values: List<String>) {
    Column(modifier = Modifier.padding(bottom = 8.dp)) {
        Text(text = title,
             style = MaterialTheme.typography.bodyLarge,
             fontWeight = FontWeight.SemiBold,
             modifier = Modifier.padding(bottom = 8.dp))
        Text(text = values.joinToString(", "),
             style = MaterialTheme.typography.bodySmall,
             modifier = Modifier.padding(start = 8.dp))

    }
}

@Composable
fun ProductDetailRow(label: String, value: String) {
    Row(modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically) {
        Text(text = label,
             style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
             color = MaterialTheme.colorScheme.onSecondaryContainer)
        Text(text = value, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSecondaryContainer)
    }
}
