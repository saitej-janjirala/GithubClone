package com.saitejajanjirala.githubclone.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bumptech.glide.load.engine.Resource
import com.saitejajanjirala.githubclone.utils.Helper
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.CompositeDisposable

abstract class BaseViewModel(
    protected val helper: Helper,
    protected val schedulerProvider: Scheduler,
    protected val compositeDisposable: CompositeDisposable):ViewModel() {

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

    abstract fun onCreate()

    val messageString: MutableLiveData<String> = MutableLiveData()

    protected fun checkInternetConnectionWithMessage(): Boolean =
        if (helper.isNetworkConnected()) {
            true
        } else {
            messageString.postValue("no internet connection")
            false
        }

    protected fun checkInternetConnection(): Boolean = helper.isNetworkConnected()
}