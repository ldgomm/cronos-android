package com.premierdarkcoffee.sales.cronos.feature.product.domain.model.product

import com.premierdarkcoffee.sales.cronos.feature.product.data.remote.dto.ProductDto
import com.premierdarkcoffee.sales.cronos.feature.product.data.remote.dto.ProductStatusDto

data class Product(
    val id: String,
    val name: String,
    val label: String? = null,
    val owner: String? = null,
    val year: String? = "2024",
    val model: String,
    val description: String,
    val category: Category,
    val price: Price,
    val stock: Int,
    val image: Image,
    val origin: String,
    val date: Long,
    var overview: List<Information>,
    val keywords: List<String>? = null,
    val specifications: Specifications? = null,
    val warranty: Warranty? = null,
    val legal: String? = null,
    val warning: String? = null,
    val storeId: String? = null
) {

    fun toProductDto(): ProductDto {
        return ProductDto(
            id = id,
            name = name,
            label = label,
            owner = owner,
            year = year,
            model = model,
            description = description,
            category = category.toCategoryDto(),
            price = price.toPriceDto(),
            stock = stock,
            image = image.toImageDto(),
            origin = origin,
            date = date,
            overview = overview.map { it.toInformationDto() },
            keywords = keywords,
            specifications = specifications?.toSpecificationsDto(),
            warranty = warranty?.toWarrantyDto(),
            legal = legal,
            warning = warning,
            storeId = storeId
        )
    }
}