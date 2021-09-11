package com.saitejajanjirala.githubclone.utils

import android.content.Context
import android.net.ConnectivityManager
import com.saitejajanjirala.githubclone.di.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Helper @Inject constructor(private val context: Context) {

    fun isNetworkConnected(): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        return activeNetwork?.isConnected ?: false
    }

}