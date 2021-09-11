package com.saitejajanjirala.githubclone.ui.detail

import androidx.lifecycle.MutableLiveData
import com.saitejajanjirala.githubclone.base.BaseViewModel
import com.saitejajanjirala.githubclone.models.Contributor
import com.saitejajanjirala.githubclone.repo.SearchRepository
import com.saitejajanjirala.githubclone.utils.Helper
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.CompositeDisposable

class DetailViewModel (
    private val repository : SearchRepository,
    helper: Helper,
    schedulerProvider: Scheduler,
    compositeDisposable: CompositeDisposable
) :BaseViewModel(helper,schedulerProvider,compositeDisposable) {

    val contributorList= MutableLiveData<List<Contributor>>()
    val isLoading=MutableLiveData<Boolean>()
    val error=MutableLiveData<Boolean>()
    init {
        isLoading.value=false
        error.value=false
    }

    override fun onCreate() {

    }

    fun getContributors(username:String,repoName:String){
        if(checkInternetConnectionWithMessage()) {
            isLoading.value=true
            compositeDisposable.add(
            repository.getContributorData(username, repoName)
                .subscribeOn(schedulerProvider)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    error.postValue(false)
                    isLoading.postValue(false)
                    contributorList.postValue(it)
                },{
                    error.postValue(true)
                    isLoading.postValue(false)
                    messageString.postValue(it.message)
                })
            )
        }
        else{
            error.postValue(true)
        }
    }
}