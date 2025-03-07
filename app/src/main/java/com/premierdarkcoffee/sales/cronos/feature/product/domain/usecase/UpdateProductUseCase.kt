package com.premierdarkcoffee.sales.cronos.feature.product.domain.usecase

import com.premierdarkcoffee.sales.cronos.feature.product.domain.serviceable.MessageResponse
import com.premierdarkcoffee.sales.cronos.feature.product.domain.serviceable.ProductServiceable
import com.premierdarkcoffee.sales.sales.feature.product.domain.model.product.request.PutProductRequest
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpdateProductUseCase @Inject constructor(private val productServiceable: ProductServiceable) {

    operator fun invoke(url: String, request: PutProductRequest, apiKey: String): Flow<Result<MessageResponse>> {
        return productServiceable.updateProduct(url, request, apiKey)
    }
}
