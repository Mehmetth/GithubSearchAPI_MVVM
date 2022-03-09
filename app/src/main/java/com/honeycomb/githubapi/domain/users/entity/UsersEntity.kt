package com.honeycomb.githubapi.domain.users.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UsersEntity(
    var id: Int,
    var name: String,
    var stargazers_count: Int,
    var open_issues_count: Int,
    var favorite : Boolean = false,
    var owner: OwnerEntity
) : Parcelable