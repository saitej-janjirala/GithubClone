package com.saitejajanjirala.githubclone.models


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Owner(
    @SerializedName("avatar_url")
    val avatarUrl: String?,
    @SerializedName("login")
    val login: String?,
):Parcelable