package com.mike.maintenancealarm.utils

object DateFactory {
    fun createDate(year: Int, month: Int, dayOfMonth: Int): java.util.Date {
        val calendar = java.util.Calendar.getInstance()
        calendar.set(java.util.Calendar.YEAR, year)
        calendar.set(java.util.Calendar.MONTH, month)
        calendar.set(java.util.Calendar.DAY_OF_MONTH, dayOfMonth)
        return calendar.time
    }
}