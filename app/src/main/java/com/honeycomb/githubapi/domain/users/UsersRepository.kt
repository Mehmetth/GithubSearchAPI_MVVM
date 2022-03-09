package com.honeycomb.githubapi.domain.users

import com.honeycomb.githubapi.domain.common.BaseResult
import com.honeycomb.githubapi.data.users.remote.dto.UsersResponse
import com.honeycomb.githubapi.domain.users.entity.UsersEntity
import kotlinx.coroutines.flow.Flow

interface UsersRepository {
    suspend fun getUsersRepo(userName:String) : Flow<BaseResult<List<UsersEntity>, UsersResponse>>
}