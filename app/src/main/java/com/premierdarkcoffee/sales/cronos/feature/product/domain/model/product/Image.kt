package com.premierdarkcoffee.sales.cronos.feature.product.domain.model.product

import com.premierdarkcoffee.sales.cronos.feature.product.data.remote.dto.ImageDto

data class Image(val path: String? = null, val url: String, val belongs: Boolean) {

    fun toImageDto(): ImageDto {
        return ImageDto(path = path, url = url, belongs = belongs)
    }
}
