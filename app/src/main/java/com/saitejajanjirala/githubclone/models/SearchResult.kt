package com.saitejajanjirala.githubclone.models

data class SearchResult(
    var incomplete_results: Boolean?,
    var items: List<Item>?,
    var total_count: Int?
)