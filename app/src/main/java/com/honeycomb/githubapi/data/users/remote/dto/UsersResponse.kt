package com.honeycomb.githubapi.data.users.remote.dto

import com.google.gson.annotations.SerializedName

data class UsersResponse(
    @SerializedName("id")
    var id: Int,
    @SerializedName("name")
    var name: String,
    @SerializedName("stargazers_count")
    var stargazers_count: Int,
    @SerializedName("open_issues_count")
    var open_issues_count: Int,
    @SerializedName("owner")
    var owner: Owner
)
