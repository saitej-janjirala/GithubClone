package com.saitejajanjirala.githubclone.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class License(
    var key: String?,
    var name: String?,
    var node_id: String?,
    var spdx_id: String?,
    var url: String?
):Parcelable