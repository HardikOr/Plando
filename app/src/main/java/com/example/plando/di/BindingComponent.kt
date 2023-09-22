package com.example.plando.di

import androidx.databinding.DataBindingComponent
import com.example.plando.binding.ImageBindingAdapter
import com.example.plando.di.modules.BindingModule
import dagger.Component

@DataBindingScope
@Component(dependencies = [ApplicationComponent::class], modules = [BindingModule::class])
interface BindingComponent : DataBindingComponent {
    override fun getImageBindingAdapter(): ImageBindingAdapter
}