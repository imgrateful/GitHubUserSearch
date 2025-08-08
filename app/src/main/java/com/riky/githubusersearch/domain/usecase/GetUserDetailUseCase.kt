package com.riky.githubusersearch.domain.usecase

import com.riky.githubusersearch.domain.repository.UserRepository
import javax.inject.Inject

class GetUserDetailUseCase @Inject constructor(
    private val repo: UserRepository
) {
    suspend operator fun invoke(username: String) = repo.getUserDetail(username)
}
