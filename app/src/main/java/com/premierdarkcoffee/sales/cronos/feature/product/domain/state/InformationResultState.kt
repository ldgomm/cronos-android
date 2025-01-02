package com.premierdarkcoffee.sales.cronos.feature.product.domain.state

import android.net.Uri
import com.premierdarkcoffee.sales.cronos.feature.product.domain.model.product.Image
import com.premierdarkcoffee.sales.cronos.feature.product.domain.model.product.Information

data class InformationResultState(val id: String,
                                  val title: String,
                                  val subtitle: String,
                                  val description: String,
                                  val image: Uri? = null,
                                  val path: String,
                                  val url: String,
                                  val belongs: Boolean,
                                  val place: Int,
                                  val isCreated: Boolean = false,
                                  val isDeleted: Boolean = false) {

    fun toInformation(): Information {
        return Information(id, title, subtitle, description, Image(path, url, belongs), place)
    }
}
