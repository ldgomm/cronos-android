package com.premierdarkcoffee.sales.cronos.feature.product.presentation.product.view.product

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.premierdarkcoffee.sales.cronos.feature.product.domain.model.product.Product
import com.premierdarkcoffee.sales.cronos.util.constant.Categories
import com.premierdarkcoffee.sales.cronos.util.constant.Mi
import com.premierdarkcoffee.sales.cronos.util.constant.Ni
import com.premierdarkcoffee.sales.cronos.util.constant.Xi

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterView(selectedMi: Mi?,
               selectedNi: Ni?,
               selectedXi: Xi?,
               categories: Categories,
               products: List<Product>,
               onMiSelected: (Mi?) -> Unit,
               onNiSelected: (Ni?) -> Unit,
               onXiSelected: (Xi?) -> Unit) {
    val nonEmptyCategories: Categories by remember {
        derivedStateOf {
            val result: MutableMap<Mi, MutableMap<Ni, List<Xi>>> = mutableMapOf()
            for ((mi, niDict) in categories) {
                val nonEmptyNiDict: MutableMap<Ni, List<Xi>> = mutableMapOf()
                for ((ni, xiList) in niDict) {
                    val nonEmptyXiList = xiList.filter { xi ->
                        products.any { it.category.group == mi.name && it.category.domain == ni.name && it.category.subclass == xi.name }
                    }
                    if (nonEmptyXiList.isNotEmpty()) {
                        nonEmptyNiDict[ni] = nonEmptyXiList
                    }
                }
                if (nonEmptyNiDict.isNotEmpty()) {
                    result[mi] = nonEmptyNiDict
                }
            }
            result
        }
    }

    Column(verticalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.padding(horizontal = 16.dp)) {
        // Group Picker
        Text("Group", style = MaterialTheme.typography.bodyLarge)
        ExposedDropdownMenuBox(expanded = false, onExpandedChange = {}) {
            TextField(value = selectedMi?.name ?: "All",
                      onValueChange = {},
                      readOnly = true,
                      trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = false) })
            ExposedDropdownMenu(expanded = false, onDismissRequest = {}) {
                DropdownMenuItem(onClick = { onMiSelected(null) }, text = { Text("All") })
                nonEmptyCategories.keys.sortedBy { it.name }.forEach { mi ->
                    DropdownMenuItem(onClick = { onMiSelected(mi) }, text = { Text(mi.name) })
                }
            }
        }

        // Category Picker
        selectedMi?.let {
            Text("Category", style = MaterialTheme.typography.bodyLarge)
            ExposedDropdownMenuBox(expanded = false, onExpandedChange = {}) {
                TextField(value = selectedNi?.name ?: "All",
                          onValueChange = {},
                          readOnly = true,
                          trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = false) })
                ExposedDropdownMenu(expanded = false, onDismissRequest = {}) {
                    DropdownMenuItem(onClick = { onNiSelected(null) }, text = { Text("All") })
                    nonEmptyCategories[selectedMi]?.keys?.sortedBy { it.name }?.forEach { ni ->
                        DropdownMenuItem(onClick = { onNiSelected(ni) }, text = { Text(ni.name) })
                    }
                }
            }
        }

        // Subcategory Picker
        if (selectedMi != null && selectedNi != null) {
            Text("Subcategory", style = MaterialTheme.typography.bodyLarge)
            ExposedDropdownMenuBox(expanded = false, onExpandedChange = {}) {
                TextField(value = selectedXi?.name ?: "All",
                          onValueChange = {},
                          readOnly = true,
                          trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = false) })
                ExposedDropdownMenu(expanded = false, onDismissRequest = {}) {
                    DropdownMenuItem(onClick = { onXiSelected(null) }, text = { Text("All") })
                    nonEmptyCategories[selectedMi]?.get(selectedNi)?.sortedBy { it.name }?.forEach { xi ->
                        DropdownMenuItem(onClick = { onXiSelected(xi) }, text = { Text(xi.name) })
                    }
                }
            }
        }
    }
}
