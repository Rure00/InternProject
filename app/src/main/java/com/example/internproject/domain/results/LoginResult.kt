package com.example.internproject.domain.results

import com.example.internproject.data.models.User

sealed class LoginResult {
    data class Success(val user: User): LoginResult()
    data object Failure: LoginResult()
}