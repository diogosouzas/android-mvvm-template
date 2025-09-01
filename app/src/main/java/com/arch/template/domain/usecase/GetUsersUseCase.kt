package com.arch.template.domain.usecase

import com.arch.template.core.util.Result
import com.arch.template.data.model.User
import com.arch.template.data.repository.UserRepository
import kotlinx.coroutines.flow.Flow

class GetUsersUseCase(
    private val userRepository: UserRepository
) {
    operator fun invoke(): Flow<Result<List<User>>> {
        return userRepository.getUsersStream()
    }
}
