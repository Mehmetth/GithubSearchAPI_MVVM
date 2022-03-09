package com.honeycomb.githubapi.domain.users.usecase

import com.honeycomb.githubapi.domain.common.BaseResult
import com.honeycomb.githubapi.domain.users.UsersRepository
import com.honeycomb.githubapi.data.users.remote.dto.UsersResponse
import com.honeycomb.githubapi.domain.users.entity.UsersEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UsersUseCase @Inject constructor(private val usersRepository: UsersRepository) {
    suspend fun execute(userName:String): Flow<BaseResult<List<UsersEntity>, UsersResponse>> {
        return usersRepository.getUsersRepo(userName)
    }
}