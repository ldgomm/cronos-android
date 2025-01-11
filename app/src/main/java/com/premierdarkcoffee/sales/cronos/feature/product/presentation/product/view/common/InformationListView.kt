package com.premierdarkcoffee.sales.cronos.feature.product.presentation.product.view.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.premierdarkcoffee.sales.cronos.feature.product.domain.state.InformationResultState

@Composable
fun InformationListView(informationResultStateList: List<InformationResultState>) {

    LazyRow(contentPadding = PaddingValues(horizontal = 11.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.semantics {
                contentDescription = "Horizontal list of information cards"
            }) {
        items(items = informationResultStateList.filterNot { it.isDeleted }, key = { it.id }) { resultState ->
            InformationCardView(resultState = resultState)
        }
    }
}
