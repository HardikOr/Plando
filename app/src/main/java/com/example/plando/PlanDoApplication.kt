package com.example.plando

import android.app.Application
import androidx.databinding.DataBindingUtil
import com.example.plando.di.ApplicationComponent
import com.example.plando.di.DaggerApplicationComponent
import com.example.plando.di.DaggerBindingComponent
import com.example.plando.di.modules.ContextModule

class PlanDoApplication : Application() {
    lateinit var appComponent: ApplicationComponent
    //    val applicationScope = CoroutineScope(SupervisorJob())

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerApplicationComponent.builder()
            .contextModule(ContextModule(applicationContext))
            .build()

        val bindingComponent = DaggerBindingComponent.builder()
            .applicationComponent(appComponent)
            .build()

        DataBindingUtil.setDefaultComponent(bindingComponent)
    }
}