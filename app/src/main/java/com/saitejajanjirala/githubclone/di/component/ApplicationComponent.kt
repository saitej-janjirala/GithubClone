package com.saitejajanjirala.githubclone.di.component

import com.saitejajanjirala.githubclone.BaseApplication
import com.saitejajanjirala.githubclone.utils.Helper
import com.saitejajanjirala.githubclone.di.module.ApplicationModule
import com.saitejajanjirala.githubclone.network.ApiService
import com.saitejajanjirala.githubclone.repo.SearchRepository
import dagger.Component
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.CompositeDisposable
import javax.inject.Singleton

@Component(modules = [ApplicationModule::class])
@Singleton
interface ApplicationComponent {

    fun inject(application: BaseApplication)

    fun getApiService(): ApiService

    fun getCompositeDisposable():CompositeDisposable

    fun getSearchRepository():SearchRepository

    fun getSchedulerProvider():Scheduler

    fun getHelper(): Helper
}