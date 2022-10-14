package com.example.weatherlamza.utils.extensions

import org.joda.time.Instant
import org.joda.time.LocalDate
import org.joda.time.LocalTime
import org.joda.time.format.DateTimeFormat


val String.toLocalDate: LocalDate
    get() = LocalDate.parse(this)

val String.toLocalTime: LocalTime
    get() = Instant.ofEpochSecond(this.toLong()).toDateTime().toLocalTime()


fun String.getHoursFromLocalTime(pattern: String = "HH:mm"): String {
    val timeFormatter = DateTimeFormat.forPattern(pattern)
    return timeFormatter.print(this.toLocalTime)
}


