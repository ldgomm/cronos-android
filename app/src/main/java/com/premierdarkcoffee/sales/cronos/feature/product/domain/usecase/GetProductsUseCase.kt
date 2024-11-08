package com.premierdarkcoffee.sales.cronos.feature.product.domain.usecase

import com.premierdarkcoffee.sales.cronos.feature.product.data.remote.dto.ProductDto
import com.premierdarkcoffee.sales.cronos.feature.product.domain.serviceable.ProductServiceable
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProductsUseCase @Inject constructor(private val productServiceable: ProductServiceable) {

    operator fun invoke(url: String, apiKey: String): Flow<Result<List<ProductDto>>> {
        return productServiceable.getProducts(url,apiKey)
    }
}
