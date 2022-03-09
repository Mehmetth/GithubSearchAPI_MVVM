package com.honeycomb.githubapi.data.users

import com.honeycomb.githubapi.data.common.NetworkModule
import com.honeycomb.githubapi.domain.users.UsersRepository
import com.honeycomb.githubapi.data.users.remote.api.UsersApi
import com.honeycomb.githubapi.data.users.repository.UsersRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module(includes = [NetworkModule::class])
@InstallIn(SingletonComponent::class)
class UsersModule {
    @Singleton
    @Provides
    fun provideProductApi(retrofit: Retrofit) : UsersApi {
        return retrofit.create(UsersApi::class.java)
    }

    @Singleton
    @Provides
    fun provideProductRepository(usersApi: UsersApi) : UsersRepository {
        return UsersRepositoryImpl(usersApi)
    }
}