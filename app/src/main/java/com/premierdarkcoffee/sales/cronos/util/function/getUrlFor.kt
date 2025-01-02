package com.premierdarkcoffee.sales.cronos.util.function

import java.net.URLEncoder

fun getUrlFor(
    endpoint: String,
    keywords: String? = null,
    storeId: String? = null
): String {
    val baseUrl = "https://www.sales.premierdarkcoffee.com/$endpoint"
    val encodedKeywords = keywords?.let { URLEncoder.encode(it, "UTF-8") }
    val encodedStoreId = storeId?.let { URLEncoder.encode(it, "UTF-8") }

    val queryParams = mutableListOf<String>()

    if (encodedKeywords != null) {
        queryParams.add("keywords=$encodedKeywords")
    }
    if (storeId != null) {
        queryParams.add("storeId=$encodedStoreId")
    }

    val urlString = if (queryParams.isNotEmpty()) {
        "$baseUrl?${queryParams.joinToString("&")}"
    } else {
        baseUrl
    }
//    Log.d(TAG, "getUrlFor: $urlString")
    return urlString
}

//fun getUrlFor(endpoint: String, keywords: String? = null): String {
//    val baseUrl = "https://goldfish-app-ai4af.ondigitalocean.app/$endpoint"
//    val encodedKeywords = keywords?.let { URLEncoder.encode(it, "UTF-8") }
//
//    val queryParams = mutableListOf<String>()
//    if (encodedKeywords != null) {
//        queryParams.add("keywords=$encodedKeywords")
//    }
//
//    val urlString = if (queryParams.isNotEmpty()) {
//        "$baseUrl?${queryParams.joinToString("&")}"
//    } else {
//        baseUrl
//    }
//    Log.d(TAG, "getUrlFor: $urlString")
//    return urlString
//}