package com.premierdarkcoffee.sales.cronos.feature.product.data.remote.dto

import com.premierdarkcoffee.sales.cronos.feature.product.domain.model.product.Category
import kotlinx.serialization.Serializable

@Serializable
data class CategoryDto(val group: String, val domain: String, val subclass: String) {

    fun toCategory(): Category {
        return Category(group = group, domain = domain, subclass = subclass)
    }
}
