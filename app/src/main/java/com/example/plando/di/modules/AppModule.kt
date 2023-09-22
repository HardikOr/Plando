package com.example.plando.di.modules

import android.content.Context
import androidx.room.Room
import com.example.plando.di.ApiUrlBaseString
import com.example.plando.network.WeatherService
import com.example.plando.room.EventDAO
import com.example.plando.room.EventRoomDatabase
import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module(includes = [ContextModule::class])
class AppModule {
    @Singleton
    @Provides
    fun getPicasso(context: Context): Picasso =
        Picasso.Builder(context)
//            .indicatorsEnabled(true)
            .build()

    @Singleton
    @Provides
    fun getRetrofit(@ApiUrlBaseString url: String): Retrofit =
        Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Singleton
    @Provides
    fun getWeatherService(retrofit: Retrofit): WeatherService =
        retrofit.create(WeatherService::class.java)

    @Singleton
    @Provides
    fun getEventDAO(database: EventRoomDatabase): EventDAO = database.eventDAO()

    @Singleton
    @Provides
    fun getEventDatabase(context: Context): EventRoomDatabase =
        Room.databaseBuilder(context, EventRoomDatabase::class.java, "event_database")
            .createFromAsset("event_database")
            .build()
}