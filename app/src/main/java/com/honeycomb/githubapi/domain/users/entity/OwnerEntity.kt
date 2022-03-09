package com.honeycomb.githubapi.domain.users.entity

import android.os.Parcelable
import androidx.room.Entity
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class OwnerEntity(
    var login: String?,
    var avatar_url: String?
    ) : Parcelable
