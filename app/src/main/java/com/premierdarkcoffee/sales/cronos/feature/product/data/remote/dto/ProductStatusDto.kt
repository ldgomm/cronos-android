package com.premierdarkcoffee.sales.cronos.feature.product.data.remote.dto

import com.premierdarkcoffee.sales.cronos.feature.product.domain.model.product.ProductStatus
import kotlinx.serialization.Serializable

@Serializable
data class ProductStatusDto(
    val isBlackFriday: Boolean,      // Black Friday (late November)
    val isCyberMonday: Boolean,      // Following Black Friday (late November)
    val isThanksgiving: Boolean,     // Fourth Thursday in November
    val isChristmas: Boolean,        // December 25
    val isNewYearsDay: Boolean,      // January 1
    val isValentinesDay: Boolean,    // February 14
    val isEaster: Boolean,           // Varies between March and April
    val isLaborDay: Boolean,         // May 1 in many countries
    val isMothersDay: Boolean,       // Second Sunday in May (varies by region)
    val isFathersDay: Boolean,       // Third Sunday in June (varies by region)
    val isIndependenceDay: Boolean,  // July 4 in the U.S. (or respective date for other countries)
    val isHalloween: Boolean         // October 31
) {

    fun toProductStatus(): ProductStatus {
        return ProductStatus(
            isBlackFriday,
            isChristmas,
            isValentinesDay,
            isMothersDay,
            isFathersDay,
            isLaborDay,
            isCyberMonday,
            isNewYearsDay,
            isEaster,
            isHalloween,
            isThanksgiving,
            isIndependenceDay
        )
    }
}
