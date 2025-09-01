package com.arch.template.domain.usecase

data class UserUseCases(
    val getUsersUseCase: GetUsersUseCase,
    val syncUsersUseCase: SyncUsersUseCase
)
