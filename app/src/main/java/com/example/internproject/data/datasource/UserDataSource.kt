package com.example.internproject.data.datasource

import com.example.internproject.data.dto.SignUpDto
import com.example.internproject.data.models.User
import com.example.internproject.data.toMap
import com.example.internproject.data.toObjectsWithId
import com.example.internproject.domain.results.DuplicatedResult
import com.example.internproject.domain.results.LoginResult
import com.example.internproject.domain.results.SignUpResult
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserDataSource @Inject constructor(

) {
    companion object {
        private const val TAG = "UserDataSource"
        private const val USER_COLLECTION = "users"

        private const val NAME = "name"
        private const val LOGIN_ID = "loginId"
        private const val PASSWORD = "password"
        private const val BIRTH = "brith"
    }

    private val db = Firebase.firestore

    suspend fun tryLogin(id: String, pwd: String): LoginResult {
        return kotlin.runCatching {
            val result = db.collection(USER_COLLECTION)
                .whereEqualTo(LOGIN_ID, id)
                .whereEqualTo(PASSWORD, pwd)
                .get().await().toObjectsWithId<User>()

            if(result.isEmpty()) LoginResult.Failure
            else LoginResult.Success(result[0])
        }.getOrElse { LoginResult.Failure }
    }

    suspend fun trySignUp(signUpDto: SignUpDto): SignUpResult {
        return kotlin.runCatching {
            val map = mutableMapOf<String, String>()
            map[NAME] = signUpDto.name
            map[LOGIN_ID] = signUpDto.loginId
            map[PASSWORD] = signUpDto.pwd
            map[BIRTH] = signUpDto.birth

            db.collection(USER_COLLECTION)
                .add(map)
                .await()

            SignUpResult.Success
        }.getOrElse { SignUpResult.Failure }
    }

    suspend fun checkName(name: String): DuplicatedResult {
        return kotlin.runCatching {
            val result = db.collection(USER_COLLECTION)
                .whereEqualTo(NAME, name)
                .get().await()

            DuplicatedResult.Success(result.isEmpty)
        }.getOrElse { DuplicatedResult.Failure }
    }

    suspend fun checkId(id: String): DuplicatedResult {
        return kotlin.runCatching {
            val result = db.collection(USER_COLLECTION)
                .whereEqualTo(LOGIN_ID, id)
                .get().await()

            DuplicatedResult.Success(result.isEmpty)
        }.getOrElse { DuplicatedResult.Failure }
    }
}