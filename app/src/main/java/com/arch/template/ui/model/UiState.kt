package com.arch.template.ui.model

import com.arch.template.data.model.User

data class HomeUiState(
    val users: List<User> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isRefreshing: Boolean = false
)
