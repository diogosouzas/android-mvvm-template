package com.arch.template.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arch.template.core.util.Result
import com.arch.template.domain.usecase.UserUseCases
import com.arch.template.ui.model.HomeUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import timber.log.Timber

class HomeViewModel(
    private val userUseCases: UserUseCases
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadUsers()
    }

    fun onRefresh() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isRefreshing = true)
            
            when (val result = userUseCases.syncUsersUseCase()) {
                is Result.Success -> {
                    Timber.d("Users synced successfully")
                }
                is Result.Error -> {
                    Timber.e(result.exception, "Error syncing users")
                    _uiState.value = _uiState.value.copy(
                        errorMessage = result.exception.message
                    )
                }
                is Result.Loading -> {
                    // Handle loading state if needed
                }
            }
            
            _uiState.value = _uiState.value.copy(isRefreshing = false)
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(errorMessage = null)
    }

    private fun loadUsers() {
        userUseCases.getUsersUseCase()
            .onEach { result ->
                when (result) {
                    is Result.Loading -> {
                        _uiState.value = _uiState.value.copy(isLoading = true)
                    }
                    is Result.Success -> {
                        _uiState.value = _uiState.value.copy(
                            users = result.data,
                            isLoading = false,
                            errorMessage = null
                        )
                    }
                    is Result.Error -> {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            errorMessage = result.exception.message
                        )
                        Timber.e(result.exception, "Error loading users")
                    }
                }
            }
            .launchIn(viewModelScope)
    }
}
