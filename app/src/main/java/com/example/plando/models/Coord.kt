package com.example.plando.models

import java.io.Serializable

data class Coord(val lt: Double, val lg: Double) : Serializable {
    fun toStringWithSpace() = "$lt  $lg"
    fun toStringWithComma() = "$lt,$lg"

    companion object {
        fun Coord?.toStringWithSpace() = this?.toStringWithSpace() ?: ""
    }
}