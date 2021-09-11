package com.saitejajanjirala.githubclone.repo

import com.saitejajanjirala.githubclone.network.ApiService
import io.reactivex.rxjava3.disposables.CompositeDisposable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchRepository @Inject constructor(private val apiService:ApiService) {

    fun getSearchData(query:String, per_page:Int, page:Int)=apiService.getSearchResults(query,per_page,page)

    fun getContributorData(userName:String,repoName:String)=apiService.getContributors(userName, repoName)
}