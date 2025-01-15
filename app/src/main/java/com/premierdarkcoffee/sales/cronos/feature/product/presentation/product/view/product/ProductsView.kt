package com.premierdarkcoffee.sales.cronos.feature.product.presentation.product.view.product

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.premierdarkcoffee.sales.cronos.R
import com.premierdarkcoffee.sales.cronos.feature.product.domain.state.ProductsState
import com.premierdarkcoffee.sales.cronos.feature.product.presentation.product.view.common.ProductCardView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductsView(
    productsState: ProductsState,
    searchText: String,
    onSearchTextChange: (String) -> Unit,
    clearSearchText: () -> Unit,
    onNavigateToProductView: (String) -> Unit,
    onAddNewProductButtonClicked: () -> Unit,
    onNavigateToSettingsButtonClicked: () -> Unit,
    onRefresh: () -> Unit
) {
    var active by rememberSaveable { mutableStateOf(false) }
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val isRefreshing by remember { mutableStateOf(false) }
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing)

    Scaffold(modifier = Modifier
        .background(MaterialTheme.colorScheme.surface)
        .statusBarsPadding()
        .navigationBarsPadding()
        .nestedScroll(scrollBehavior.nestedScrollConnection), topBar = {
        TopAppBar(title = {
            Text(
                text = "${stringResource(id = R.string.products_label)}${productsState.products?.size}",
                style = titleStyle
            )
        }, actions = {
            IconButton(onAddNewProductButtonClicked) {
                Icon(imageVector = ImageVector.vectorResource(R.drawable.add), contentDescription = null)
            }
            IconButton(onNavigateToSettingsButtonClicked) {
                Icon(imageVector = ImageVector.vectorResource(R.drawable.settings), contentDescription = null)
            }
        }, scrollBehavior = scrollBehavior
        )
    }) { paddingValues ->
        SwipeRefresh(
            state = swipeRefreshState, onRefresh = onRefresh, // Trigger the refresh action
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = paddingValues.calculateBottomPadding())
        ) {
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(bottom = paddingValues.calculateBottomPadding())
            ) {
                SearchBar(query = searchText,
                          onQueryChange = onSearchTextChange,
                          onSearch = { active = false },
                          active = active,
                          onActiveChange = { active = it },
                          modifier = Modifier
                              .fillMaxWidth()
                              .padding(horizontal = 12.dp)
                              .padding(top = paddingValues.calculateTopPadding()),
                          placeholder = { Text("Search") },
                          leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = null) },
                          trailingIcon = {
                              if (active) {
                                  Icon(
                                      imageVector = Icons.Default.Close,
                                      contentDescription = null,
                                      modifier = Modifier.clickable {
                                          if (searchText.isNotEmpty()) {
                                              clearSearchText()
                                          } else {
                                              active = false
                                          }
                                      })
                              }
                          }) {
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        productsState.products?.let { products ->
                            items(products) { product ->
                                ProductCardView(product = product, onNavigateToProductView)
                            }
                        }
                    }
                }

                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    productsState.products?.let { products ->
                        items(products) { product ->
                            ProductCardView(product = product, onNavigateToProductView)
                        }
                    }
                }
            }
        }
    }
}

val titleStyle: TextStyle = TextStyle(
    fontSize = 27.sp, fontWeight = FontWeight.Bold, fontStyle = FontStyle.Normal
)