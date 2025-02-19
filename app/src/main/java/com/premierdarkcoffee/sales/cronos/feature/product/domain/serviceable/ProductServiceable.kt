package com.premierdarkcoffee.sales.cronos.feature.product.domain.serviceable

import com.premierdarkcoffee.sales.cronos.feature.product.data.remote.dto.ProductDto
import com.premierdarkcoffee.sales.cronos.feature.product.domain.model.product.request.PostProductRequest
import com.premierdarkcoffee.sales.sales.feature.product.domain.model.product.request.PutProductRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.Serializable

interface ProductServiceable {

    fun getProducts(endpoint: String, apiKey: String): Flow<Result<List<ProductDto>>>

    fun addProduct(url: String, request: PostProductRequest, apiKey: String): Flow<Result<MessageResponse>>

    fun updateProduct(url: String, request: PutProductRequest, apiKey: String): Flow<Result<MessageResponse>>
}

@Serializable
data class MessageResponse(val message: String)
