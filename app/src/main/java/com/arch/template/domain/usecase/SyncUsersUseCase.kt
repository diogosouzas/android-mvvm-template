package com.arch.template.domain.usecase

import com.arch.template.core.util.Result
import com.arch.template.data.repository.UserRepository

class SyncUsersUseCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(): Result<Unit> {
        return userRepository.syncUsers()
    }
}
