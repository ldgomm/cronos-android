package com.premierdarkcoffee.sales.sales.feature.product.presentation.product.view.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.premierdarkcoffee.sales.cronos.feature.product.domain.state.InformationResultState

@Composable
fun InformationListView(informationResultStateList: List<InformationResultState>) {

    LazyRow(contentPadding = PaddingValues(horizontal = 11.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)) {
        items(items = informationResultStateList.filterNot { it.isDeleted }, key = { it.id }) { resultState ->
            /*val dismissState = rememberSwipeToDismissBoxState()

            if (dismissState.currentValue == SwipeToDismissBoxValue.EndToStart) {
                onSwipeToDismissInformationResultItem(resultState)
            }
            SwipeToDismissBox(state = dismissState, backgroundContent = {
                val color by animateColorAsState(when (dismissState.targetValue) {
                                                     SwipeToDismissBoxValue.Settled -> Color.LightGray
                                                     SwipeToDismissBoxValue.StartToEnd -> Color.Green
                                                     SwipeToDismissBoxValue.EndToStart -> Color.Red
                                                 }, label = "")
                Box(Modifier
                        .fillMaxSize()
                        .background(color))
            }) {*/
            InformationCardView(resultState = resultState)
        }
    }
}
