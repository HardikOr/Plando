package com.example.plando.di

import com.example.plando.di.modules.AppModule
import com.example.plando.di.modules.ContextModule
import com.example.plando.di.modules.DispatcherModule
import com.example.plando.di.modules.ViewModelModule
import com.example.plando.ui.details.DetailsFragment
import com.example.plando.ui.events.EventsFragment
import com.squareup.picasso.Picasso
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ContextModule::class,
        AppModule::class,
        ViewModelModule::class,
        DispatcherModule::class
    ]
)
interface ApplicationComponent {
    @IconsUrlBaseString
    fun provideIconsUrl(): String

    fun providePicasso(): Picasso

    fun inject(eventsFragment: EventsFragment)
    fun inject(detailsFragment: DetailsFragment)
}