package com.premierdarkcoffee.sales.cronos.feature.product.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
enum class ProductStatusDto {
    BLACKFRIDAY, CYBERMONDAY, THANKSGIVING, CHRISTMAS, NEWYEARSDAY, VALENTINESDAY, EASTER, LABORDAY, MOTHERSDAY, FATHERSDAY, INDEPENDENCEDAY, HALLOWEEN, NOEVENT
}