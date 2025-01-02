package com.premierdarkcoffee.sales.cronos.feature.product.domain.model.product

import com.premierdarkcoffee.sales.cronos.feature.product.data.remote.dto.OfferDto

data class Offer(val isActive: Boolean, val discount: Int) {

    fun toOfferDto(): OfferDto {
        return OfferDto(isActive = isActive, discount = discount)
    }
}
