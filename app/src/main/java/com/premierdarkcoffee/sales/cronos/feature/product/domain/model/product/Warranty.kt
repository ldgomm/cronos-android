package com.premierdarkcoffee.sales.cronos.feature.product.domain.model.product

import com.premierdarkcoffee.sales.cronos.feature.product.data.remote.dto.WarrantyDto

data class Warranty(val hasWarranty: Boolean, val details: List<String>, val months: Int) {

    fun toWarrantyDto(): WarrantyDto {
        return WarrantyDto(hasWarranty = hasWarranty, details = details, months = months)
    }

}
