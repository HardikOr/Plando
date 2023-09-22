package com.example.plando.models

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class MyDate(val date: Date?) {
    constructor(year: Int, month: Int, day: Int) : this(Date(year, month, day))

    fun toFormatString(pattern: String) = if (date == null) {
        ""
    } else {
        SimpleDateFormat(pattern, Locale.getDefault()).format(date).toString()
    }

    fun toDMYString() = toFormatString("dd.MM.yyyy")
    fun toYMDDashString() = toFormatString("yyyy-MM-dd")
    override fun toString() = date.toString()

    fun toMillis() = date?.time ?: -1L
    fun toSeconds() = if (date == null) -1L else date.time / 1000

    companion object {
        fun fromLong(time: Long) = if (time == -1L) MyDate(null) else MyDate(Date(time))
    }
}