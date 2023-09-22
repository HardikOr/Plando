package com.example.plando.di.modules

import android.content.Context
import com.example.plando.R
import com.example.plando.di.ApiUrlBaseString
import com.example.plando.di.IconsUrlBaseString
import dagger.Module
import dagger.Provides

@Module
class ContextModule(val context: Context) {
    @Provides
    fun getApplicationContext() = context.applicationContext

    @Provides
    @IconsUrlBaseString
    fun getIconsUrlBaseString() = context.getString(R.string.icons_base_url)

    @Provides
    @ApiUrlBaseString
    fun getApiUrlBaseString() = context.getString(R.string.api_base_url)
}