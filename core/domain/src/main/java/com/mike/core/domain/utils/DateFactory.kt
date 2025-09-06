package com.mike.core.domain.utils

import java.util.Calendar
import java.util.Date

object DateFactory {
    fun createDate(year: Int, month: Int, dayOfMonth: Int): Date {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, month)
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
        return calendar.time
    }
}