package com.premierdarkcoffee.sales.sales.util.constant

import kotlinx.serialization.Serializable

@Serializable
data class Mi(val name: String) {
    val id: String
        get() = name
}

@Serializable
data class Ni(val name: String) {
    val id: String
        get() = name
}

@Serializable
data class Xi(val name: String) {
    val id: String
        get() = name
}

typealias Categories = Map<Mi, Map<Ni, List<Xi>>>
