package com.example.internproject.data.datasource

import com.example.internproject.data.dto.SignUpDto
import com.example.internproject.domain.results.DuplicatedResult
import com.example.internproject.domain.results.SignUpResult
import javax.inject.Inject

class UserDataSource @Inject constructor(

) {
    suspend fun trySignUp(signUpDto: SignUpDto): SignUpResult {

    }

    suspend fun checkName(name: String): DuplicatedResult {

    }

    suspend fun checkId(id: String): DuplicatedResult {

    }
}