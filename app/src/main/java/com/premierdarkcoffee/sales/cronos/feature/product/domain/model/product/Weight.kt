package com.premierdarkcoffee.sales.cronos.feature.product.domain.model.product

import com.premierdarkcoffee.sales.cronos.feature.product.data.remote.dto.WeightDto

data class Weight(val weight: Double, val unit: String) {

    fun toWeightDto(): WeightDto {
        return WeightDto(weight = weight, unit = unit)
    }
}
