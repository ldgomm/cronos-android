package com.premierdarkcoffee.sales.cronos.feature.product.domain.state

import com.premierdarkcoffee.sales.cronos.feature.product.domain.model.product.Product

data class ProductsState(val products: List<Product>? = null)
