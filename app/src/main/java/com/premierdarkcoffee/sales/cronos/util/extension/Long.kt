package com.premierdarkcoffee.sales.cronos.util.extension

//
//  Long.kt
//  Maia
//
//  Created by José Ruiz on 5/8/24.
//

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Long.formatDate(): String {
    return SimpleDateFormat("EEEE, dd-MM-yy", Locale.getDefault()).format(Date(this))
}

fun Long.formatShortDate(): String {
    val currentTime = System.currentTimeMillis()
    val diff = currentTime - this

    val oneMinute = 60 * 1000
    val oneHour = 60 * oneMinute
    val oneDay = 24 * oneHour

    return when {
        diff < oneDay -> { // If the difference is less than a day, show hours and minutes
            val formatter = SimpleDateFormat("h:mm a", Locale.getDefault())
            formatter.format(Date(this))
        }

        diff < 2 * oneDay -> { // If the difference is less than two days, show 'yesterday'
            "yesterday"
        }

        diff < 7 * oneDay -> { // If the difference is less than a week, show the day of the week
            val formatter = SimpleDateFormat("EEEE", Locale.getDefault())
            formatter.format(Date(this))
        }

        diff < 30 * oneDay -> { // If the difference is less than a month, show 'last week'
            "last week"
        }

        diff < 365 * oneDay -> { // If the difference is less than a year, show the month
            val formatter = SimpleDateFormat("MMMM", Locale.getDefault())
            formatter.format(Date(this))
        }

        else -> { // If the difference is more than a year, show the month and year
            val formatter = SimpleDateFormat("MMMM yyyy", Locale.getDefault())
            formatter.format(Date(this))
        }
    }
}
