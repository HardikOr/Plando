package com.example.plando.di

import androidx.lifecycle.ViewModel
import dagger.MapKey
import javax.inject.Qualifier
import javax.inject.Scope
import kotlin.reflect.KClass

// DataBiding
@Scope
annotation class DataBindingScope

// ViewModelKey
@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER
)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class ViewModelKey(val value: KClass<out ViewModel>)

// Dispatchers
@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class DefaultDispatcher

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class IoDispatcher

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class MainDispatcher

// Url Strings
@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class IconsUrlBaseString

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class ApiUrlBaseString