package com.riky.githubusersearch.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.riky.githubusersearch.domain.model.User
import com.riky.githubusersearch.domain.usecase.SearchUsersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchUsers: SearchUsersUseCase
) : ViewModel() {
    private val _users = MutableLiveData<List<User>>()
    val users: LiveData<List<User>> = _users
    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    fun search(query: String) {
        viewModelScope.launch {
            _loading.value = true
            try {
                _users.value = searchUsers(query)
            } catch (e: Exception) {
                _users.value = emptyList()
            }
            _loading.value = false
        }
    }
}
