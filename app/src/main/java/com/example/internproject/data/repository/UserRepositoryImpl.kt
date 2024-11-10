package com.example.internproject.data.repository

import com.example.internproject.data.datasource.UserDataSource
import com.example.internproject.data.dto.SignUpDto
import com.example.internproject.data.models.User
import com.example.internproject.domain.repository.UserRepository
import com.example.internproject.domain.results.DuplicatedResult
import com.example.internproject.domain.results.LoginResult
import com.example.internproject.domain.results.SignUpResult
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userDataSource: UserDataSource
): UserRepository {
    override suspend fun login(id: String, pwd: String): LoginResult {
        return LoginResult.Success(User(
            id = "dicit", userName = "Imogene Jackson", birth = "doctus", loginId = "viris", pwd = "melius"
        ))
    }

    override suspend fun signUp(signUpDto: SignUpDto): SignUpResult {
        return kotlin.runCatching {
            userDataSource.trySignUp(signUpDto)
        }.getOrElse { SignUpResult.Failure }
    }

    override suspend fun checkDuplicatedName(name: String): DuplicatedResult {
        return kotlin.runCatching {
            userDataSource.checkName(name)
        }.getOrElse { DuplicatedResult.Failure }
    }

    override suspend fun checkDuplicatedLoginId(loginId: String): DuplicatedResult {
        return kotlin.runCatching {
            userDataSource.checkId(loginId)
        }.getOrElse { DuplicatedResult.Failure }
    }
}