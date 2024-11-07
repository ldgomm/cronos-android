package com.premierdarkcoffee.sales.cronos.util.key

import android.content.Context
import com.premierdarkcoffee.sales.cronos.feature.product.presentation.product.viewmodel.getApiKey

fun getCronosKey(context: Context): String {
    return getApiKey(context)
}