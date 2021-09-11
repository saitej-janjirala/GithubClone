package com.saitejajanjirala.githubclone.ui.main

import android.text.TextUtils
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.saitejajanjirala.githubclone.utils.Helper
import com.saitejajanjirala.githubclone.base.BaseViewModel
import com.saitejajanjirala.githubclone.models.Item
import com.saitejajanjirala.githubclone.repo.SearchRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.subjects.PublishSubject
import java.util.concurrent.TimeUnit

class MainViewModel(
    private val repository : SearchRepository,
    helper: Helper,
    schedulerProvider:Scheduler,
    compositeDisposable:CompositeDisposable) :BaseViewModel(helper,schedulerProvider,compositeDisposable) {
    val listItems=MutableLiveData<MutableList<Item>>()
    val searchNetworkCall=MutableLiveData<Boolean>()
    val paginatedNetworkCall=MutableLiveData<Boolean>()
    var page=1
    var query=""
    override fun onCreate() {

    }

    fun setSubject(subject:PublishSubject<String>){
        subject.debounce(300,TimeUnit.MILLISECONDS)
            .filter {
                return@filter !TextUtils.isEmpty(it) && checkInternetConnectionWithMessage()
            }
            .distinctUntilChanged()
            .switchMap {
                page=1
                query=it
                searchNetworkCall.postValue(true)
                return@switchMap repository.getSearchData(it,10,page).toObservable()
            }
            .observeOn(schedulerProvider)
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribe( {
                Log.i("data","$it")
                searchNetworkCall.postValue(false)
                listItems.postValue(it.items as MutableList<Item>?)
            },{
                Log.i("error","${it.message}")
                searchNetworkCall.postValue(false)
                messageString.postValue("${it.message}")
            })
    }

    fun getData(){
        if(checkInternetConnectionWithMessage()) {
            page++
            paginatedNetworkCall.postValue(true)
            repository.getSearchData(query, 10, page)
                .observeOn(schedulerProvider)
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Log.i("data", "$it")
                    paginatedNetworkCall.postValue(false)
                    listItems.apply {
                        it.items?.let { it1 -> value?.addAll(it1) }
                    }
                }, {
                    Log.i("error", "${it.message}")
                    paginatedNetworkCall.postValue(false)
                    messageString.postValue("${it.message}")
                })
        }
    }

}