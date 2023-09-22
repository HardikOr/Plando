package com.example.plando.network

import com.example.plando.BuildConfig
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WeatherService {
    @GET("{coord}/{date}")
    suspend fun getData(
        @Path("coord") coord: String,
        @Path("date") date: Long,
        @Query("include") include: String = "days",
        @Query("elements") elemets: String = "conditions,icon",
        @Query("key") apiKey: String = BuildConfig.WEATHER_API_KEY,
        @Query("unitGroup") units: String = "metric"
    ) : Response<WeatherResponse>
}