package com.saitejajanjirala.githubclone.network

import com.saitejajanjirala.githubclone.models.Contributor
import com.saitejajanjirala.githubclone.utils.Keys
import com.saitejajanjirala.githubclone.models.SearchResult
import io.reactivex.rxjava3.core.ObservableSource
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET(Keys.SEARCH_END_POINT)
    fun getSearchResults(
        @Query("q") q:String,@Query("per_page") perPage:Int,@Query("page") page:Int
    ): Single<SearchResult>

    @GET("/repos/{userName}/{repoName}/contributors")
    fun getContributors(
    @Path("userName") userName:String,
    @Path("repoName") repoName:String):Single<List<Contributor>>

}