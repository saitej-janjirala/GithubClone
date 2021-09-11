package com.saitejajanjirala.githubclone

import android.app.Application
import com.saitejajanjirala.githubclone.di.component.ApplicationComponent
import com.saitejajanjirala.githubclone.di.component.DaggerApplicationComponent
import com.saitejajanjirala.githubclone.di.module.ApplicationModule

class BaseApplication:Application() {
    lateinit var applicationComponent: ApplicationComponent
    override fun onCreate() {
        super.onCreate()
        applicationComponent=DaggerApplicationComponent.builder()
            .applicationModule(ApplicationModule(this))
            .build()
        applicationComponent.inject(this)
    }
}