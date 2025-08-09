package com.riky.githubusersearch.di

import com.riky.githubusersearch.data.api.GitHubApiService
import com.riky.githubusersearch.data.db.UserDao
import com.riky.githubusersearch.data.repository.UserRepositoryImpl
import com.riky.githubusersearch.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideUserRepository(
        api: GitHubApiService,
        dao: UserDao
    ): UserRepository = UserRepositoryImpl(api, dao)
}
