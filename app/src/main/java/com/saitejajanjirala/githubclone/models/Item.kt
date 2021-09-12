package com.saitejajanjirala.githubclone.models


import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "items")
@Parcelize
data class Item(
    @SerializedName("description")
    val description: String?,
    @SerializedName("name")
    val name:String?,
    @SerializedName("forks")
    val forks: Int?,
    @SerializedName("html_url")
    val htmlUrl: String?,
    @PrimaryKey
    @SerializedName("id")
    val id: Int,
    @SerializedName("open_issues_count")
    val openIssuesCount: Int?,
    @SerializedName("owner")
    val owner: Owner?,
    @SerializedName("stargazers_count")
    val stargazersCount: Int?,
):Parcelable