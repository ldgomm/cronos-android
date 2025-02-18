package com.premierdarkcoffee.sales.cronos.feature.product.presentation.product.view.product

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
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
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.premierdarkcoffee.sales.cronos.R
import com.premierdarkcoffee.sales.cronos.feature.product.domain.serviceable.Group
import com.premierdarkcoffee.sales.cronos.feature.product.domain.state.ProductsState
import com.premierdarkcoffee.sales.cronos.feature.product.presentation.product.view.common.ProductCardView
import com.premierdarkcoffee.sales.cronos.feature.product.presentation.product.view.product.component.HorizontalChips

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductsView(productsState: ProductsState,
                 searchText: String,
                 groups: List<Group>,
                 onSearchTextChange: (String) -> Unit,
                 clearSearchText: () -> Unit,
                 onNavigateToProductView: (String) -> Unit,
                 onAddNewProductButtonClicked: () -> Unit,
                 onNavigateToSettingsButtonClicked: () -> Unit,
                 onRefresh: () -> Unit) {
    var active by rememberSaveable { mutableStateOf(false) }
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    // Refresh + states
    val isRefreshing by remember { mutableStateOf(false) }
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing)

    // Filter state
    var selectedGroup by rememberSaveable { mutableStateOf<String?>(null) }
    var selectedDomain by rememberSaveable { mutableStateOf<String?>(null) }
    var selectedSubclass by rememberSaveable { mutableStateOf<String?>(null) }

    // All products
    val allProducts = productsState.products.orEmpty()

    // Filter out groups/domains/subclasses that have no products
    val filteredGroups = groups.filter { group ->
        allProducts.any { it.category.group == group.name }
    }
    val selectedGroupObj = filteredGroups.find { it.name == selectedGroup }

    val filteredDomains = selectedGroupObj?.domains?.filter { domain ->
        allProducts.any {
            it.category.group == selectedGroup && it.category.domain == domain.name
        }
    }.orEmpty()
    val selectedDomainObj = filteredDomains.find { it.name == selectedDomain }

    val filteredSubclasses = selectedDomainObj?.subclasses?.filter { subclass ->
        allProducts.any {
            it.category.group == selectedGroup && it.category.domain == selectedDomain && it.category.subclass == subclass.name
        }
    }.orEmpty()

    // Filter products by selections + search
    val filteredProducts = allProducts.filter { product ->
        (selectedGroup == null || product.category.group == selectedGroup) && (selectedDomain == null || product.category.domain == selectedDomain) && (selectedSubclass == null || product.category.subclass == selectedSubclass) && (searchText.isEmpty() || product.name.contains(
                searchText,
                ignoreCase = true))
    }

    Scaffold(modifier = Modifier
        .background(MaterialTheme.colorScheme.surface)
        .statusBarsPadding()
        .navigationBarsPadding()
        .nestedScroll(scrollBehavior.nestedScrollConnection), topBar = {
        TopAppBar(title = {
            Text(text = "${stringResource(id = R.string.products_label)}${filteredProducts.size}",
                 style = MaterialTheme.typography.titleLarge)
        }, actions = {
            IconButton(onClick = onAddNewProductButtonClicked) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
            }
            IconButton(onClick = onNavigateToSettingsButtonClicked) {
                Icon(imageVector = Icons.Default.Settings, contentDescription = "Settings")
            }
        }, scrollBehavior = scrollBehavior)
    }) { paddingValues ->
        SwipeRefresh(state = swipeRefreshState,
                     onRefresh = onRefresh,
                     modifier = Modifier
                         .fillMaxSize()
                         .padding(bottom = paddingValues.calculateBottomPadding())) {
            Column(Modifier
                       .fillMaxSize()
                       .padding(bottom = paddingValues.calculateBottomPadding())) {
                // SearchBar
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
                          leadingIcon = {
                              Icon(imageVector = Icons.Default.Search, contentDescription = null)
                          },
                          trailingIcon = {
                              if (active) {
                                  Icon(imageVector = Icons.Default.Close,
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
                    // Just an example nested lazy list
                    LazyColumn(Modifier.fillMaxSize()) {
                        items(allProducts) { product ->
                            ProductCardView(product, onNavigateToProductView)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // 1) Groups row
                if (filteredGroups.isNotEmpty()) {
                    HorizontalChips(items = filteredGroups.map { it.name },
                                    selectedItem = selectedGroup,
                                    onItemSelected = { clickedGroupName ->
                                        selectedGroup = clickedGroupName
                                        selectedDomain = null
                                        selectedSubclass = null
                                    })
                }

                // 2) Domains row
                if (filteredDomains.isNotEmpty() && selectedGroup != null) {
                    HorizontalChips(items = filteredDomains.map { it.name },
                                    selectedItem = selectedDomain,
                                    onItemSelected = { clickedDomainName ->
                                        selectedDomain = clickedDomainName
                                        selectedSubclass = null
                                    })
                }

                // 3) Subclasses row
                if (filteredSubclasses.isNotEmpty() && selectedDomain != null) {
                    HorizontalChips(items = filteredSubclasses.map { it.name },
                                    selectedItem = selectedSubclass,
                                    onItemSelected = { clickedSubclassName ->
                                        selectedSubclass = clickedSubclassName
                                    })
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Final filtered products
                if (filteredProducts.isEmpty()) {
                    Text(text = "noProductsFound",
                         style = MaterialTheme.typography.bodyLarge,
                         modifier = Modifier
                             .semantics { contentDescription = "noProductsFound" }
                             .padding(16.dp))
                } else {
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        items(filteredProducts) { product ->
                            ProductCardView(product, onNavigateToProductView)
                        }
                    }
                }
            }
        }
    }
}


val titleStyle: TextStyle = TextStyle(fontSize = 27.sp, fontWeight = FontWeight.Bold, fontStyle = FontStyle.Normal)