package com.example.plando

import com.example.plando.models.Coord
import com.example.plando.models.Event
import com.example.plando.models.MyDate
import com.example.plando.network.WeatherService
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class UnitTests {
    val client = OkHttpClient.Builder()
        .addInterceptor(UrlInterceptor())
        .build()

    val service = Retrofit.Builder()
        .baseUrl("https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()
        .create(WeatherService::class.java)

    val validEvent = Event(
        name = "Heisenberg cookoff",
        description = "Best party",
        date = MyDate.fromLong(1203883200000),
        location = Coord(35.10610741822557, -106.63922511041163),
        locationString = "322 Arvada Ave NE, Albuquerque, NM 87102, USA"
    )

    class UrlInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val request = chain.request()
            val url = request.url

            println(url)

            return chain.proceed(request)
        }
    }

    @Test
    fun check_valid_request() {
        val res = runBlocking {
            val response = service.getData(
                coord = validEvent.location!!.toStringWithComma(),
                date = validEvent.date.toSeconds()
            )

            println(response.body())
            println(response.message())
//            println(response.raw())

            response.isSuccessful
        }

        assert(res)
    }

    @Test
    fun check_bad_date_request() {
        val res = runBlocking {
            val response = service.getData(
                coord = validEvent.location!!.toStringWithComma(),
                date = 100
            )

            println("code: ${response.code()}")
            println("message: ${response.message()}")
            println("body: ${response.body()}")
            println("errorBody: ${response.errorBody()?.string()}")
            println("raw: ${response.raw()}")

            val dateError =
                response.errorBody()?.string()?.contains("Start date cannot be before") == true

            response.isSuccessful && dateError
        }

        assert(!res)
    }
}