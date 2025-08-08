package com.riky.githubusersearch.domain.usecase

import com.riky.githubusersearch.domain.repository.UserRepository
import javax.inject.Inject

class SearchUsersUseCase @Inject constructor(
    private val repo: UserRepository
) {
    suspend operator fun invoke(query: String) = repo.searchUsers(query)
}
