package com.premierdarkcoffee.sales.cronos.feature.product.domain.usecase

import com.premierdarkcoffee.sales.sales.feature.product.domain.model.product.request.PostProductRequest
import com.premierdarkcoffee.sales.cronos.feature.product.domain.serviceable.MessageResponse
import com.premierdarkcoffee.sales.cronos.feature.product.domain.serviceable.ProductServiceable
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AddProductUseCase @Inject constructor(private val productServiceable: ProductServiceable) {

    operator fun invoke(
        url: String,
        request: PostProductRequest, apiKey: String
    ): Flow<Result<MessageResponse>> {
        return productServiceable.addProduct(url, request, apiKey)
    }
}
