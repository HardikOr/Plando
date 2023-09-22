package com.example.plando.room

import androidx.room.TypeConverter
import com.example.plando.models.Coord
import com.example.plando.models.MyDate

class EventTypeConverters {
    @TypeConverter
    fun dateToLong(value: MyDate) = value.toMillis()

    @TypeConverter
    fun LongToDate(value: Long) = MyDate.fromLong(value)

    @TypeConverter
    fun coordToString(value: Coord?) = if (value == null) "" else "${value.lt},${value.lg}"

    @TypeConverter
    fun stringToCoord(value: String): Coord? = if (value == "") {
        null
    } else {
        val spl = value.split(",")
        Coord(spl[0].toDouble(), spl[1].toDouble())
    }
}