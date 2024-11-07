package com.premierdarkcoffee.sales.cronos.feature.product.domain.model.product

import com.premierdarkcoffee.sales.cronos.feature.product.data.remote.dto.CategoryDto

data class Category(val group: String, val domain: String, val subclass: String) {

    fun toCategoryDto(): CategoryDto {
        return CategoryDto(group = group, domain = domain, subclass = subclass)

    }
}
