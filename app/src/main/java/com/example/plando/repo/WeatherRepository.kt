package com.example.plando.repo

import com.example.plando.di.IoDispatcher
import com.example.plando.models.Coord
import com.example.plando.models.Event
import com.example.plando.models.MyDate
import com.example.plando.network.Day
import com.example.plando.network.WeatherService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherRepository @Inject constructor(
    private val weatherService: WeatherService,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {
    suspend fun getEventsWithWeather(events: List<Event>) = withContext(ioDispatcher) {
        events.map { getEventWeatherOrNull(it) }
    }

    suspend fun getWeatherDay(coord: Coord, date: MyDate): Day? = withContext(ioDispatcher) {
        val response =
            weatherService.getData(coord = coord.toStringWithComma(), date = date.toSeconds())

        if (response.isSuccessful) {
            response.body()?.days?.first()
        } else null
    }

    private suspend fun getEventWeatherOrNull(event: Event): Event? = withContext(ioDispatcher) {
        var res: Event? = null

        event.copy().apply {
            this.location?.let { coord ->
                val response = weatherService.getData(
                    coord = coord.toStringWithComma(),
                    date = this.date.toSeconds()
                )

                if (response.isSuccessful) {
                    response.body()?.let {
                        this.weather = it.days.first().conditions
                        this.weatherIcon = it.days.first().icon

                        res = this
                    }
                }
            }
        }

        res
    }
}