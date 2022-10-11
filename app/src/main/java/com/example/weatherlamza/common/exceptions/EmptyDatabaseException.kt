package com.example.weatherlamza.common.exceptions

class EmptyDatabaseException(override val message: String? = "Cannot find stored location") :
    Exception()
