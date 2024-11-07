package com.premierdarkcoffee.sales.cronos.feature.product.data.remote.dto

import com.premierdarkcoffee.sales.cronos.feature.product.domain.model.product.Warranty
import kotlinx.serialization.Serializable

@Serializable
data class WarrantyDto(val hasWarranty: Boolean, val details: List<String>, val months: Int) {

    fun toWarranty(): Warranty {
        return Warranty(hasWarranty = hasWarranty, details = details, months = months)
    }
}
