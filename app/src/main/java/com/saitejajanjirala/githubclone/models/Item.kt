package com.saitejajanjirala.githubclone.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "items")
data class Item(
    var description: String?,
    var forks: Int?,
    @PrimaryKey
    var id:Int,
    var html_url: String?,
    var language: String?,
    var languages_url: String?,
    var name: String?,
    var node_id: String?,
    var open_issues_count: Int?,
    var owner: Owner?,
    var stargazers_count: Int?,
):Parcelable