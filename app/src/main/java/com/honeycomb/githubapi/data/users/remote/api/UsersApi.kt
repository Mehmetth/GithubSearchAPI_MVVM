package com.honeycomb.githubapi.data.users.remote.api

import com.honeycomb.githubapi.data.users.remote.dto.UsersResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface UsersApi {
    @GET("{userName}/repos")
    suspend fun getUsers(@Path("userName") userName: String) : Response<List<UsersResponse>>
}