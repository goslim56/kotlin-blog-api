package com.goslim.kotlinblogapi.util

import java.text.SimpleDateFormat


object DateFormatUtil {
    const val DEFAULT_PATTERN = "yyyy-MM-dd'T'HH:mm:ss"

    fun changeDateFormat(oldFormat: String, newFormat: String = DEFAULT_PATTERN, dateString: String): String {
        val simpleDateFormat = SimpleDateFormat(oldFormat)
        val date = simpleDateFormat.parse(dateString)
        simpleDateFormat.applyPattern(newFormat)
        return simpleDateFormat.format(date)
    }
}