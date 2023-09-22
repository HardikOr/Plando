package com.example.plando.network

data class WeatherResponse(
    val days: List<Day>
)

data class Day(
    val conditions: String,
    val icon: String
)