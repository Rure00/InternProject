package com.example.internproject.domain.usecase

import com.example.internproject.domain.repository.UserRepository
import com.example.internproject.domain.results.LoginResult
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(id: String, pwd: String): LoginResult {
        return userRepository.login(id, pwd)
    }
}