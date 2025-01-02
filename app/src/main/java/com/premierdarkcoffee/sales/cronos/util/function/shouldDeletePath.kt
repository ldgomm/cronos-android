package com.premierdarkcoffee.sales.cronos.util.function

fun shouldDeletePath(path: String): Boolean {
    val basePath = "sales/stores"
    return path.startsWith("$basePath/") && path.length > basePath.length
}
