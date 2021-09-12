package com.saitejajanjirala.githubclone.repo

import com.saitejajanjirala.githubclone.db.DatabaseService
import com.saitejajanjirala.githubclone.models.Item
import com.saitejajanjirala.githubclone.network.ApiService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchRepository @Inject constructor(
    private val apiService:ApiService,
    private val databaseService: DatabaseService) {

    fun getSearchData(query:String, per_page:Int, page:Int)=apiService.getSearchResults(query,per_page,page)

    fun getContributorData(userName:String,repoName:String)=apiService.getContributors(userName, repoName)

    fun getItemsFromDatabase()=databaseService.getItemsDao().getAllItems()

    fun insertItemsIntoDatabase(list:List<Item>)=databaseService.getItemsDao().insertMany(list)

    fun clearDatabaseRecords()=databaseService.getItemsDao().clearAll()

    fun getCount()=databaseService.getItemsDao().getCount()
}