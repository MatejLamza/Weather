package com.example.weatherlamza.utils.extensions


fun Int.toNamedDayOfTheWeek(): String =
    when (this) {
        1 -> "Monday"
        2 -> "Tuesday"
        3 -> "Wednesday"
        4 -> "Thursday"
        5 -> "Friday"
        6 -> "Saturday"
        7 -> "Sunday"
        else -> throw IllegalStateException("Unknown day of the week")
    }
