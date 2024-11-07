package com.premierdarkcoffee.sales.cronos.feature.product.domain.model.product

import com.premierdarkcoffee.sales.cronos.feature.product.data.remote.dto.SizeDto

data class Size(val width: Double, val height: Double, val depth: Double, val unit: String) {

    fun toSizeDto(): SizeDto {
        return SizeDto(width = width, height = height, depth = depth, unit = unit)
    }

}
