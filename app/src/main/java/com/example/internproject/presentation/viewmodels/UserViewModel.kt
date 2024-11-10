package com.example.internproject.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.internproject.data.models.User
import com.example.internproject.domain.results.LoginResult
import com.example.internproject.domain.usecase.LoginUseCase
import com.example.internproject.presentation.ui_state.ResultUiState
import com.example.internproject.presentation.ui_state.SignUpUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
): ViewModel() {
    private val _user = MutableLiveData<User?>(null)
    val user get() = _user

    private val _resultUiState = MutableStateFlow<ResultUiState>(ResultUiState.Init)
    val resultUiState = _resultUiState.asStateFlow()

    fun tryLogin(id: String, pwd: String) {
        viewModelScope.launch {
            when(val result = loginUseCase.invoke(id, pwd)) {
                is LoginResult.Success -> {
                    _user.value = result.user
                    _resultUiState.value = ResultUiState.Success(true)
                } is LoginResult.Failure -> {
                _resultUiState.value = ResultUiState.Success(false)
                }
            }
        }
    }


}