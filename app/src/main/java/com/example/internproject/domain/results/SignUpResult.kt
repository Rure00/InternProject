package com.example.internproject.domain.results

sealed class SignUpResult {
    data object Success : SignUpResult()
    data object Failure : SignUpResult()
}