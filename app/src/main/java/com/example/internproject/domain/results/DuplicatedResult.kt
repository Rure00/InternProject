package com.example.internproject.domain.results

sealed class DuplicatedResult {
    data class Success(val isSuccess: Boolean): DuplicatedResult()
    data object Failure: DuplicatedResult()

}