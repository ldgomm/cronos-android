package com.premierdarkcoffee.sales.cronos.feature.product.data.remote.dto

import com.premierdarkcoffee.sales.cronos.feature.product.domain.model.product.Size
import kotlinx.serialization.Serializable

@Serializable
data class SizeDto(val width: Double, val height: Double, val depth: Double, val unit: String) {

    fun toSize(): Size {
        return Size(width = width, height = height, depth = depth, unit = unit)
    }
}
