package com.premierdarkcoffee.sales.sales.feature.product.domain.model.product.request

import com.premierdarkcoffee.sales.cronos.feature.product.data.remote.dto.ProductDto
import kotlinx.serialization.Serializable

@Serializable
data class PutProductRequest(val key: String? = null, val product: ProductDto)
