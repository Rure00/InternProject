package com.example.internproject.domain.repository

import com.example.internproject.data.dto.SignUpDto
import com.example.internproject.data.models.User
import com.example.internproject.domain.results.LoginResult
import com.example.internproject.domain.results.SignUpResult

interface UserRepository {
    suspend fun login(id: String, pwd: String): LoginResult
    suspend fun signUp(signUpDto: SignUpDto): SignUpResult

    suspend fun checkDuplicatedLoginId(loginId: String): Boolean
}