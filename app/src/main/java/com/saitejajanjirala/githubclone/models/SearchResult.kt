package com.saitejajanjirala.githubclone.models


import com.google.gson.annotations.SerializedName

data class SearchResult(
    @SerializedName("incomplete_results")
    val incompleteResults: Boolean?,
    @SerializedName("items")
    val items: List<Item>?,
    @SerializedName("total_count")
    val totalCount: Int?
)