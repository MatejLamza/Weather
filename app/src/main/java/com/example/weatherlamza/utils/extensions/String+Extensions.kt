package com.example.weatherlamza.utils.extensions

import org.joda.time.LocalDate


val String.toLocalDate: LocalDate
    get() = LocalDate.parse(this)
