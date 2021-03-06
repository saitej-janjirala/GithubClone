package com.saitejajanjirala.githubclone.ui.main

import android.text.TextUtils
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.saitejajanjirala.githubclone.base.BaseViewModel
import com.saitejajanjirala.githubclone.models.Item
import com.saitejajanjirala.githubclone.repo.SearchRepository
import com.saitejajanjirala.githubclone.utils.Helper
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.subjects.PublishSubject
import java.util.concurrent.TimeUnit

class MainViewModel(
    private val repository: SearchRepository,
    helper: Helper,
    schedulerProvider: Scheduler,
    compositeDisposable: CompositeDisposable
) : BaseViewModel(helper, schedulerProvider, compositeDisposable) {
    val listItems = MutableLiveData<MutableList<Item>>()
    val searchNetworkCall = MutableLiveData<Boolean>()
    val paginatedNetworkCall = MutableLiveData<Boolean>()
    val fromDb=MutableLiveData<Boolean>()
    var page = 1
    var query = ""

    init {
        searchNetworkCall.value=false
        paginatedNetworkCall.value=false
        fromDb.value=false
    }

    override fun onCreate() {

    }

    fun setSubject(subject: PublishSubject<String>) {
        compositeDisposable.add(
            subject.debounce(300, TimeUnit.MILLISECONDS)
                .filter {
                    if (!checkInternetConnection()) {
                        getDataForNetworkError()
                    }
                    return@filter !TextUtils.isEmpty(it) && checkInternetConnectionWithMessage()
                }
                .distinctUntilChanged()
                .switchMap {
                    page = 1
                    query = it
                    searchNetworkCall.postValue(true)
                    return@switchMap repository.getSearchData(it, 10, page).toObservable()
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(schedulerProvider)
                .subscribe({ itu ->
                    clearAndInsertIntoDatabase(itu.items)
                }, {
                    Log.i("error", "${it.message}")
                    searchNetworkCall.postValue(false)
                    messageString.postValue("${it.message}")
                })
        )
    }

    private fun clearAndInsertIntoDatabase(items: List<Item>?) {
        compositeDisposable.add(
            repository.clearDatabaseRecords()
                .flatMap {
                    return@flatMap items?.let { it1 -> repository.insertItemsIntoDatabase(it1) }
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(schedulerProvider)
                .subscribe({
                    fromDb.postValue(false)
                    listItems.postValue(items as MutableList<Item>?)
                    searchNetworkCall.postValue(false)
                }, {
                    fromDb.postValue(false)
                    Log.i("clearandinserterror", "${it.message}")
                    listItems.postValue(items as MutableList<Item>?)
                    searchNetworkCall.postValue(false)
                })
        )
    }

    private fun insertIntoDatabaseAfterTenRecords(items: MutableList<Item>) {
        compositeDisposable.add(
            repository.getCount()
                .flatMap {
                    if (it == 10) {
                        val list = items.subList(0, 5)
                        return@flatMap repository.insertItemsIntoDatabase(list as List<Item>)
                    } else {
                        return@flatMap null
                    }
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(schedulerProvider)
                .subscribe({
                    fromDb.postValue(false)
                    paginatedNetworkCall.postValue(false)
                    listItems.apply {
                        items?.let { it1 -> value?.addAll(it1) }
                    }
                }, {
                    fromDb.postValue(false)
                    Log.i("insertafter10error", "${it.message}")
                    paginatedNetworkCall.postValue(false)
                    listItems.apply {
                        items?.let { it1 -> value?.addAll(it1) }
                    }
                })
        )

    }

    private fun getDataForNetworkError() {
        searchNetworkCall.postValue(true)
        compositeDisposable.add(
            repository.getItemsFromDatabase()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(schedulerProvider)
                .subscribe({
                    fromDb.postValue(true)
                    listItems.postValue(it as MutableList<Item>?)
                    searchNetworkCall.postValue(false)
                }, {
                    fromDb.postValue(true)
                    Log.i("networkerrorresult", "${it.message}")
                    searchNetworkCall.postValue(false)
                    messageString.postValue("${it.message}")
                })
        )
    }

    fun getData() {
        if (checkInternetConnectionWithMessage()) {
            page++
            paginatedNetworkCall.postValue(true)
            compositeDisposable.add(
                repository.getSearchData(query, 10, page)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(schedulerProvider)
                    .subscribe({
                        Log.i("data", "$it")
                        fromDb.postValue(false)
                        insertIntoDatabaseAfterTenRecords(it.items as MutableList<Item>)
                    }, {
                        fromDb.postValue(false)
                        Log.i("error", "${it.message}")
                        paginatedNetworkCall.postValue(false)
                        messageString.postValue("${it.message}")
                    })
            )
        }
    }

}