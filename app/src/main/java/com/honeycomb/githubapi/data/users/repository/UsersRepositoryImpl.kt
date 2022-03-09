package com.honeycomb.githubapi.data.users.repository

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.honeycomb.githubapi.domain.common.BaseResult
import com.honeycomb.githubapi.domain.users.UsersRepository
import com.honeycomb.githubapi.data.users.remote.api.UsersApi
import com.honeycomb.githubapi.data.users.remote.dto.UsersResponse
import com.honeycomb.githubapi.domain.users.entity.OwnerEntity
import com.honeycomb.githubapi.domain.users.entity.UsersEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UsersRepositoryImpl @Inject constructor(private val usersApi: UsersApi) :
    UsersRepository {
    override suspend fun getUsersRepo(userName:String): Flow<BaseResult<List<UsersEntity>, UsersResponse>> {
        return flow {
            val response = usersApi.getUsers(userName)
            if (response.isSuccessful){
                val body = response.body()!!

                val users = mutableListOf<UsersEntity>()
                var owner: OwnerEntity?
                body.forEach { item ->
                    owner = OwnerEntity(item.owner.login, item.owner.avatar_url)
                    users.add(
                        UsersEntity(
                            item.id,
                            item.name,
                            item.stargazers_count,
                            item.open_issues_count,
                            false,
                            owner!!
                    )
                    )
                }


                emit(BaseResult.Success(users))
            }else{
                val type = object : TypeToken<UsersResponse>(){}.type
                val err = Gson().fromJson<UsersResponse>(response.errorBody()!!.charStream(), type)!!
                emit(BaseResult.Error(err))
            }
        }
    }
}