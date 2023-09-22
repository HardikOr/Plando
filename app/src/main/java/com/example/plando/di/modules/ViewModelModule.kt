package com.example.plando.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.plando.di.ViewModelKey
import com.example.plando.ui.details.DetailsViewModel
import com.example.plando.ui.events.EventsViewModel
import com.example.plando.viewmodel.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

// Fucking bullshit with @ViewModelKey: Use java next use kotlin. And now everything works
// ViewModelKey -> ViewModelKeyJava -> ViewModelKey

@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(EventsViewModel::class)
    abstract fun provideEventViewModel(viewModel: EventsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DetailsViewModel::class)
    abstract fun provideDetailsViewModel(viewModel: DetailsViewModel): ViewModel

    @Binds
    abstract fun bindsViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}