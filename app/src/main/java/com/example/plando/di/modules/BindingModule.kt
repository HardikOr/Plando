package com.example.plando.di.modules

import com.example.plando.binding.ImageBindingAdapter
import com.example.plando.di.IconsUrlBaseString
import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides

@Module
class BindingModule {
    @Provides
    fun getImageBindingAdapter(picasso: Picasso, @IconsUrlBaseString baseUrl: String) =
        ImageBindingAdapter(picasso, baseUrl)
}