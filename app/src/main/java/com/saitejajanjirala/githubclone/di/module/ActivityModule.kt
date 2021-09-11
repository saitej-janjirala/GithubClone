package com.saitejajanjirala.githubclone.di.module

import android.app.Activity
import com.saitejajanjirala.githubclone.utils.Helper
import com.saitejajanjirala.githubclone.utils.ViewModelProviderFactory
import com.saitejajanjirala.githubclone.repo.SearchRepository
import com.saitejajanjirala.githubclone.ui.detail.DetailViewModel
import com.saitejajanjirala.githubclone.ui.main.MainViewModel
import dagger.Module
import dagger.Provides
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.CompositeDisposable

@Module
class ActivityModule(val activity: Activity) {

    @Provides
    fun providesMainViewModel(
        scheduler: Scheduler,
        compositeDisposable: CompositeDisposable,
        helper: Helper,
        searchRepository: SearchRepository
    ):MainViewModel= ViewModelProviderFactory(MainViewModel::class) { MainViewModel(searchRepository,helper,scheduler,compositeDisposable)
    }.create(MainViewModel::class.java)

    @Provides
    fun providesDetailViewModel(
        scheduler: Scheduler,
        compositeDisposable: CompositeDisposable,
        helper: Helper,
        searchRepository: SearchRepository
    ): DetailViewModel = ViewModelProviderFactory(DetailViewModel::class) { DetailViewModel(searchRepository,helper,scheduler,compositeDisposable)
    }.create(DetailViewModel::class.java)



}