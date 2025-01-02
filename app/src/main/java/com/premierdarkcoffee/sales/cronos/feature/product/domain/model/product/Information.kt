package com.premierdarkcoffee.sales.cronos.feature.product.domain.model.product

import com.premierdarkcoffee.sales.cronos.feature.product.data.remote.dto.InformationDto

data class Information(val id: String,
                       val title: String,
                       val subtitle: String,
                       val description: String,
                       val image: Image,
                       val place: Int) {

    fun toInformationDto(): InformationDto {
        return InformationDto(id = id,
                              title = title,
                              subtitle = subtitle,
                              description = description,
                              image = image.toImageDto(),
                              place = place)
    }

}


//    fun toInformationResultState(): InformationResultState {
//        return InformationResultState(id = id,
//                                      title = title,
//                                      subtitle = subtitle,
//                                      description = description,
//                                      path = image.path ?: "",
//                                      belongs = image.belongs,
//                                      place = place)
//    }