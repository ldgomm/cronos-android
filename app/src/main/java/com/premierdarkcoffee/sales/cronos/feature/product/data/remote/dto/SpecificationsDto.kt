package com.premierdarkcoffee.sales.cronos.feature.product.data.remote.dto

import com.premierdarkcoffee.sales.cronos.feature.product.domain.model.product.Specifications
import kotlinx.serialization.Serializable

@Serializable
data class SpecificationsDto(val colours: List<String>,
                             val finished: String? = null,
                             val inBox: List<String>? = null,
                             val size: SizeDto? = null,
                             val weight: WeightDto? = null) {

    fun toSpecifications(): Specifications {
        return Specifications(colours = colours,
                              finished = finished,
                              inBox = inBox,
                              size = size?.toSize(),
                              weight = weight?.toWeight())
    }
}
