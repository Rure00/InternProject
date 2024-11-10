package com.example.internproject.domain.usecase

import com.example.internproject.domain.repository.UserRepository
import com.example.internproject.domain.results.DuplicatedResult
import javax.inject.Inject

class CheckIdDuplicateUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(loginId: String): DuplicatedResult {
        return userRepository.checkDuplicatedLoginId(loginId)
    }
}