package com.premierdarkcoffee.sales.cronos.feature.product.data.remote.dto

import com.premierdarkcoffee.sales.cronos.feature.product.domain.model.product.Weight
import kotlinx.serialization.Serializable

@Serializable
data class WeightDto(val weight: Double, val unit: String) {

    fun toWeight(): Weight {
        return Weight(weight = weight, unit = unit)
    }
}
