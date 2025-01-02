package com.premierdarkcoffee.sales.cronos.feature.product.data.remote.dto

import com.premierdarkcoffee.sales.cronos.feature.product.domain.model.product.Price
import kotlinx.serialization.Serializable

@Serializable
data class PriceDto(val amount: Double,
                    val currency: String = "USD",
                    val offer: OfferDto,
                    val creditCard: CreditCardDto? = null) {

    fun toPrice(): Price {
        return Price(amount = amount, currency = currency, offer = offer.toOffer(), creditCard = creditCard?.toCreditCard())
    }
}
