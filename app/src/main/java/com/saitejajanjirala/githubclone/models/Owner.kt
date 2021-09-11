package com.saitejajanjirala.githubclone.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Owner(
    var avatar_url: String?,
    var login: String?,
    var node_id: String?,
):Parcelable