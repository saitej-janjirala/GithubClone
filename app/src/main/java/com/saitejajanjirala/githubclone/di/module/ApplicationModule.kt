package com.saitejajanjirala.githubclone.di.module

import android.app.Application
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import com.saitejajanjirala.githubclone.BaseApplication
import com.saitejajanjirala.githubclone.db.DatabaseService
import com.saitejajanjirala.githubclone.utils.Helper
import com.saitejajanjirala.githubclone.utils.Keys
import com.saitejajanjirala.githubclone.di.ApplicationContext
import com.saitejajanjirala.githubclone.network.ApiService
import dagger.Module
import dagger.Provides
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class ApplicationModule (private val application:BaseApplication){

    @Provides
    @Singleton
    fun providesApiService():ApiService{
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(logging)
        return Retrofit.Builder()
            .baseUrl(Keys.BASE_URL)
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient.build())
            .build()
            .create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun providesCompositeDisposable()=CompositeDisposable()

    @Provides
    @Singleton
    fun providesApplication():Application=application

    @Provides
    @Singleton
    @ApplicationContext
    fun providesContext():Context=application

    @Provides
    @Singleton
    fun providesSchedulerProvider():Scheduler=Schedulers.io()

    @Provides
    @Singleton
    fun providesHelper(): Helper = Helper(application)

    @Provides
    @Singleton
    fun provideDatabaseService():DatabaseService=
        Room.databaseBuilder(application,DatabaseService::class.java,Keys.DATABASE_NAME).build()
}