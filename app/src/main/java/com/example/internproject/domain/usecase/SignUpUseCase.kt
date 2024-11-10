package com.example.internproject.domain.usecase

import com.example.internproject.data.dto.SignUpDto
import com.example.internproject.domain.repository.UserRepository
import com.example.internproject.domain.results.SignUpResult
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(signUpDto: SignUpDto): SignUpResult {
        return userRepository.signUp(signUpDto)
    }
}