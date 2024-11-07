package com.premierdarkcoffee.sales.cronos.feature.product.domain.state

import com.premierdarkcoffee.sales.cronos.feature.product.domain.model.product.Image
import com.premierdarkcoffee.sales.cronos.feature.product.domain.model.product.Information
import com.premierdarkcoffee.sales.cronos.feature.product.domain.model.product.Offer
import com.premierdarkcoffee.sales.cronos.feature.product.domain.model.product.Price
import com.premierdarkcoffee.sales.cronos.feature.product.domain.model.product.Product
import com.premierdarkcoffee.sales.cronos.feature.product.domain.model.product.ProductStatus
import com.premierdarkcoffee.sales.cronos.feature.product.domain.model.product.Specifications
import com.premierdarkcoffee.sales.cronos.feature.product.domain.model.product.Warranty
import com.premierdarkcoffee.sales.cronos.feature.product.domain.model.product.Category
import com.premierdarkcoffee.sales.cronos.feature.product.domain.model.product.CreditCard
import java.util.UUID

data class AddEditProductState(
    val id: String = UUID.randomUUID().toString(),
    val name: String = "",
    val label: String = "",
    val owner: String = "",
    val year: String = "2024",
    val model: String = "N/A",
    val description: String = "",
    val category: Category = Category("", "", ""),
    val price: Price = Price(
        0.0, offer = Offer(true, 10), creditCard = CreditCard(
            withoutInterest = 3, withInterest = 12, freeMonths = 0
        )
    ),
    val stock: Int = 0,
    var image: Image = Image(path = null, url = "", belongs = false),
    val origin: String = "",
    val date: Long = System.currentTimeMillis(),
//    var overview: MutableList<Information> = mutableListOf(),
//    val overviewResult: MutableList<InformationResultState> = mutableListOf(),
    val keywords: MutableList<String> = mutableListOf(),
    val specifications: Specifications? = null,
    val warranty: Warranty? = null,
    val legal: String? = null,
    val warning: String? = null,
    val status: ProductStatus = ProductStatus(
        isBlackFriday = true,
        isChristmas = false,
        isValentinesDay = false,
        isMothersDay = false,
        isFathersDay = false,
        isLaborDay = false,
        isCyberMonday = false,
        isNewYearsDay = false,
        isEaster = false,
        isHalloween = false,
        isThanksgiving = false,
        isIndependenceDay = false
    ),
    val storeId: String? = null
) {

    fun toProduct(
        image: Image,
        overview: List<Information>
    ): Product {
        this.image = image
        try {
            return Product(
                id = id,
                name = name,
                label = label,
                owner = owner,
                year = year,
                model = model,
                description = description,
                category = category,
                price = price,
                stock = stock,
                image = image,
                origin = origin,
                date = date,
                overview = overview,
                keywords = keywords,
                specifications = specifications,
                warranty = warranty,
                legal = legal,
                status = status,
                warning = warning
            )
        } catch (e: Exception) {
            throw e
        }
    }

//    private suspend fun getUrlForNewMainPhoto(): ImageInfo? {
//        Log.d(TAG, "Tracking | AddEditProductState | getUrlForNewMainPhoto: Path(${image.path}), Belongs(${image.belongs})")
//        return if (!image.belongs) {
//            image.path?.let { path ->
//                downloadImageDataFromFirebaseByReference(path)
//            }
//        } else {
//            image.path?.let { ImageInfo(path = it, url = image.url) }
//        }
//    }
}
