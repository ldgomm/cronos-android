package com.premierdarkcoffee.sales.cronos.feature.product.domain.model.product

import com.premierdarkcoffee.sales.cronos.feature.product.data.remote.dto.CreditCardDto

data class CreditCard(val withoutInterest: Int, val withInterest: Int, val freeMonths: Int) {

    fun toCreditCardDto(): CreditCardDto {
        return CreditCardDto(withoutInterest = withoutInterest, withInterest = withInterest, freeMonths = freeMonths)
    }

}
