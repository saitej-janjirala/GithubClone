package com.saitejajanjirala.githubclone.di.component

import com.saitejajanjirala.githubclone.di.ActivityScope
import com.saitejajanjirala.githubclone.di.module.ActivityModule
import com.saitejajanjirala.githubclone.di.module.ApplicationModule
import com.saitejajanjirala.githubclone.ui.detail.DetailActivity
import com.saitejajanjirala.githubclone.ui.main.MainActivity
import com.saitejajanjirala.githubclone.ui.main.MainViewModel
import dagger.Component
import dagger.Module


@ActivityScope
@Component(modules = [ActivityModule::class],dependencies = [ApplicationComponent::class])
interface ActivityComponent {
    fun inject(activity:MainActivity)
    fun inject(activity:DetailActivity)

}