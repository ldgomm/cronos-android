package com.premierdarkcoffee.sales.cronos.feature.product.domain.usecase

import com.premierdarkcoffee.sales.cronos.feature.product.domain.serviceable.ProductServiceable
import com.premierdarkcoffee.sales.cronos.util.constant.Categories
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor(private val productServiceable: ProductServiceable) {

    operator fun invoke(url: String): Flow<Result<Categories>> {
        return productServiceable.getCategories(endpoint = url)
    }
}
