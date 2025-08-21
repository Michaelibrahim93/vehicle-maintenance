package com.mike.maintenancealarm.utils.extensions

import java.util.Calendar
import java.util.Date

fun Date.toCalendar(): Calendar {
    return Calendar.getInstance().apply {
        time = this@toCalendar
    }
}

val Calendar.year: Int
    get() = this.get(Calendar.YEAR)
val Calendar.month: Int
    get() = this.get(Calendar.MONTH)
val Calendar.dayOfMonth: Int
    get() = this.get(Calendar.DAY_OF_MONTH)